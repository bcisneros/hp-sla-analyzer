/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 *
 * @author ramirmal
 * @param <T> AuditParser or IncidentParser
 */
public abstract class ExcelParser<T> {
    public abstract List<T> parse(XSSFSheet sheet);
    
    public List<T> parseDocument(XSSFSheet sheet,  String firstColumnName){
        List data=new ArrayList();
        // Get iterator to all the rows in current sheet 
        Iterator<Row> rowIterator = sheet.iterator();
        boolean dataStart=false;
        // Traversing over each row of XLSX file 
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns 
            Iterator<Cell> cellIterator = row.cellIterator();
            
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(dataStart){
                    data.add(null);
                }else if(cell.getStringCellValue().equals(firstColumnName)){
                    dataStart=true;
                }
            }
        }
        if(!dataStart){
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        return data;
    }
}