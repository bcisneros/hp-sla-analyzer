package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.sql.Date;
import java.sql.Timestamp;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Benjamín Cisneros Barraza
 */
public class AuditSystemModifiedTimeComparatorTest {

    public AuditSystemModifiedTimeComparatorTest() {
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
     * Test of compare method, of class AuditSystemModifiedTimeComparator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Audit audit1 = new Audit();
        audit1.setSystemModifiedTime(new Timestamp(123456789));
        Audit audit2 = new Audit();
        audit2.setSystemModifiedTime(new Timestamp(123456789));
        AuditSystemModifiedTimeComparator instance = new AuditSystemModifiedTimeComparator();
        assertEquals("This must return zero", instance.compare(audit1, audit2), 0);
        assertEquals("Even if we invert the order", instance.compare(audit2, audit1), 0);

        audit2.setSystemModifiedTime(new Timestamp(123456789 + 1000));
        assertTrue("Audit 1 is less than Audit 2", instance.compare(audit1, audit2) < 0);
        assertTrue("Audit 2 is great than Audit 1", instance.compare(audit2, audit1) > 0);
    }

}
