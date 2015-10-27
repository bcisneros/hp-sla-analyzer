package com.hp.sla.analyser.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 *
 * @author Mallinali Ramirez Corona
 */
public class ExcelWritterTest {

    @Test
    public void testWrite() {
        try {
            ExcelWritter ew = new ExcelWritter();
            String filename = "C:\\temp\\testExcelWritter";
            String expected = filename + ".xlsx";
            String result = ew.write(null, filename);
            assertNotNull("The result must be not null", result);
            assertEquals("The resulting file must be written at this location", expected, result);
        } catch (Exception ex) {
            fail("No exception is expected here: " + ex);
        }
    }

}
