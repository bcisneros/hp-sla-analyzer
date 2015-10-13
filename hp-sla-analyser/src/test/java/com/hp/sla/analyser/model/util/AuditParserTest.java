package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public void testParseDocument() throws ParseException {
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
        
        SimpleDateFormat ft = new SimpleDateFormat ("MM/dd/yyy HH:mm:ss"); 
         
        Audit a_start = new Audit();
        a_start.setFieldDisplayName("Assignment Group");
        a_start.setFieldName("assignment");
        a_start.setIncidentID("IM20941354");
        a_start.setLogicalDeleteFlag(false);
        a_start.setNewVaueText("W-INCLV3-HPIT-BLUES-APPLICATIONS");
        a_start.setPreviousValueText("W-INCFLS-HPIT-MONITORING-RESTORATION");
        a_start.setRecordNumber(0);
        a_start.setSystemModifiedUser("HPOO");
        a_start.setSystemModifiedTime(ft.parse("09/17/2015 18:44:38"));
        
        Audit a_middle=new Audit();
        a_middle.setFieldDisplayName("Assignment Group");
        a_middle.setFieldName("assignment");
        a_middle.setIncidentID("IM20947277");
        a_middle.setLogicalDeleteFlag(false);
        a_middle.setNewVaueText("W-INCLV4-ITSRV-OO-DISPATCHER");
        a_middle.setPreviousValueText("null");
        a_middle.setRecordNumber(0);
        a_middle.setSystemModifiedUser("BSMIntegrator");
        a_middle.setSystemModifiedTime(ft.parse("09/18/2015 14:36:44"));
        
        Audit a_end=new Audit();
        a_end.setFieldDisplayName("Assignment Group");
        a_end.setFieldName("assignment");
        a_end.setIncidentID("IM20929991");
        a_end.setLogicalDeleteFlag(false);
        a_end.setNewVaueText("W-INCFLS-HPIT-BIZAPPS-CORP-FUNCTIONS");
        a_end.setPreviousValueText("null");
        a_end.setRecordNumber(0);
        a_end.setSystemModifiedUser("HPOO");
        a_end.setSystemModifiedTime(ft.parse("09/16/2015 00:08:23"));
        
        assertEquals("The first element is correct", result.get(0), a_start);
        assertEquals("The last element is correct", result.get(result.size()-1), a_end);
        assertEquals("One intermediate element ("+(result.size()-1)/2+") is correct", result.get((result.size()-1)/2), a_middle);
       
    }

}
