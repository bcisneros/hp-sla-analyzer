package com.hp.sla.analyser.model.util;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class ExcelReader {

    private FileInputStream inputFile;
    final static Logger logger = Logger.getLogger(ExcelReader.class);

    public void setInputFile(FileInputStream inputFile) {
        this.inputFile = inputFile;
    }

    public Workbook read() throws IOException {

        return new XSSFWorkbook(inputFile);
    }
}
