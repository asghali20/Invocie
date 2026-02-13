/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.strategy;

import com.sag.model.v1.Client.CountryCodeEnum;
import com.sag.model.v1.Invoice;
import java.math.BigDecimal;

/**
 *
 * @author ahmed
 */
public interface TaxStrategy {
     Invoice taxing(Invoice invoice);
     
     CountryCodeEnum countryCodeType();
}
