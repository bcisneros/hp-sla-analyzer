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

    public void read() throws IOException {
        Workbook w;

        try {
            logger.info("Startig reading the file ");
            XSSFWorkbook myWorkBook = new XSSFWorkbook(inputFile);
            // Return first sheet from the XLSX workbook XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Get iterator to all the rows in current sheet 
            Iterator<Row> rowIterator = mySheet.iterator();
            // Traversing over each row of XLSX file 
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                // For each row, iterate through each columns 
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                    }
                }
            }

        } catch (Exception e) {
            logger.error("An error", e);
        }

    }
}
