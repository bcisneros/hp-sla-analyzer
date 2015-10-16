package com.hp.sla.analyser.model.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ramirmal
 */
public class ExcelWritter {

    final static Logger logger = Logger.getLogger(ExcelWritter.class);

    /**
     * Write to an excel document
     *
     * @param wb The Workbook object to write
     * @param fileName the name of the file
     * @throws java.lang.IllegalAccessException
     */
    public void write(Workbook wb, String fileName) throws IllegalArgumentException, IllegalAccessException {

        // Write the output to a file
        FileOutputStream out;
        try {
            out = new FileOutputStream(fileName + ".xlsx");
            if (wb == null) {
                wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("None");
                Row headerRow = sheet.createRow(0);
                Cell cell = headerRow.createCell(0);
                cell.setCellValue("No data to display");
            }
            wb.write(out);

            out.close();
        } catch (FileNotFoundException ex) {
            logger.error("File not found.", ex);
        } catch (IOException ex) {
            logger.error("I/O Error.", ex);
        }
    }
}
