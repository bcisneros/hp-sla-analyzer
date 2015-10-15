/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ramirmal
 */
public class IncidentTest {
    
    public IncidentTest() {
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
     * Test of getTimeToFix method, of class Incident.
     */
    @Test
    public void testGetTimeToFix() throws ParseException {
        ServiceLevelAgreement sla = null;
        Incident instance = new Incident();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date parsedDate = dateFormat.parse("09/01/2015 00:00:04");
        instance.setCreationTimestamp(new Timestamp(parsedDate.getTime()));
        Timestamp expResult = new Timestamp(dateFormat.parse("09/01/2015 02:00:04").getTime());
        Timestamp result = instance.getTimeToFix(sla);
        assertEquals("Correct time to fix ",expResult, result);
    }
    
}
