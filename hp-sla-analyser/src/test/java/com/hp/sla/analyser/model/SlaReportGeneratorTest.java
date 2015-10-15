/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParserTest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author ramirmal
 */
public class SlaReportGeneratorTest {
    
    public SlaReportGeneratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of generateReport method, of class SlaReportGenerator.
     */
    @Test
    public void testIntegrateIncidents() {
        List<Audit> audits = testAudits();
        List<Incident> incidents = testIncidents();
        
        SlaReportGenerator instance = new SlaReportGenerator();
        List<Incident> result=instance.integrateIncidents(incidents, audits);
        
        Logger.getLogger(AuditParserTest.class.getName()).log(Level.INFO, "{0}Integrated Incidents: ", result);
        assertEquals("Audits per incident "+ result.get(0).getId(), result.get(0).getAudits().size(), 0);
        assertEquals("Audits per incident "+ result.get(1).getId(), result.get(1).getAudits().size(), 1);
        assertEquals("Audits per incident "+ result.get(2).getId(), result.get(2).getAudits().size(), 2);
    }
    
    private List<Incident> testIncidents(){
        List<Incident> incidents=new LinkedList();
        
        Incident incident = new Incident();
        incident.setId("IM0001");
       
        incidents.add(incident);
        
        incident = new Incident();
        incident.setId("IM0010");
       
        incidents.add(incident);
        
        incident = new Incident();
        incident.setId("IM0005");
       
        incidents.add(incident);
        
        return incidents;
    }
    
    private List<Audit> testAudits(){
        List<Audit> audits=new LinkedList();
        
        Audit audit = new Audit();
        audit.setNewVaueText("ANOTHER-NON-APLYABLE-AG");
        audit.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 00:30:00.00"));
        audit.setIncidentID("IM0005");
        
        audits.add(audit);
        
        audit = new Audit();
        audit.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 01:30:00.00"));
        audit.setIncidentID("IM0010");
        
        audits.add(audit);
        
        audit = new Audit();
        audit.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 07:56:00.00"));
        audit.setIncidentID("IM0010");
        
        audits.add(audit);
        return audits;
    }
     private List<ReportDetail> dummyReportDetails(){
        List<ReportDetail> data=new LinkedList();
        Date date= new Date();
        
        ReportDetail rd=new ReportDetail();
         rd.setBurnedOut(true);
        Incident i= new Incident();
        i.setId("IM0005");
 
        i.setCreationTimestamp(new Timestamp(date.getTime()));
        rd.setIncident(i);
        
        data.add(rd);
        
        rd=new ReportDetail();
        rd.setBurnedOut(true);
        i= new Incident();
        i.setId("IM0006");
        i.setCreationTimestamp(new Timestamp(date.getTime()));
        rd.setIncident(i);
        
        data.add(rd);
        
        rd=new ReportDetail();
         rd.setBurnedOut(false);
        i= new Incident();
        i.setId("IM0007");
        i.setCreationTimestamp(new Timestamp(date.getTime()));
        rd.setIncident(i);
        
        data.add(rd);
        return data;
    }
    
     @Test
    public void testGenerateWorkbook() throws IllegalArgumentException, IllegalAccessException{
        System.out.println("Testing generate Report");
        SlaReportGenerator instance = new SlaReportGenerator();
        instance.generateWorkbook(dummyReportDetails());
    }
    
    
    /**
     * Test of generateReport method, of class SlaReportGenerator.
     * @throws java.lang.Exception
     */
    @Test
    @Ignore
    public void testGenerateReport() throws Exception {
        String incidentFile = "";
        String auditFile = "";
        String d = "";
        SlaReportGenerator instance = new SlaReportGenerator();
        instance.generateReport(incidentFile, auditFile, d);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
   
    
}
