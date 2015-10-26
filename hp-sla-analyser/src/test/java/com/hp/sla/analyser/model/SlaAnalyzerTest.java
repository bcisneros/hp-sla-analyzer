package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.Criticality;
import com.hp.sla.analyser.model.util.IncidentParser;
import com.hp.sla.analyser.model.util.Priority;
import com.hp.sla.analyser.util.DateTimeUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
@RunWith(JUnitParamsRunner.class)
public class SlaAnalyzerTest {

    private SlaAnalyzer instance;
    protected static final Logger logger = Logger.getLogger(IncidentParser.class);

    @Before
    public void setUp() {
        instance = new SlaAnalyzer();
    }

    /**
     * Test of analyze method, of class SlaAnalyzer.
     */
    @Test
    public void testAnalyze() {
        List<Incident> incidentsToAnalize = new ArrayList<>();
        incidentsToAnalize.addAll(getCompliantIncidents());
        incidentsToAnalize.addAll(getNonCompliantIncidents());
        incidentsToAnalize.addAll(getBurnedOutIncidents());
        incidentsToAnalize.addAll(getNonBurnedOutIncidents());
        final List<ReportDetail> result = instance.analyze(incidentsToAnalize);
        assertNotNull("The resulting analized list must be not null", result);
        assertEquals("The size of the resulting list must be the equals to the input incident list", incidentsToAnalize.size(), result.size());

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
    public void testGetAssignmentGroupsListToAnalize() {
        List<String> result = instance.getAssignmentGroupsListToAnalize();
        assertNotNull("The result list must be not null", result);
        assertFalse("The result list must be not empty", result.isEmpty());
    }

    @Test
    @Parameters(method = "getCompliantIncidents")
    public void testAnalizeIncidentWithCompliantIncidents(Incident incident) {
        try {
            assertTrue("This incident must be compliant with the SLA", instance.analizeIncident(incident).isCompliantWithSLA());
        } catch (SlaAnalysisException ex) {
            fail("No exception was expected: " + ex);
        }
    }

    @Test
    @Parameters(method = "getNonCompliantIncidents")
    public void testAnalizeIncidentWithNonCompliantIncidents(Incident incident) {
        try {
            assertFalse("This incident must not be compliant with the SLA", instance.analizeIncident(incident).isCompliantWithSLA());
        } catch (SlaAnalysisException ex) {
            fail("No exception was expected: " + ex);
        }
    }

    @Test
    @Parameters(method = "getBurnedOutIncidents")
    public void testAnalizeIncidentWithBurnedOutIncidents(Incident incident) {
        try {
            assertTrue("This incident must be burned out", instance.analizeIncident(incident).isBurnedOut());
        } catch (SlaAnalysisException ex) {
            fail("No exception was expected: " + ex);
        }
    }

    @Test
    @Parameters(method = "getNonBurnedOutIncidents")
    public void testAnalizeIncidentWithNonBurnedOutIncidents(Incident incident) {
        try {
            assertFalse("This incident must be not burned out", instance.analizeIncident(incident).isBurnedOut());
        } catch (SlaAnalysisException ex) {
            fail("No exception was expected: " + ex);
        }
    }

    @Test
    public void testAnalizeIncidentWithNullValuesOnNotRequiredFields() {
        Incident incident = getCompliantIncidents().get(0);
        incident.setCloseTimestamp(null);
        try {
            ReportDetail result = instance.analizeIncident(incident);
            assertNotNull("The object must be not null", result);
        } catch (Exception exception) {
            fail("This test must not throw an exception: " + exception);
        }
    }

    @Test(expected = SlaAnalysisException.class)
    public void testAnalizeIncidentWithNullServiceLevelAgreement() throws SlaAnalysisException {
        Incident incident = getCompliantIncidents().get(0);
        incident.setCriticalityDescription(Criticality.ENTITY_ESSENTIAL.getName());
        incident.setPriority(Priority.TOP.getName());
        instance.analizeIncident(incident);
    }

    @Test
    public void testGetServiceLevelAgreementByIncident() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority("top");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("high");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("medium");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("low");
        assertSame(ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));

        incident.setCriticalityDescription("Entity Essential");
        incident.setPriority("top");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_TOP, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("high");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("medium");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("low");
        assertSame(ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));

        incident.setCriticalityDescription("Normal");
        incident.setPriority("top");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_TOP, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("high");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_HIGH, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("medium");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
        incident.setPriority("low");
        assertSame(ServiceLevelAgreement.HP_IT_NORMAL_LOW, SlaAnalyzer.getServiceLevelAgreementByIncident(incident));
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNullCriticality() throws SlaAnalysisException {
        Incident incident = new Incident();
        incident.setCriticalityDescription(null);
        SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNullPriority() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority(null);
        SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNotValidCriticality() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("An invalid criticality value");
        SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
    }

    @Test(expected = SlaAnalysisException.class)
    public void testGetServiceLevelAgreementByIncidentWithNotValidPriority() throws Exception {
        Incident incident = new Incident();
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority("An invalid priority");
        SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
    }

    protected static List<Incident> getCompliantIncidents() {
        return testIncidentValues(true, true);
    }

    public static List<Incident> getNonCompliantIncidents() {
        return testIncidentValues(false, false);
    }

    public static List<Incident> getBurnedOutIncidents() {
        return testIncidentValues(true, false);
    }

    public static List<Incident> getNonBurnedOutIncidents() {
        return testIncidentValues(false, true);
    }

    public static List<Incident> getIncidentsWithNullServiceLevelAgreement() {
        List<Incident> incidents = new ArrayList<>();
        incidents.add(generateIncident(null, null, true, true));
        incidents.add(generateIncident(Criticality.ENTITY_ESSENTIAL.getName(), Priority.TOP.getName(), true, true));
        incidents.add(generateIncident(Criticality.NORMAL.getName(), Priority.TOP.getName(), true, true));
        incidents.add(generateIncident(Criticality.NORMAL.getName(), Priority.HIGH.getName(), true, true));
        return incidents;
    }

    /**
     * Obtains a Collection of incident cases that has a certain value of
     * burnedOut and compliant
     *
     * @param burnedOut will the incidents be burned out
     * @param compliant will the incidents be compliant with sla
     * @return a collection of objects in the form {incident, burnedOut,
     * compliant}
     * @throws SlaAnalysisException
     */
    private static List<Incident> testIncidentValues(boolean burnedOut, boolean compliant) {
        String[] priorities = {Priority.TOP.getName(), Priority.HIGH.getName(), Priority.MEDIUM.getName(), Priority.LOW.getName()};
        String[] criticalities = {Criticality.MISSION_CRITICAL.getName(), Criticality.ENTITY_ESSENTIAL.getName(), Criticality.NORMAL.getName()};
        List values = new ArrayList();
        for (int i = 0; i < priorities.length; i++) {
            for (int j = 0; j <= i && j < criticalities.length; j++) {
                Incident incident = generateIncident(criticalities[j], priorities[i], burnedOut, compliant);
                if (incident.getCloseTimestamp() != null) {
                    values.add(incident);
                }
            }
        }
        return values;
    }

    /**
     * getCompliantWithSLAIncident
     *
     * @param criticality the criticality of the incident
     * @param priority the priority of the incident
     * @param burnedOut will the incident be burned out
     * @param compliant will the incident be compliant
     * @return an Incident with the values input in the parameters
     * @throws SlaAnalysisException
     */
    private static Incident generateIncident(String criticality, String priority, boolean burnedOut, boolean compliant) {
        //If is burned out will surpase the expected burnedout time by one minute else will be below by one minute
        int timeBurnedOut;
        if (burnedOut) {
            timeBurnedOut = 1;
        } else {
            timeBurnedOut = -1;
        }
        int timeCompliant;
        if (compliant) {
            timeCompliant = -1;
        } else {
            timeCompliant = 1;
        }

        //To generate a new incident with a random id
        Random randomGenerator = new Random();
        Incident incident = new Incident();
        incident.setId("IM00" + randomGenerator.nextInt(100) + randomGenerator.nextInt(100));
        //Setcriticallity and priority
        incident.setCriticalityDescription(criticality);
        incident.setPriority(priority);
        //Compute burned out time stamp
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
        ServiceLevelAgreement sla;
        try {
            sla = SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
        } catch (SlaAnalysisException ex) {
            // When we cannot calculate the service level we return an incident with provided values
            logger.info("Skipping this error", ex);
            return incident;
        }
        Timestamp burnedOutTimestamp = incident.calculateBurnedOutDate(sla);
        Timestamp complianceLimitTimestamp = incident.calculateTimeToFixDeadLine(sla);

        //El incidente se cierra 1 min antes/despues del tiempo en que cumple el fixed time
        incident.setCloseTimestamp(DateTimeUtil.addHours(complianceLimitTimestamp, timeCompliant * 1.0 / 60));

        //Add the audits
        List<Audit> audits = new ArrayList<>();
        final Audit audit1 = new Audit();
        audit1.setIncidentID(incident.getId());
        audit1.setNewVaueText("ANOTHER-NON-APLYABLE-AG");
        //Lo toma este AG 30 min despues de creado
        audit1.setSystemModifiedTime(new Timestamp(incident.getCreationTimestamp().getTime() + 30 * 60 * 1000));
        audits.add(audit1);
        final Audit audit2 = new Audit();
        audit2.setIncidentID(incident.getId());
        audit2.setNewVaueText("W-INCLV4-FAIT-CTE");
        //Lo toma el AG de interes 1 min antes/despues de tiempo en que se quema
        audit2.setSystemModifiedTime(DateTimeUtil.addHours(burnedOutTimestamp, timeBurnedOut * 1.0 / 60));
        audits.add(audit2);
        incident.setAudits(audits);

        return incident;
    }

}
