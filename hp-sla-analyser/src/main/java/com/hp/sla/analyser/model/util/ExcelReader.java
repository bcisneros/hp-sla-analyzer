package com.hp.sla.analyser.model.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.log4j.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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

    public XSSFSheet read() throws IOException {
        
        logger.info("Started reading the file ");
        XSSFWorkbook workbook = new XSSFWorkbook(inputFile);
        XSSFSheet sheet = workbook.getSheetAt(0);
        return sheet;
    }
}
