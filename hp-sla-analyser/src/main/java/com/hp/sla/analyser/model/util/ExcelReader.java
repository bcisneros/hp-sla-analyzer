package com.hp.sla.analyser.model.util;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Provide a method to read an Excel file from an InputStream
 *
 * @author Benjamin Cisneros Barraza
 */
public class ExcelReader {

    final static Logger logger = Logger.getLogger(ExcelReader.class);

    public static Workbook read(FileInputStream inputFile) throws IOException {
        return new XSSFWorkbook(inputFile);
    }
}
