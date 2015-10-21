package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParserTest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
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
    @Ignore
    public void testGenerateReport() {
        try {
            System.out.println("user.dir:");
            System.out.println(System.getProperty("user.dir"));
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
            fail("This exception was not expected:");
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
        Incident incidentWithOneAudit = new Incident();
        incidentWithOneAudit.setId("OneAudit");
        Audit auditOne = new Audit();
        auditOne.setIncidentID("OneAudit");

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
        } catch (Exception ex) {
            fail("No exception is expected here: " + ex.getMessage());
        }
    }

    private ReportDetail getTestReportDetail1() {
        return new ReportDetail() {

            @Override
            public String getErrorMessage() {
                return null;
            }

            @Override
            public Exception getDetailException() {
                return null;
            }

            @Override
            public String getIncidentConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name() {
                return "L4";
            }

            @Override
            public String getConfigurationItemEnvironmentDescription() {
                return "test";
            }

            @Override
            public String getConfigurationItemStatusDescription() {
                return "active";
            }

            @Override
            public String getIncidentClosedAssignmentGroupSupportLevelDescription() {
                return "L1";
            }

            @Override
            public String getIncidentCurrentAssignmentGroupSupportLevelDescription() {
                return "L3";
            }

            @Override
            public String getIncidentAssigneeOrganizationUnitName() {
                return "Test Organization Unit Name";
            }

            @Override
            public String getIncidentAssigneeManagerEmailName() {
                return "test@test.com";
            }

            @Override
            public String getIncidentAssigneeEmailName() {
                return "test@test.com";
            }

            @Override
            public String getIncidentOpenedByEmailName() {
                return "test@test.com";
            }

            @Override
            public double getIncidentAgingDurationInDays() {
                return 100;
            }

            @Override
            public String getIncidentCurrentStatusDescription() {
                return "test status";
            }

            @Override
            public String getIncidentCurrentStatusPhaseDescription() {
                return "test status";
            }

            @Override
            public String getIncidentPriorityDescription() {
                return "high";
            }

            @Override
            public String getIncidentCriticalityDescription() {
                return "Mission Critical";
            }

            @Override
            public long getRelatedRootConfigurationItemApplicationPortfolioIdentifier() {
                return 120;
            }

            @Override
            public String getRelatedRootConfigurationItemBusinessFriendlyName() {
                return "test";
            }

            @Override
            public String getConfigurationItemLogicalName() {
                return "test-ci";
            }

            @Override
            public String getIncidentAuditNewValueText() {
                return "TEST-AG";
            }

            @Override
            public String getIncidentCurrentAssignmentGroupName() {
                return "TEST-AG";
            }

            @Override
            public Double getIncidentTimeToFixDurationHours() {
                return 0.12d;
            }

            @Override
            public String getBurnedOutComplianceString() {
                return "yes";
            }

            @Override
            public Date getIncidentAuditSystemModifiedTime() {
                return new Date(1234567890);
            }

            @Override
            public Timestamp getIncidentClosedTimestamp() {
                return Timestamp.valueOf("2015-01-01 00:00:00");
            }

            @Override
            public Timestamp getIncidentCreatedTimestamp() {
                return Timestamp.valueOf("2015-01-01 00:00:00");
            }

            @Override
            public boolean isBurnedOut() {
                return false;
            }

            @Override
            public boolean isCompliantWithSLA() {
                return true;
            }

            @Override
            public String getIncidentIdentifier() {
                return "IM0000001";
            }

            @Override
            public Incident getIncident() {
                return new Incident();
            }

        };
    }

    private List<ReportDetail> dummyReportDetails() {
        List<ReportDetail> data = new LinkedList();
        ReportDetail rd = new ReportDetail();
        rd.setBurnedOut(true);
        Incident i = new Incident();
        i.setId("IM0005");

        i.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00"));
        rd.setIncident(i);

        data.add(getTestReportDetail1());

        rd = new ReportDetail();
        rd.setBurnedOut(true);
        i = new Incident();
        i.setId("IM0006");
        i.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00"));
        i.setCloseTimestamp(Timestamp.valueOf("2015-01-01 15:34:12"));
        rd.setIncident(i);

        data.add(rd);

        rd = new ReportDetail();
        rd.setBurnedOut(false);
        i = new Incident();
        i.setId("IM0007");
        i.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00"));
        i.setCloseTimestamp(Timestamp.valueOf("2015-01-01 23:15:12"));
        rd.setIncident(i);

        data.add(rd);
        return data;
    }

}
