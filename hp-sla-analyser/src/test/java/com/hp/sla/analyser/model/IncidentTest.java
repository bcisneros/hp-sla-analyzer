/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model;

import java.sql.Timestamp;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Ignore;
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

}
