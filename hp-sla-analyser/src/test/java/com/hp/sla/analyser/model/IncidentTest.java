package com.hp.sla.analyser.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

/**
 *
 * @author cisnerob
 */
public class IncidentTest {

    /**
     * Test of calculateBurnedOutDate method, of class Incident.
     */
    @Test
    public void testCalculateBurnedOutDate() {
        ServiceLevelAgreement serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP;
        Incident incident = new Incident();
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
        Timestamp expResult = Timestamp.valueOf("2015-01-01 03:00:00.00");
        Timestamp result = incident.calculateBurnedOutDate(serviceLevelAgreement);
        assertEquals(expResult, result);
    }

    /**
     * Test of calculateTimeToFixDeadLine method, of class Incident.
     */
    @Test
    public void testCalculateTimeToFixDeadLine() {
        ServiceLevelAgreement serviceLevelAgreement = ServiceLevelAgreement.HP_IT_MISSION_CRITICAL_TOP;
        Incident incident = new Incident();
        incident.setCreationTimestamp(Timestamp.valueOf("2015-01-01 00:00:00.00"));
        Timestamp expResult = Timestamp.valueOf("2015-01-01 03:00:00.00");
        Timestamp result = incident.calculateTimeToFixDeadLine(serviceLevelAgreement);
        assertEquals(expResult, result);
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

}
