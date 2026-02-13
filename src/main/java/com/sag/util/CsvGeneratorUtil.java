/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.util;

import com.sag.model.v1.Client;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author ahmed
 */
@Component
public class CsvGeneratorUtil {
    private static final String CSV_HEADER = "ID,Name,Country\n";

    public String generateCsv(List<Client> clients) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append(CSV_HEADER);

        for (Client c : clients) {
            csvContent.append(c.getId()).append(",")
                      .append(c.getName()).append(",")
                      .append(c.getCountryCode()).append(",");
                     
        }

        return csvContent.toString();
    }
}