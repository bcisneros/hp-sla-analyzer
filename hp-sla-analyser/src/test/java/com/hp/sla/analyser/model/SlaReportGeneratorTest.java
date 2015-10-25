package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParserTest;
import com.hp.sla.analyser.model.util.ExcelReader;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class for SlaReportGenerator class
 *
 * @author ramirmal
 */
public class SlaReportGeneratorTest {

    private SlaReportGenerator instance;

    @Before
    public void setUp() {
        instance = new SlaReportGenerator();
    }

    @Test
    public void testGenerateReport() {
        try {
            String resourcesLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\files\\";
            instance.generateReport(resourcesLocation + "testIncidents.xlsx", resourcesLocation + "testAssignmentGroupAudits.xlsx", "C:\\temp\\");
        } catch (SlaReportGenerationException ex) {
            fail("This exception was not expected:" + ex.getMessage());
        }
    }

    @Test()
    public void testPerformance() {
        try {
            long startTime, endTime, completeMillis, filteredMillis;
            String complete, filtered;
            String resourcesLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\files\\";
            startTime = System.currentTimeMillis();
            instance.generateReport(resourcesLocation + "Incidenttickets-ALLGFITFAIT.xlsx", resourcesLocation + "SSITRTBAGassignmentaudit.xlsx", "C:\\temp\\");
            endTime = System.currentTimeMillis();
            completeMillis = endTime - startTime;
            complete = timeFormat(startTime, endTime);
            Logger.getLogger(AuditParserTest.class.getName()).log(Level.INFO, "Elapsed Time Complete Registers:{0}", complete);

            startTime = System.currentTimeMillis();
            instance.generateReport(resourcesLocation + "Incidenttickets-ALLGFITFAIT - Filtered.xlsx", resourcesLocation + "SSITRTBAGassignmentaudit - Filtered.xlsx", "C:\\temp\\");
            endTime = System.currentTimeMillis();
            filteredMillis = endTime - startTime;

            filtered = timeFormat(startTime, endTime);
            Logger.getLogger(AuditParserTest.class.getName()).log(Level.INFO, "Elapsed Time Filtered Registers: {0}", filtered);
            Assert.assertTrue("Complete time is longer than filetered time: Complete time = " + complete + " | Filtered time = " + filtered, completeMillis > filteredMillis);
        } catch (SlaReportGenerationException ex) {
            fail("This exception was not expected:" + ex);
            System.err.println(ex.getMessage());
        }
    }

    private String timeFormat(long startTime, long endTime) {
        SimpleDateFormat time = new SimpleDateFormat("mm:ss.SSS");
        return time.format(new Date(endTime - startTime));
    }

    /**
     * Test of generateReport method, of class SlaReportGenerator.
     */
    @Test
    public void testIntegrateIncidents() {
        final TestIncidentBuilder incidentBuilder = TestIncidentBuilder.getInstance();
        Incident incidentWithOneAudit = incidentBuilder.id("OneAudit").build();
        Audit auditOne = new Audit();
        auditOne.setIncidentID(incidentWithOneAudit.getId());

        Incident incidentWithZeroAudits = new Incident();
        incidentWithZeroAudits.setId("ZeroAudits");

        Incident incidentWithThreeAudits = new Incident();
        incidentWithThreeAudits.setId("ThreeAudits");
        Audit auditForIncidentWithThreeAudits = new Audit();
        auditForIncidentWithThreeAudits.setIncidentID("ThreeAudits");

        List<Incident> incidentsToIntegrate = new ArrayList<>();
        List<Audit> auditsToIntegrate = new ArrayList<>();
        incidentsToIntegrate.add(incidentWithOneAudit);
        incidentsToIntegrate.add(incidentWithZeroAudits);
        incidentsToIntegrate.add(incidentWithThreeAudits);

        auditsToIntegrate.add(auditOne);
        auditsToIntegrate.add(auditForIncidentWithThreeAudits);
        auditsToIntegrate.add(auditForIncidentWithThreeAudits);
        auditsToIntegrate.add(auditForIncidentWithThreeAudits);

        List<Incident> integratedIncidents = instance.integrateIncidents(incidentsToIntegrate, auditsToIntegrate);
        assertNotNull("The integrated incidents list must not be null.", integratedIncidents);
        assertEquals("Must be 3 elements in the list.", 3, integratedIncidents.size());
        assertSame("The first element must be the incident with one audit.", incidentWithOneAudit, integratedIncidents.get(0));
        assertEquals("Must be exactly one audit for the first incident", 1, integratedIncidents.get(0).getAudits().size());
        assertSame("The second element must be the incident with zero audits.", incidentWithZeroAudits, integratedIncidents.get(1));
        assertEquals("Must be exactly zero audits for the second incident", 0, integratedIncidents.get(1).getAudits().size());
        assertSame("The third element must be the incident with three audits.", incidentWithThreeAudits, integratedIncidents.get(2));
        assertEquals("Must be exactly three audits for the third incident", 3, integratedIncidents.get(2).getAudits().size());
    }

    @Test
    public void testGenerateWorkbook() {
        try {
            instance.generateWorkbook(dummyReportDetails());
            final Workbook workBook = ExcelReader.read(new FileInputStream(instance.getGeneratedReportFile()));
            assertNotNull(workBook);
            final Sheet firstSheet = workBook.getSheetAt(0);
            assertNotNull(firstSheet);
            final Sheet secondSheet = workBook.getSheetAt(1);
            assertNotNull(secondSheet);
        } catch (Exception ex) {
            fail("No exception is expected here: " + ex.getMessage());
        }
    }

    private List<ReportDetail> dummyReportDetails() {
        List<ReportDetail> data = TestReportDetailBuilder.getInstance().buildList(1000);
        return data;
    }

}
