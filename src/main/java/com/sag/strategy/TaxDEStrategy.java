/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.strategy;

import com.sag.model.v1.Client;
import com.sag.model.v1.Invoice;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 *
 * @author ahmed
 */
@Component("deCalculation")
public class TaxDEStrategy implements TaxStrategy {

    @Override
    public Invoice taxing(Invoice invoice) {
        invoice.setTax(BigDecimal.valueOf(15.00));
        return invoice;    
    }

    @Override
    public Client.CountryCodeEnum countryCodeType() {
       return Client.CountryCodeEnum.DE;
    }
    
}
