package com.hp.sla.analyser.util;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Benjamín Cisneros Barraza
 */
public class ResourcesUtilTest {

    /**
     * Test of getResourceFromProjectClasspath method, of class ResourcesUtil.
     */
    @Test
    public void testGetResourceFromProjectClasspath() {
        assertNotNull("This file must be not null", ResourcesUtil.getResourceFromProjectClasspath("files/reportTemplate.xlsx"));
        assertNull("This file must be null", ResourcesUtil.getResourceFromProjectClasspath("files/somethingThatDoesNotExist"));
    }

}
