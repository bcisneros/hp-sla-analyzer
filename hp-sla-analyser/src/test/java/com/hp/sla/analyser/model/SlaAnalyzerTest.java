package com.hp.sla.analyser.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyzerTest {

    private SlaAnalyzer instance;

    public SlaAnalyzerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        instance = new SlaAnalyzer();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of analyze method, of class SlaAnalyzer.
     */
    @Test
    @Ignore(value = "At this moment is not implemented a test logic here")
    public void testAnalyze() {
        List<Incident> incidents = new ArrayList<>();
        Incident incident1 = getCompliantWithSLAIncident();
        incidents.add(incident1);
        List<ReportDetail> result = instance.analyze(incidents);
        assertNotNull("The result must not be null", result);
        assertFalse("The result list must not be empty", result.isEmpty());
        assertSame("Need to be the same object", incident1, result.get(0).getIncident());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnalyzeWithNullIncidentList() {
        instance.analyze(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAnalyzeWithEmptyIncidentList() {
        instance.analyze(new ArrayList<Incident>());
    }

    /**
     * Test of getAssignmentGroupsListToAnalize method, of class SlaAnalyzer.
     */
    @Test
    @Ignore(value = "At this moment is not implemented a test logic here")
    public void testGetAssignmentGroupsListToAnalize() {
        System.out.println("getAssignmentGroupsListToAnalize");
        List<String> expResult = null;
        List<String> result = instance.getAssignmentGroupsListToAnalize();
        assertEquals(expResult, result);
    }

    /**
     * Test of analizeIncident method, of class SlaAnalyzer.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testAnalizeIncidentThatIsComplianceWithSLA() throws Exception {
        Incident incident = getCompliantWithSLAIncident();
        ReportDetail result = instance.analizeIncident(incident);
        assertTrue("This incident must be compliant with the SLA", result.isComplianceWithSLA());
    }

    @Test
    @Ignore
    public void testAnalizeIncidentThatsIsNotCompliantWithSLA() throws Exception {
        Incident incident = getNotCompliantWithSLAIncident();
        ReportDetail result = instance.analizeIncident(incident);
        assertFalse("This incident must not be compliant with the SLA", result.isComplianceWithSLA());
    }

    private Incident getCompliantWithSLAIncident() {
        Incident incident = new Incident();
        incident.setId("IM0001");
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));

        List<Audit> audits = new ArrayList<>();
        final Audit audit1 = new Audit();
        audit1.setNewVaueText("ANOTHER-NON-APLYABLE-AG");
        audit1.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 00:30:00.00"));
        audits.add(audit1);
        final Audit audit2 = new Audit();
        audit2.setNewVaueText("W-INCLV4-FAIT-CTE");
        audit2.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 12:00:00.00"));
        audits.add(audit2);
        incident.setAudits(audits);

        return incident;
    }

    private Incident getNotCompliantWithSLAIncident() {
        Incident incident = new Incident();
        incident.setId("IM0002");
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));

        List<Audit> audits = new ArrayList<>();
        audits.add(new Audit());
        incident.setAudits(audits);
        return incident;
    }

    /**
     * Test of getServiceLevelAgreementByIncident method, of class SlaAnalyzer.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetServiceLevelAgreementByIncident() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority("top");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("high");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("medium");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("low");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW, instance.getServiceLevelAgreementByIncident(incident));

        incident.setCriticalityDescription("Entity Essential");
        incident.setPriority("top");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_TOP, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("high");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("medium");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("low");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW, instance.getServiceLevelAgreementByIncident(incident));

        incident.setCriticalityDescription("Normal");
        incident.setPriority("top");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_TOP, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("high");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_HIGH, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("medium");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM, instance.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("low");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_LOW, instance.getServiceLevelAgreementByIncident(incident));
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNullCriticality() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription(null);
        instance.getServiceLevelAgreementByIncident(incident);
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNullPriority() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority(null);
        instance.getServiceLevelAgreementByIncident(incident);
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNotValidCriticality() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("An invalid criticality value");
        instance.getServiceLevelAgreementByIncident(incident);
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNotValidPriority() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority("An invalid priority");
        instance.getServiceLevelAgreementByIncident(incident);
    }
}
