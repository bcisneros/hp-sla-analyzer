package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Incident;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author cisnerob
 */
public class IncidentParserTest {

    public IncidentParserTest() {
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
     * Test of parseDocument method, of class IncidentParser.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testParseDocument() throws Exception {
        ExcelReader excelReader = new ExcelReader();
        ClassLoader classLoader = getClass().getClassLoader();
        FileInputStream testFile = new FileInputStream(new File(classLoader.getResource("files/Incidenttickets-ALLGFITFAIT.xlsx").getFile()));
        excelReader.setInputFile(testFile);
        XSSFSheet sheet = excelReader.read();
        IncidentParser instance = new IncidentParser();
        List<Incident> result = instance.parseDocument(sheet);
        assertNotNull("This must return a not null value", result);
        assertEquals("This must return 4196 incidents", 4196, result.size());

        Incident firstIncident = result.get(0);
        assertNotNull("The first incident at the list must not be null", firstIncident);
        assertEquals("The expected ID is IM20840143", "IM20840143", firstIncident.getId());
        assertEquals("The expected created timestamp is 09/01/2015 00:00:04.00", Timestamp.valueOf("2015-09-01 00:00:04.00"), firstIncident.getCreationTimestamp());
        assertEquals("The expected closed timestamp is 09/01/2015 04:27:39.00", Timestamp.valueOf("2015-09-01 04:27:39.00"), firstIncident.getCloseTimestamp());
        assertEquals("The time to fix duration must be 4.46 days", 4.46, firstIncident.getTimeToFixDuration(), 0);
        assertEquals("The CI Logical Name must be w-recadvt-inc:prd-app", "w-recadvt-inc:prd-app", firstIncident.getConfigurationItemLogicalName());
        assertEquals("The current status phase description must be Closed", "Closed", firstIncident.getCurrentStatusPhaseDescription());
        assertTrue("The incident is Production Active", firstIncident.isProductionActive());
        assertEquals("The priority description must be medium", "medium", firstIncident.getPriority());
        assertEquals("The aging duration must be 20.0", 20.0d, firstIncident.getAgingDurationInDays(), 0);
        assertEquals("The closure code description must be not solved - not able to re-produce", "not solved - not able to re-produce", firstIncident.getClosureCodeDescription());
        assertEquals("The opened-by email name must be BSMIntegrator", "BSMIntegrator", firstIncident.getOpenedByEmailName());
        assertEquals("The assignee email name must be shantanu.thakur@hpe.com", "shantanu.thakur@hpe.com", firstIncident.getAssigneeEmailName());
        assertEquals("The assignee manager's email must be viswanathan@hpe.com", "viswanathan@hpe.com", firstIncident.getAssigneeManagerEmailName());
        assertEquals("The assignee organization unit name must be Application Operations and Support", "Application Operations and Support", firstIncident.getAssigneeOrganizationUnitName());
        assertEquals("The current Assignment Group Name must be W-INCLV3-HPIT-BLUES-APPLICATIONS", "W-INCLV3-HPIT-BLUES-APPLICATIONS", firstIncident.getCurrentAssignmentGroup());
        assertEquals("The criticality description must be Normal", "Normal", firstIncident.getCriticalityDescription());
        assertEquals("The current assignment group support level description must be L3", "L3", firstIncident.getCurrentAssignmentGroupSupportLevelDescription());
        assertEquals("The closed assignment group support level description must be L3", "L3", firstIncident.getClosedAssignmentGroupSupportLevelDescription());
        assertEquals("This field must be not avail value", "not avail", firstIncident.getConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel4Name());
        assertNotNull("Need to have a title", firstIncident.getTitle());
        assertEquals("The related root CI Application Portfolio Identifier must be 202168", 202168L, firstIncident.getRelatedRootConfigurationItemApplicationPortfolioIdentifier());
        assertEquals("The related root CI Business Friendly Name must be Records Advisor Tool", "Records Advisor Tool", firstIncident.getRelatedRootConfigurationItemBusinessFriendlyName());
        assertEquals("The CI Status Description must be active", "active", firstIncident.getConfigurationItemStatus());
        assertEquals("The CI Environment Description must be Production", "Production", firstIncident.getConfigurationItemEnvironment());
        assertEquals("The current status must be Closed", "Closed", firstIncident.getCurrentStatus());
        assertEquals("This field must be hpi-it", "hpi-it", firstIncident.getConfigurationItemITAssetOwnerAssignmentGroupOrganizationLevel1Name());
    }

}
