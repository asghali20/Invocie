
package com.sag.util;

/**
 *
 * @author ahmed
 */
import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

import java.io.IOException;

import java.io.InputStream;

public class ReadingExcel {

    public static void main(String[] args) {
  
        String filePath = "C:\\\\Earnings.xlsx";
        try (InputStream inputStream = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0); // Read first sheet

            for (Row row : sheet) {

                for (Cell cell : row) {

                    switch (cell.getCellType()) {

                        case STRING:

                            System.out.print(cell.getStringCellValue() + "\t");

                            break;

                        case NUMERIC:

                            if (DateUtil.isCellDateFormatted(cell)) {

                                System.out.print(cell.getDateCellValue() + "\t");

                            } else {

                                System.out.print(cell.getNumericCellValue() + "\t");

                            }

                            break;

                        case BOOLEAN:

                            System.out.print(cell.getBooleanCellValue() + "\t");

                            break;

                        case FORMULA:

                            System.out.print(cell.getCellFormula() + "\t");

                            break;

                        default:

                            System.out.print(" \t");

                    }

                }

                System.out.println();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

}
