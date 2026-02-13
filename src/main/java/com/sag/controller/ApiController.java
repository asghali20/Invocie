package com.sag.controller;

import com.sag.model.v1.Client;
import com.sag.model.v1.Invoice;
import com.sag.service.ApiService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.IOException;
//import org.apache.camel.ProducerTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.io.PrintWriter;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMethod;

import com.opencsv.CSVWriter;
import com.sag.service.FileProcessingServiceImpl;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Ahmed
 */
@ControllerAdvice
@RestController
@RequestMapping("/sag-invoice/v1")
public class ApiController {

    public static final String API_PATH = "/sag-invoice/v1";

    @Autowired
    private ApiService apiService;

    
      @Value("${filePath}")
    private String basePath;
      
    @PostMapping(
            value = "addClient",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addClient(@Valid @RequestBody Client request) throws Exception {
        System.out.println("@@@@@@ add client");
        Boolean status = apiService.createClient(request);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(status);
    }

    @RequestMapping(value = "getClient",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> retriveClient(@Valid @RequestParam String id) throws Exception {
        Client client = apiService.retriveClient(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(client);
    }

    @PostMapping(
            value = "addInvoice",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> addInvoice(@Valid @RequestBody Invoice request) throws Exception {
        Boolean status = apiService.createInvoice(request);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(status);
    }

    @RequestMapping(value = "finalizeInvoice",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> finalizeInvoice(@Valid @RequestParam String id) throws Exception {
        Invoice invoice = apiService.finalizeInvoice(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(invoice);
    }

    @RequestMapping(value = "paidInvoice",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> paidInvoice(@Valid @RequestParam String id) throws Exception {
        Invoice invoice = apiService.paidInvoice(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(invoice);
    }

    @RequestMapping(value = "getInvoice",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> retriveInvoice(@Valid @RequestParam String id) throws Exception {
        Invoice invoice = apiService.retriveInvoice(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(invoice);
    }

    @RequestMapping(value = "generateMonthlyReport",
            method = RequestMethod.GET
    )
    public void exportToCsv(HttpServletResponse response) throws IOException {
        try {
            // get num of invoices
            List<Invoice> invoices = apiService.getInvoicesReport("01");

            // get total amount 
            BigDecimal totalSum = invoices.stream()
                    .map(Invoice::getTotal)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // get total taxes 
            BigDecimal totalTaxes = invoices.stream()
                    .map(Invoice::getTax)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            // get average invocie 
            BigDecimal averageInvoice = BigDecimal.ZERO;
            if (totalSum.intValue() > 0 && invoices.size() > 0) {
                averageInvoice = totalSum.divide(new BigDecimal(invoices.size()));
            }

            // getting unpaid invoices
            List<Invoice> unPaidInvoices = invoices.stream()
                    .filter(obj -> (obj.getStatus()) != null)
                    .filter(obj -> !((obj.getStatus()).equals("PAID")))
                    .collect(Collectors.toList());


          
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=data.csv");

            try (PrintWriter writer = response.getWriter(); CSVWriter csvWriter = new CSVWriter(writer)) {

                String[] header = {"Total Invoices", "Total Amount", "Total Taxes Paid", "Average Invoice ", "Count of unpaid Invocies"};
                csvWriter.writeNext(header);

                List<String[]> data = Arrays.asList(
                        new String[]{String.valueOf(
                                invoices.size()),
                                totalSum.toString(), 
                                totalTaxes.toString(), 
                                averageInvoice.toString(),
                                String.valueOf(unPaidInvoices.size())
                        },
                        new String[]{"", "", "", "", ""}
                );

                // Write data rows
                csvWriter.writeAll(data);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // run every month in midnight
    @Scheduled(cron = "0 0 0 1 * ?")
    public void generateAndEmailMonthlyReport() {

        // generate an notification to send the report
    }
    
    
    /********* file upload/download functions **********************/
    @Autowired
    private FileProcessingServiceImpl fileProcessingService;



    
    @PostMapping(
            value = "upload"           
    )
    public ResponseEntity<?> uploadFile(@RequestParam String fileName, @RequestParam(name = "file") MultipartFile file){
        
        System.out.println("########## uploading ....");
        String status = fileProcessingService.uploadFile(fileName, file, basePath);
        return "CREATED".equals(status) ? new ResponseEntity<>(HttpStatus.CREATED) : ("EXIST".equals(status) ? new ResponseEntity<>(HttpStatus.NOT_MODIFIED) :new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED));
    }
    
//
//    @PostMapping("/upload-files-entity")
//    Mono uploadFileWithEntity(@RequestPart("files") Flux<FilePart> filePartFlux) {
//    FileRecord fileRecord = new FileRecord();
//
//    return filePartFlux.flatMap(filePart -> filePart.transferTo(Paths.get(filePart.filename()))
//      .then(Mono.just(filePart.filename())))
//      .collectList()
//      .flatMap(filenames -> {
//          fileRecord.setFilenames(filenames);
//          return fileRecordService.save(fileRecord);
//      })
//      .onErrorResume(error -> Mono.error(error));
//}
    
}
