package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParserTest;
import com.hp.sla.analyser.model.util.ExcelReader;

import static org.junit.Assert.*;

import java.io.File;
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
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test class for SlaReportGenerator class
 *
 * @author Mallinali Ramirez Corona
 */
public class SlaReportGeneratorTest {

    private SlaReportGenerator instance;

    @Before
    public void setUp() {
        instance = new SlaReportGenerator();
    }

    @Test
    public void generateReport_WithValidEntryReports_ShouldGenerateReportFile() throws Exception {
    	String resourcesLocation = System.getProperty("user.dir") + "\\src\\test\\resources\\files\\";
        String testDestinationPath = "C:\\temp\\";
		instance.generateReport(resourcesLocation + "testIncidents.xlsx", resourcesLocation + "testAssignmentGroupAudits.xlsx", testDestinationPath);
		File file = new File(instance.getGeneratedReportFile());
		assertTrue(file.exists());
    }
    
    @Test(expected=SlaReportGenerationException.class)
	public void generateReport_WithInvalidIncidentFiles_ShouldThrowAnException() throws Exception {
		instance.generateReport(null, null, null);
	}
    
    @Test(expected=SlaReportGenerationException.class)
	public void generateReport_WithInvalidAuditFiles_ShouldThrowAnException() throws Exception {
		instance.generateReport("incidents.xlsx", null, null);
	}
    
    @Test(expected=SlaReportGenerationException.class)
	public void generateReport_WithInvalidOutputDirectory_ShouldThrowAnException() throws Exception {
		instance.generateReport("incidents.xlsx", "audits.xlsx", null);
	}
    
    @Test(expected=SlaReportGenerationException.class)
	public void generateReport_WithInvalidFiles_ShouldThrowAnException() throws Exception {
		instance.generateReport("incidents.xlsx", "audits.xlsx", "C:\\temp\\");
	}
    

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
    public void testGenerateWorkbook() throws Exception {
    	instance.generateWorkbook(dummyReportDetails());
    	final Workbook workBook = ExcelReader.read(new FileInputStream(instance.getGeneratedReportFile()));
    	assertNotNull(workBook);
    	final Sheet firstSheet = workBook.getSheetAt(0);
    	assertNotNull(firstSheet);
    	final Sheet secondSheet = workBook.getSheetAt(1);
    	assertNotNull(secondSheet);
    }

    private List<ReportDetail> dummyReportDetails() {
        List<ReportDetail> data = TestReportDetailBuilder.getInstance().buildList(10);
        data.get(0).setDetailException(new Exception("Dummy Exception"));
        return data;
    }

}
