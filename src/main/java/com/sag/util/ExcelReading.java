/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sag.util;



import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;




import java.io.FileInputStream;

import java.io.InputStream;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;


public class ExcelReading {


    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

 
    public static void main (String args[]) {
   
        importFromExcel("C:\\\\Earnings.xlsx");
    }
    


    public static void  importFromExcel(String filePath) {
        System.out.println("Reading the excel from folder");
        try (InputStream inputStream = new FileInputStream(filePath); 
            Workbook workbook = new XSSFWorkbook(inputStream)
            ) {


                Sheet sheet = workbook.getSheetAt(0);
                Row beginRow = sheet.getRow(1);
                Row endRow = sheet.getRow(2);
           
                System.out.println("@@@@ beginRow " + beginRow.getCell(1).getStringCellValue());
                System.out.println("@@@@ endRow " + endRow.getCell(1).getStringCellValue());
                
              
//                 LocalDate mnthStDt = getLocalDate(beginRow.getCell(1));
//                LocalDate mnthEndDt = getLocalDate(endRow.getCell(1));
                System.out.println("Beginning of the month: {}");
                System.out.println("End of the month: {}");
                for (int i = 6; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    
                    if (row == null || row.getCell(0) == null) {
                        continue;
                    }
                   if (row.getCell(2) != null && !row.getCell(2).getStringCellValue().isEmpty() ) {
                    System.out.println("+++++" + row.getCell(2));
                   }
                   
                   if (row.getCell(5) != null  ) {
                    System.out.println("+++++" + row.getCell(5));
                   }
//                    System.out.println("+++++" + row.getCell(4).getStringCellValue());
//                    System.out.println("+++++" + row.getCell(5).getStringCellValue());
//                    StgPlnStfDtl record = new StgPlnStfDtl();
//                    record.setMnthStDt(mnthStDt);
//                    record.setMnthEndDt(mnthEndDt);
//                    record.setSsn(getString(row.getCell(2)));
//                    record.setGrossPay(getBigDecimal(row.getCell(5)));
//                    record.setStats(getString(row.getCell(4)));
//                    record.setCreatedAt(LocalDateTime.now());
//                    record.setCreatedBy("SYSTEM");
//                    record.setUpdatedAt(LocalDateTime.now());
//                    record.setUpdatedBy("SYSTEM");
//                    System.out.println("Saving record: {}", record);
//                    repository.save(record);
                }


            } catch (Exception e) {
                       e.printStackTrace();
                    }

        }



    private  LocalDate getLocalDate(Cell cell) {
        if (cell == null) {
          
            return null;
        }


        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue().toLocalDate();
              } else {


                String value = cell.getStringCellValue().trim();
              
                return LocalDate.parse(value, dateFormatter);
            }


         } catch (Exception e) {
            System.out.println("Failed to parse date from cell '{}'" + e.getMessage());
            return null;
        
    

    }

}

private LocalDateTime getLocalDateTime(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(cell)) {
                return cell.getLocalDateTimeCellValue();
              } else {


                return LocalDateTime.parse(cell.getStringCellValue().trim(), dateTimeFormatter);
            }


         } catch (Exception e) {
            return null;
        
    

    }

}

private String getString(Cell cell) {
        if (cell == null) {
            return null;
        }
        return switch (cell.getCellType()) {

            case STRING ->
                cell.getStringCellValue().trim();

            case NUMERIC ->
                String.valueOf((long) cell.getNumericCellValue()).trim();

            case BOOLEAN ->
                String.valueOf(cell.getBooleanCellValue());

            default ->
                cell.toString().trim();

        
    

    };

}

private BigDecimal getBigDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            return new BigDecimal(cell.toString().trim());
         } catch (NumberFormatException e) {
            return null;
        
    

    }

}

private Integer getInteger(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            return (int) cell.getNumericCellValue();
         } catch (Exception e) {
            return null;
        
    

}

}

}
