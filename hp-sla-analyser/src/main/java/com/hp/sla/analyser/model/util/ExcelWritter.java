package com.hp.sla.analyser.model.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ramirmal
 */
public class ExcelWritter {

    Workbook wb = new XSSFWorkbook();
    Map<String, CellStyle> styles;

    /**
     * Write to an excel document
     * @param titles the titles of the columns 
     * @param data the data 
     * @param fileName the name of the file
     * @throws java.lang.IllegalAccessException
     */
    public void write(Workbook wb, String fileName) throws IllegalArgumentException, IllegalAccessException {

        // Write the output to a file
        FileOutputStream out;
        try {
            out = new FileOutputStream(fileName+".xlsx");
            if(wb==null)
            {
                wb=new XSSFWorkbook();
                Sheet sheet = wb.createSheet("None");
                Row headerRow = sheet.createRow(0);
                Cell cell = headerRow.createCell(0);
                cell.setCellValue("No data to display");                
            }
            wb.write(out);
            
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelWritter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWritter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
