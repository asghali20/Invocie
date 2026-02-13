package com.sag.service;


import com.sag.model.v1.Client;
import com.sag.model.v1.Invoice;
import com.sag.model.v1.LineItem;
import com.sag.repo.ApiRepository;
import com.sag.strategy.TaxDEStrategy;
import com.sag.strategy.TaxStrategy;
import com.sag.strategy.TaxUSStrategy;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/*
 * Sample service to demonstrate what the API would use to get things done
 */
@Service
public class ApiService { // implements TaxStrategy {

  

    @Autowired
    private ApiRepository apiRepository;

 
    Map<String,TaxStrategy>  taxCalculations;
    
    

    public ApiService() {
    }

    public Boolean createClient(Client client) {
        return apiRepository.addClient(client);
    }

   public Client retriveClient(String id) {
        Client client =  apiRepository.getClient(id);
        return client;
    }
    
            
    public Boolean createInvoice(Invoice invoice) {
        return apiRepository.addInvoice(invoice);
    }
    
     public Invoice finalizeInvoice(String id) {
        
        Invoice invoice =  apiRepository.getInvoice(id);
        if (invoice != null) {  
            BigDecimal total = new BigDecimal("0.0");

             if (invoice.getLineItems() != null && invoice.getLineItems().size() >0) {
                    for(LineItem item:  invoice.getLineItems()) {  
                        total = total.add( item.getQuantity().multiply(item.getUnitPrice())) ;
                    }
             }
            invoice.setTotal(total);
            invoice.setStatus(Invoice.StatusEnum.FINALIZED);
            apiRepository.addInvoice(invoice);
        }
        return invoice;
    }
    
    public Invoice paidInvoice(String id) {
        Invoice invoice =  apiRepository.getInvoice(id);
        if (invoice != null) {
            invoice.setStatus(Invoice.StatusEnum.PAID);
        }
        return invoice;
    }
    
    public Invoice retriveInvoice(String id) {
        
        Invoice invoice =  apiRepository.getInvoice(id);
        try {
            Client client = apiRepository.getClient(invoice.getClientId());
        
            
            TaxStrategy strategy = this.taxCalculations.get("deCalculation");
            invoice = strategy.taxing(invoice);
            
//            if (invoice != null && client != null) {
//                if (client.getCountryCode().equals(Client.CountryCodeEnum.US)) {
//                    invoice = new TaxUSStrategy().taxing(invoice);
//                } else if (client.getCountryCode().equals(Client.CountryCodeEnum.DE)) {
//                    invoice = new TaxDEStrategy().taxing(invoice);
//                }
//                    invoice.setSubTotal(invoice.getTotal());
//                    invoice.setTotal(invoice.getTax().add(invoice.getSubTotal()));  
//            }
        }catch (Exception e) {
            
        }
        return invoice;
    }


    
    
    public List<Invoice> getInvoicesReport(String clientId) {
       return apiRepository.getInvoicesForClient(clientId);
    }
    
   
}
