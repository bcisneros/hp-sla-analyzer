package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.AuditParserTest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author ramirmal
 */
public class SlaReportGeneratorTest {

    /**
     * Test of generateReport method, of class SlaReportGenerator.
     */
    @Test
    public void testIntegrateIncidents() {
        List<Audit> audits = testAudits();
        List<Incident> incidents = testIncidents();

        SlaReportGenerator instance = new SlaReportGenerator();
        List<Incident> result = instance.integrateIncidents(incidents, audits);

        Logger.getLogger(AuditParserTest.class.getName()).log(Level.INFO, "{0}Integrated Incidents: ", result);
        assertEquals("Audits per incident " + result.get(0).getId(), result.get(0).getAudits().size(), 0);
        assertEquals("Audits per incident " + result.get(1).getId(), result.get(1).getAudits().size(), 1);
        assertEquals("Audits per incident " + result.get(2).getId(), result.get(2).getAudits().size(), 2);
    }

    private List<Incident> testIncidents() {
        List<Incident> incidents = new LinkedList();

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

    private List<Audit> testAudits() {
        List<Audit> audits = new LinkedList();

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

    @Test
    public void testGenerateWorkbook() {
        SlaReportGenerator instance = new SlaReportGenerator();
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

}
