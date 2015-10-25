package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.util.ResourcesUtil;
import org.apache.poi.ss.usermodel.Workbook;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * Test class for ExcelReader class
 *
 * @author Benjamin Cisneros Barraza
 */
public class ExcelReaderTest {

    @Test
    public void testRead() throws Exception {
        Workbook workBookFromClasspath = ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/testWorkBook.xlsx"));
        assertNotNull("This workbook object must be not null", workBookFromClasspath);
    }

}
