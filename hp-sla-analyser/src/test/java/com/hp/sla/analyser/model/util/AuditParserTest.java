package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import com.hp.sla.analyser.util.ResourcesUtil;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Mallinali Ramirez Corona
 */
public class AuditParserTest extends ExcelParserTest<Audit> {

    @Override
    protected Object getLastElementExpected() {
        Audit lastAudit = new Audit();
        lastAudit.setFieldDisplayName("Assignment Group");
        lastAudit.setFieldName("assignment");
        lastAudit.setIncidentID("IM20929991");
        lastAudit.setLogicalDeleteFlag(false);
        lastAudit.setNewVaueText("W-INCFLS-HPIT-BIZAPPS-CORP-FUNCTIONS");
        lastAudit.setPreviousValueText("null");
        lastAudit.setRecordNumber(0);
        lastAudit.setSystemModifiedUser("HPOO");
        lastAudit.setSystemModifiedTime(Timestamp.valueOf("2015-09-16 00:08:23"));
        return lastAudit;
    }

    @Override
    protected Object getFirstElementExpected() {
        Audit firstAudit = new Audit();
        firstAudit.setFieldDisplayName("Assignment Group");
        firstAudit.setFieldName("assignment");
        firstAudit.setIncidentID("IM20941354");
        firstAudit.setLogicalDeleteFlag(false);
        firstAudit.setNewVaueText("W-INCLV3-HPIT-BLUES-APPLICATIONS");
        firstAudit.setPreviousValueText("W-INCFLS-HPIT-MONITORING-RESTORATION");
        firstAudit.setRecordNumber(0);
        firstAudit.setSystemModifiedUser("HPOO");
        firstAudit.setSystemModifiedTime(Timestamp.valueOf("2015-09-17 18:44:38"));
        return firstAudit;
    }

    @Override
    protected int getExpectedSize() {
        return 3040;
    }

    @Override
    protected Sheet getSheetToTest() {
        try {
            return (XSSFSheet) ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/SSITRTBAGassignmentaudit.xlsx")).getSheetAt(0);
        } catch (IOException ex) {
            fail("This exception is not expected: " + ex);
        }
        return null;
    }

    @Override
    protected ExcelParser<?> getInstance() {
        return new AuditParser();
    }

    @Test
    public void testCreateObject() {
        Audit auditExpected = getDefaultAudit();
        AuditParser auditParser = (AuditParser) this.getInstance();

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCreationHelper createHelper = wb.getCreationHelper();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(
                createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm:ss"));

        Row row = wb.createSheet().createRow(0);
        int i = 0;
        row.createCell(i++).setCellValue(auditExpected.getFieldDisplayName());
        row.createCell(i++).setCellValue(auditExpected.getFieldName());
        row.createCell(i++).setCellValue(auditExpected.getIncidentID());
        if (auditExpected.getLogicalDeleteFlag()) {
            row.createCell(i++).setCellValue("y");
        } else {
            row.createCell(i++).setCellValue("n");
        }
        row.createCell(i++).setCellValue(auditExpected.getNewVaueText());
        row.createCell(i++).setCellValue(auditExpected.getPreviousValueText());
        row.createCell(i++).setCellValue(0);
        row.createCell(i++).setCellValue(auditExpected.getSystemModifiedUser());
        row.createCell(i++).setCellValue(auditExpected.getSystemModifiedTime());
        row.getCell(i - 1).setCellStyle(cellStyle);

        Iterator<Cell> iterator = row.iterator();
        Audit audit = auditParser.createObject(iterator);
        assertNotNull(audit);
        assertEquals(auditExpected, audit);
    }

    private Audit getDefaultAudit() {
        Audit defaultAudit = new Audit();
        defaultAudit.setFieldDisplayName("Assignment Group");
        defaultAudit.setFieldName("assignment");
        defaultAudit.setIncidentID("IM20941354");
        defaultAudit.setLogicalDeleteFlag(false);
        defaultAudit.setNewVaueText("W-INCLV3-HPIT-BLUES-APPLICATIONS");
        defaultAudit.setPreviousValueText("W-INCFLS-HPIT-MONITORING-RESTORATION");
        defaultAudit.setSystemModifiedUser("HPOO");
        defaultAudit.setSystemModifiedTime(Timestamp.valueOf("2015-09-17 18:44:38"));
        return defaultAudit;
    }
}
