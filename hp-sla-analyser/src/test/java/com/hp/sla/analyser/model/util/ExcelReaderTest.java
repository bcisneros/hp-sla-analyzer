package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.util.ResourcesUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class ExcelReaderTest {

    public ExcelReaderTest() {
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

    @Test
    public void testRead() throws Exception {
        Workbook workBookFromClasspath = ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/testWorkBook.xlsx"));
        assertNotNull("This workbook object must be not null", workBookFromClasspath);
    }

}
