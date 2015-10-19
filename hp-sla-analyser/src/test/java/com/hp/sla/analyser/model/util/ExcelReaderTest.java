package com.hp.sla.analyser.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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

    /**
     * Test of read method, of class ExcelReader.
     */
    @Test
    public void testRead() throws Exception {
        System.out.println("read");
        ExcelReader excelReader = new ExcelReader();
        ClassLoader classLoader = getClass().getClassLoader();
        FileInputStream testFile = new FileInputStream(new File(classLoader.getResource("files/testWorkBook.xlsx").getFile()));
        excelReader.setInputFile(testFile);
        excelReader.read();
    }

}
