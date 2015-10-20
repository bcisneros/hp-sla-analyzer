package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import com.hp.sla.analyser.util.ResourcesUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author ramirmal
 */
public class AuditParserTest {

    /**
     * Test of parseDocument method, of class AuditParser.
     *
     */
    @Test
    public void testParseDocument() {
        try {
            Sheet sheet = (XSSFSheet) ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/SSITRTBAGassignmentaudit.xlsx")).getSheetAt(0);
            AuditParser instance = new AuditParser();
            List<Audit> result = instance.parseDocument(sheet);
            final int EXPECTED_AUDITS = 3040;
            assertNotNull("The resulting List must be not null", result);
            assertEquals(EXPECTED_AUDITS + " audits were expected", EXPECTED_AUDITS, result.size());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyy HH:mm:ss");

            Audit firstAudit = new Audit();
            firstAudit.setFieldDisplayName("Assignment Group");
            firstAudit.setFieldName("assignment");
            firstAudit.setIncidentID("IM20941354");
            firstAudit.setLogicalDeleteFlag(false);
            firstAudit.setNewVaueText("W-INCLV3-HPIT-BLUES-APPLICATIONS");
            firstAudit.setPreviousValueText("W-INCFLS-HPIT-MONITORING-RESTORATION");
            firstAudit.setRecordNumber(0);
            firstAudit.setSystemModifiedUser("HPOO");
            firstAudit.setSystemModifiedTime(simpleDateFormat.parse("09/17/2015 18:44:38"));

            Audit lastAudit = new Audit();
            lastAudit.setFieldDisplayName("Assignment Group");
            lastAudit.setFieldName("assignment");
            lastAudit.setIncidentID("IM20929991");
            lastAudit.setLogicalDeleteFlag(false);
            lastAudit.setNewVaueText("W-INCFLS-HPIT-BIZAPPS-CORP-FUNCTIONS");
            lastAudit.setPreviousValueText("null");
            lastAudit.setRecordNumber(0);
            lastAudit.setSystemModifiedUser("HPOO");
            lastAudit.setSystemModifiedTime(simpleDateFormat.parse("09/16/2015 00:08:23"));

            assertEquals("The first element must be the first Audit", firstAudit, result.get(0));
            assertEquals("The last element must be the last Audit", lastAudit, result.get(result.size() - 1));
        } catch (IOException | ParseException ex) {
            fail("Exception is not expected: " + ex);
        }

    }

}
