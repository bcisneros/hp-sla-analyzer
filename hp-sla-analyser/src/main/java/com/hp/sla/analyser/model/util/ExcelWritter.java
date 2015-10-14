/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.ReportDetail;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
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

    /**
     * Write to an excel document
     * @param titles the titles of the columns 
     * @param data the data 
     * @param fileName the name of the file
     */
    public void write(String[] titles, List<ReportDetail> data, String fileName) throws IllegalArgumentException, IllegalAccessException {
        Sheet sheet = wb.createSheet("Report");

        Map<String, CellStyle> styles = createStyles(wb);
        //Write header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(styles.get("header"));
        }
        
        //freeze the first row
        sheet.createFreezePane(0, 1);

        //Write the data
        Row row;
        Cell cell;
        int rownum = 1;
        for (int i = 0; i < data.size(); i++, rownum++) {
            row = sheet.createRow(rownum);
            
            ReportDetail repDet = data.get(i);
            Field[] fs = repDet.getClass().getSuperclass().getDeclaredFields();
            int j=0;
            for (Field field : fs)
            {
                cell = row.createCell(j);
                cell.setCellValue(field.get(repDet)+"");
                cell.setCellStyle(styles.get("body"));
                j++;
            }
        }

        // Write the output to a file
        FileOutputStream out;
        try {
            out = new FileOutputStream(fileName+".xlsx");
            wb.write(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExcelWritter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWritter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();

        CellStyle style = wb.createCellStyle();
        Font headerFont = wb.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);

        style = wb.createCellStyle();
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("body", style);
        return styles;
    }

}
