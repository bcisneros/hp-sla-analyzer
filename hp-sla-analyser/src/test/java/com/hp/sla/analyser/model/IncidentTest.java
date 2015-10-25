package com.hp.sla.analyser.model;

import com.hp.sla.analyser.util.DateTimeUtil;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author cisnerob
 */
public class IncidentTest {

    private final static Logger logger = Logger.getLogger(SlaReportGenerator.class);

    /**
     * Test of calculateBurnedOutDate method, of class Incident.
     */
    @Test
    public void testCalculateBurnedOutDate() {
        Timestamp startTime = Timestamp.valueOf("2015-01-01 00:00:00.00");
        //TOP
        ServiceLevelAgreement serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP;
        Incident incident = new Incident();
        incident.setCreationTimestamp(startTime);
        Timestamp expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(3));
        Timestamp result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        //HIGH
        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(6));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(6));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        //MEDIUM
        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + DateTimeUtil.hoursToMilliseconds(72 * 0.75));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + DateTimeUtil.hoursToMilliseconds(72 * 0.75));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + DateTimeUtil.hoursToMilliseconds(96 * 0.75));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        //LOW
        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + DateTimeUtil.hoursToMilliseconds(96 * 0.75));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + DateTimeUtil.hoursToMilliseconds(96 * 0.75));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_NORMAL_LOW;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + DateTimeUtil.hoursToMilliseconds(144 * 0.75));
        result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateTimeToFixDeadLine method, of class Incident.
     */
    @Test
    public void testCalculateTimeToFixDeadLine() {
        Timestamp startTime = DateTimeUtil.FIRST_DAY_2015_YEAR_TIMESTAMP;
        //TOP
        ServiceLevelAgreement serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP;
        Incident incident = new Incident();
        incident.setCreationTimestamp(startTime);
        Timestamp expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(3));
        Timestamp result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        //HIGH
        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_HIGH;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(6));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_HIGH;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(6));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        //MEDIUM
        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_MEDIUM;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(72));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_MEDIUM;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(72));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_NORMAL_MEDIUM;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(96));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        //LOW
        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_LOW;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(96));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        serviceLevelAgreement = ServiceLevelAgreement.HP_IT_ENTITY_ESSENTIAL_LOW;
        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(96));
        result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);

        incident = new Incident();
        incident.setCreationTimestamp(startTime);
        expResult = new Timestamp(startTime.getTime() + TimeUnit.HOURS.toMillis(144));
        result = incident.calculateTimeToFixDeadLine(ServiceLevelAgreement.HP_IT_NORMAL_LOW);
        assertEquals("The deadline for a HP IT Normal - Low Incidents must be 144 hours (4 business days)", expResult, result);
    }

    @Test
    public void testSearchAndSetLastAssignmentGroupAudit() {
        Incident incident = new Incident();
        List<Audit> audits = new ArrayList<>();

        Audit audit1 = new Audit();
        audit1.setNewVaueText("A");
        audit1.setSystemModifiedTime(Timestamp.valueOf("2015-01-01 00:00:00"));

        Audit audit2 = new Audit();
        audit2.setNewVaueText("B");
        audit2.setSystemModifiedTime(Timestamp.valueOf("2015-01-15 00:00:00"));

        Audit audit3 = new Audit();
        audit3.setNewVaueText("X");
        audit3.setSystemModifiedTime(Timestamp.valueOf("2015-01-29 00:00:00"));

        audits.add(audit1);
        audits.add(audit2);

        incident.setAudits(audits);

        List<String> assignmentGroups;
        assignmentGroups = new ArrayList<>();
        assignmentGroups.add("A");
        assignmentGroups.add("B");
        assignmentGroups.add("C");

        incident.searchAndSetLastAssignmentGroupAudit(assignmentGroups);

        assertNotNull("The last asssignment group audit must be not null", incident.getLastAssignmentGroupAudit());
        assertSame("The last asssignment group audit must be the same object as Audit 2", incident.getLastAssignmentGroupAudit(), audit1);
    }

    @Test
    public void testClone() {
        try {
            Incident incident = new Incident();
            assertEquals(incident, incident.clone());
            assertNotSame(incident, incident.clone());
        } catch (CloneNotSupportedException ex) {
            fail("No exception was expected: " + ex);
        }
    }

}
