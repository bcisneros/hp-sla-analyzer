package com.hp.sla.analyser.model.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Provide a method to read an Excel file from an InputStream
 *
 * @author Benjamin Cisneros Barraza
 */
public class ExcelReader {

    /**
     * Reads an FileInputStream and gets the Workbook object
     *
     * @param inputFile The file source of the XLSX file
     * @return The Workbook object
     * @throws IOException If there is an error while reading or creating the
     * XLSX file
     */
    public static Workbook read(InputStream inputFile) throws IOException {
        return new XSSFWorkbook(inputFile);
    }
}
