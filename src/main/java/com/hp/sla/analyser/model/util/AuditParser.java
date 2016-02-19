package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;

/**
 * This class parses the audit excel document to obtain an Audit List
 *
 * @author Mallinali Ramirez Corona
 */
public class AuditParser extends ExcelParser<Audit> {

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

        Date systemModifiedTime = cellIterator.next().getDateCellValue();
        if (systemModifiedTime != null) {
            au.setSystemModifiedTime(new Timestamp(systemModifiedTime.getTime()));
        }
        return au;
    }

    @Override
    public String getStartDataIndicator() {
        return "Incident Audit Field Display Name";
    }
}
