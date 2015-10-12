/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
public class AuditParserTest {

    public AuditParserTest() {
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
     * Test of parseDocument method, of class AuditParser.
     */
    @Test
    public void testParseDocument() {
        System.out.println("parseDocument");
        XSSFSheet sheet=null;
        try {
            ExcelReader reader = new ExcelReader();
            ClassLoader classLoader = getClass().getClassLoader(); 
            reader.setInputFile(new FileInputStream(new File(classLoader.getResource("files/SSITRTBAGassignmentaudit.xlsx").getFile())));
            sheet = reader.read();
        } catch (IOException ex) {
            Logger.getLogger(AuditParserTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        AuditParser instance = new AuditParser();
        List result = instance.parseDocument(sheet);
        assertNotNull("The resulting List is not null", result);
        assertEquals("The resulting list has 3041 elements", 3040, result.size());
    }

}
