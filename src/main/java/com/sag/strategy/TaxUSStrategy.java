/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.strategy;

import com.sag.model.v1.Client;
import com.sag.model.v1.Client.CountryCodeEnum;
import com.sag.model.v1.Invoice;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;


/**
 *
 * @author ahmed
 */
@Component("usCalculation")
public class TaxUSStrategy implements TaxStrategy {

    @Override
    public Invoice taxing(Invoice invoice) {
        invoice.setTax(BigDecimal.TEN);
        return invoice;    
    }

    @Override
    public Client.CountryCodeEnum countryCodeType() {
       return CountryCodeEnum.US;
    }
    
    
}
