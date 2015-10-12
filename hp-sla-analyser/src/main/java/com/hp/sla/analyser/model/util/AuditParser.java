/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author ramirmal
 */
public class AuditParser extends ExcelParser {

    @Override
    public List parseDocument(XSSFSheet sheet) {
        List data = new ArrayList();
        // Get iterator to all the rows in current sheet 
        Iterator<Row> rowIterator = sheet.iterator();
        boolean dataStart = false;
        // Traversing over each row of XLSX file 
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            // For each row, iterate through each columns 
            Iterator<Cell> cellIterator = row.cellIterator();

            if (dataStart && row.getLastCellNum() > 1) {
                Audit au = new Audit();
                au.setFieldDisplayName(cellIterator.next().getStringCellValue());
                au.setFieldName(cellIterator.next().getStringCellValue());
                au.setIncidentID(cellIterator.next().getStringCellValue());
                if(cellIterator.next().getStringCellValue().equals("n"))
                    au.setLogicalDeleteFlag(false);
                else
                    au.setLogicalDeleteFlag(true);
                au.setNewVaueText(cellIterator.next().getStringCellValue());
                au.setPreviousValueText(cellIterator.next().getStringCellValue());
                au.setRecordNumber((int) cellIterator.next().getNumericCellValue());
                au.setSystemModifiedUser(cellIterator.next().getStringCellValue());
                au.setSystemModifiedTime(cellIterator.next().getDateCellValue());
                data.add(au);
            } else if (cellIterator.hasNext() && cellIterator.next().getStringCellValue().equals("Incident Audit Field Display Name")) {
                dataStart = true;
            }
        }
        if (!dataStart) {
            //The required columns was not foud
            throw new UnsupportedOperationException("Required columns not found");
        }
        return data;
    }

}
