package com.hp.sla.analyser.model;

import com.hp.sla.analyser.model.util.IncidentParser;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyzerTest {

    private SlaAnalyzer instance;
    protected static final Logger logger = Logger.getLogger(IncidentParser.class);
    
    @Parameters
    public static List<Incident> compliantNonBurnedOutData() throws SlaAnalysisException{
        boolean compliant=true;
        boolean burnedOut=false;
        return testIncidentValues(burnedOut, compliant);
    }
    
    @Parameters
    public static List<Incident> compliantBurnedOutData() throws SlaAnalysisException{
        boolean compliant=true;
        boolean burnedOut=true;
        return testIncidentValues(burnedOut, compliant);
    }
    
     @Parameters
    public static List<Incident> nonCompliantNonBurnedOutData() throws SlaAnalysisException{
        boolean compliant=false;
        boolean burnedOut=false;
        return testIncidentValues(burnedOut, compliant);
    }
    
     @Parameters
    public static List<Incident> nonCompliantBurnedOutData() throws SlaAnalysisException{
        boolean compliant=false;
        boolean burnedOut=true;
        return testIncidentValues(burnedOut, compliant);
    }
    
    /**
     * Obtains a Collection of incident cases that has a certain value of burnedOut and compliant
     * @param burnedOut will the incidents be burned out
     * @param compliant will the incidents be compliant with sla
     * @return a collection of objects in the form {incident, burnedOut, compliant}
     * @throws SlaAnalysisException 
     */
    private static List<Incident> testIncidentValues( boolean burnedOut, boolean compliant) throws SlaAnalysisException{
    //public void testIncidentCompliantNonBurnedValues( boolean burnedOut, boolean compliant) throws SlaAnalysisException{
        String[] priority={"top", "high", "medium", "low"};
        String[] criticallity={"Mission Critical", "Entity Essential", "Normal"};
        List values=new ArrayList();
        for (int i=0; i<priority.length; i++) {
            for (int j = 0; j<=i && j<criticallity.length; j++) {
                Incident incident=generateIncident(criticallity[j], priority[i], burnedOut, compliant);
                
                ServiceLevelAgreement sla1=SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
                Timestamp burnedOutTimestamp=incident.calculateBurnedOutDate(sla1);
                Timestamp complianceLimitTimestamp=incident.calculateTimeToFixDeadLine(sla1);
                
                logger.debug("Incident: "+incident);
                logger.debug("  Is compliant:   "+incident.getCloseTimestamp().before(complianceLimitTimestamp));
                logger.debug("  Is burned out:  "+incident.getAudits().get(1).getSystemModifiedTime().after(burnedOutTimestamp));
                values.add(incident);
            }
        }
        return values;
    }
    
    /**
     * getCompliantWithSLAIncident
     * @param criticallity the criticallity of the incident
     * @param priority the priority of the incident
     * @param burnedOut will the incident be burned out
     * @param compliant will the incident be compliant
     * @return an Incident with the values input in the parameters
     * @throws SlaAnalysisException 
     */
    private static Incident generateIncident(String criticallity, String priority, boolean burnedOut, boolean compliant) throws SlaAnalysisException {
        //If is burned out will surpase the expected burnedout time by one minute else will be below by one minute
        int timeBurnedOut;
        if(burnedOut) timeBurnedOut=1; else timeBurnedOut=-1;
        int timeCompliant;
        if(compliant) timeCompliant=-1; else timeCompliant=1;
        
        //To generate a new incident with a random id
        Random randomGenerator = new Random();
        Incident incident = new Incident();
        incident.setId("IM00"+randomGenerator.nextInt(100)+randomGenerator.nextInt(100));  
        //Setcriticallity and priority
        incident.setCriticalityDescription(criticallity);
        incident.setPriority(priority);
        //Compute burned out time stamp
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
        ServiceLevelAgreement sla=SlaAnalyzer.getServiceLevelAgreementByIncident(incident);
        Timestamp burnedOutTimestamp=incident.calculateBurnedOutDate(sla);
        Timestamp complianceLimitTimestamp=incident.calculateTimeToFixDeadLine(sla);
        
        //El incidente se cierra 1 min antes/despues del tiempo en que cumple el fixed time
        incident.setCloseTimestamp(new Timestamp( complianceLimitTimestamp.getTime() + timeCompliant*60*1000));

        //Add the audits
        List<Audit> audits = new ArrayList<>();
        final Audit audit1 = new Audit();
        audit1.setNewVaueText("ANOTHER-NON-APLYABLE-AG");
        //Lo toma este AG 30 min despues de creado
        audit1.setSystemModifiedTime(new Timestamp( incident.getCreationTimestamp().getTime() + 30*60*1000));
        audits.add(audit1);
        final Audit audit2 = new Audit();
        audit2.setNewVaueText("W-INCLV4-FAIT-CTE");
        //Lo toma el AG de interes 1 min antes/despues de tiempo en que se quema 
        audit2.setSystemModifiedTime(new Timestamp( burnedOutTimestamp.getTime() + timeBurnedOut*60*1000));
        audits.add(audit2);
        incident.setAudits(audits);

        return incident;
    }

    @Before
    public void setUp() {
        instance = new SlaAnalyzer();
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
    public void testAnalizeIncidentThatIsComplianceWithSLAButNotBurnedOut() throws Exception {
        Incident incident = getCompliantWithSLAIncident();
        ReportDetail result = instance.analizeIncident(incident);
        assertTrue("This incident must be compliant with the SLA", result.isCompliantWithSLA());
        assertFalse("This incident must not be burned out", result.isBurnedOut());
    }

    @Test
    public void testAnalizeIncidentThatsIsNotCompliantWithSLA() throws Exception {
        Incident incident = getNotCompliantWithSLAIncident();
        ReportDetail result = instance.analizeIncident(incident);
        assertFalse("This incident must not be compliant with the SLA", result.isCompliantWithSLA());
        assertTrue("This incident must be burned out", result.isBurnedOut());
    }

    @Test
    public void testAnalizeIncidentWithNullValuesOnNotRequiredFields() throws Exception {
        Incident incident = getNotCompliantWithSLAIncident();
        incident.setCloseTimestamp(null);
        try {
            ReportDetail result = instance.analizeIncident(incident);
            assertNotNull("The object must be not null", result);
        } catch (Exception exception) {
            fail("This test must not throw an exception: " + exception);
        }
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

    private Incident getCompliantWithSLAIncident() {
        Incident incident = new Incident();
        incident.setId("IM0001");
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
        incident.setCloseTimestamp(Timestamp.valueOf("2015-01-01 02:31:00.00"));
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority("top");

        List<Audit> audits = new ArrayList<>();
        final Audit audit1 = new Audit();
        audit1.setNewVaueText("ANOTHER-NON-APLYABLE-AG");
        audit1.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 00:30:00.00"));
        audits.add(audit1);
        final Audit audit2 = new Audit();
        audit2.setNewVaueText("W-INCLV4-FAIT-CTE");
        audit2.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 2:30:00.00"));
        audits.add(audit2);
        incident.setAudits(audits);

        return incident;
    }

    private Incident getNotCompliantWithSLAIncident() {
        Incident incident = new Incident();
        incident.setId("IM0002");
        incident.setCriticalityDescription("Mission Critical");
        incident.setPriority("top");
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
        incident.setCloseTimestamp(Timestamp.valueOf("2015-01-01 3:31:00.00"));
        List<Audit> audits = new ArrayList<>();
        final Audit audit1 = new Audit();
        audit1.setNewVaueText("ANOTHER-NON-APLYABLE-AG");
        audit1.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 00:30:00.00"));
        audits.add(audit1);
        final Audit audit2 = new Audit();
        audit2.setNewVaueText("W-INCLV4-FAIT-CTE");
        audit2.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 3:30:00.00"));
        audits.add(audit2);
        incident.setAudits(audits);
        return incident;
    }

}
