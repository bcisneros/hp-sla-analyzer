package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Incident;
import com.hp.sla.analyser.util.ResourcesUtil;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import static org.junit.Assert.fail;

/**
 * Test class for IncidentParser class
 *
 * @author Benjamin Cisneros Barraza
 */
public class IncidentParserTest extends ExcelParserTest<Incident> {

    @Override
    protected Object getLastElementExpected() {
        Incident incident = new Incident();
        incident.setId("IM20958696");
        return incident;
    }

    @Override
    protected Object getFirstElementExpected() {
        Incident incident = new Incident();
        incident.setId("IM20840143");
        return incident;
    }

    @Override
    protected int getExpectedSize() {
        return 4196;
    }

    @Override
    protected Sheet getSheetToTest() {
        try {
            return (XSSFSheet) ExcelReader.read(ResourcesUtil.getResourceFromProjectClasspath("files/Incidenttickets-ALLGFITFAIT.xlsx")).getSheetAt(0);
        } catch (IOException ex) {
            fail("This exception is not expected: " + ex);
        }
        return null;
    }

    @Override
    protected ExcelParser getInstance() {
        return new IncidentParser();
    }
}
