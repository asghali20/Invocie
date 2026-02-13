/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.controller;

import java.util.List;

/**
 *
 * @author ahmed
 */




import com.sag.service.FileUploadService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

//@Slf4j
@RestController
@RequestMapping("multipart-file")
//@RequiredArgsConstructor
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    // use Flux<FilePart> for multiple file upload
    @PostMapping(value = "/upload-flux", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload(@RequestPart("files") Flux<FilePart> filePartFlux) {

        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return fileUploadService.getLines(filePartFlux).collectList();
    }

    // use single Mono<FilePart> for single file upload
    @PostMapping(value = "/upload-mono", 
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
           
    )
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload(@RequestPart("file") Mono<FilePart> filePartMono) {

        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        System.out.println(":::::: upload-mono" + filePartMono);
        return fileUploadService.getLines(filePartMono).collectList();
    }

    // use single FilePart for single file upload
    @PostMapping(value = "/upload-filePart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> upload(@RequestPart("file") FilePart filePart) {

        System.out.println(":::::: upload-filePar");
        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return fileUploadService.getLines(filePart).collectList();
    }

    // use Mono<MultiValueMap<String, Part>> for both single and multiple file upload under `files` param key
    @PostMapping(value = "/upload-multiValueMap", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<List<String>> uploadFileMap(@RequestBody Mono<MultiValueMap<String, Part>> filePartMap) {
        /*
          To see the response beautifully we are returning strings as Mono List
          of String. We could have returned Flux<String> from here.
          If you are curious enough then just return Flux<String> from here and
          see the response on Postman
         */
        return fileUploadService.getLinesFromMap(filePartMap).collectList();
    }
    
}