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
 * This class writes a workbook to an excel file
 *
 * @author Mallinali Ramirez Corona
 */
public class ExcelWritter {

    final static Logger logger = Logger.getLogger(ExcelWritter.class);

    /**
     * Write to an excel document
     *
     * @param wb The Workbook object to write
     * @param fileName the name of the file
     * @return
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public String write(Workbook wb, String fileName) throws FileNotFoundException, IOException {
        FileOutputStream out = null;
        final String file = fileName + ".xlsx";
        logger.debug(file);
        out = new FileOutputStream(file);
        if (wb == null) {
            wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("None");
            Row headerRow = sheet.createRow(0);
            Cell cell = headerRow.createCell(0);
            cell.setCellValue("No data to display");
        }
        wb.write(out);
        if (out != null) {
            out.close();
        }

        return file;
    }
}
