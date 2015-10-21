package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;

/**
 * This class parses the audit document to obtain an Audit List
 *
 * @author ramirmal
 */
public class AuditParser extends ExcelParser<Audit> {
    
    public AuditParser(){
        this.startColumnName = "Incident Audit Field Display Name";
    }

    @Override
    public Audit createObject(Iterator<Cell> cellIterator) {
        Audit au = new Audit();
        au.setFieldDisplayName(cellIterator.next().getStringCellValue());
        au.setFieldName(cellIterator.next().getStringCellValue());
        au.setIncidentID(cellIterator.next().getStringCellValue());
        if (cellIterator.next().getStringCellValue().equals("n")) {
            au.setLogicalDeleteFlag(false);
        } else {
            au.setLogicalDeleteFlag(true);
        }
        au.setNewVaueText(cellIterator.next().getStringCellValue());
        au.setPreviousValueText(cellIterator.next().getStringCellValue());
        au.setRecordNumber((int) cellIterator.next().getNumericCellValue());
        au.setSystemModifiedUser(cellIterator.next().getStringCellValue());
        au.setSystemModifiedTime(cellIterator.next().getDateCellValue());
        return au;
    }   
}
