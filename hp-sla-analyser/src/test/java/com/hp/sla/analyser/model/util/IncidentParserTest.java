package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Incident;
import com.hp.sla.analyser.model.TestIncidentBuilder;
import com.hp.sla.analyser.util.DateTimeUtil;
import com.hp.sla.analyser.util.ResourcesUtil;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.XSSFRowShifter;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * Test class for IncidentParser class
 *
 * @author Benjamin Cisneros Barraza
 */
public class IncidentParserTest extends ExcelParserTest<Incident> {


	@Override
	protected int getExpectedSize() {
		return 4196;
	}
	
	@Override
	protected Object getFirstElementExpected() {
		Incident incident = new Incident();
		incident.setId("IM20840143");
		return incident;
	}
	
	@Override
	protected ExcelParser getInstance() {
		return new IncidentParser();
	}
	
	@Override
	protected Object getLastElementExpected() {
		Incident incident = new Incident();
		incident.setId("IM20958696");
		return incident;
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
    @Test
    public void testCreateObject() {
        Incident incidentExpected = TestIncidentBuilder.getInstance().build();
        IncidentParser incidentParser = (IncidentParser) getInstance();
        Row row = new XSSFWorkbook().createSheet().createRow(0);
        int i = 0;
        row.createCell(i++).setCellValue(incidentExpected.getId());
        row.createCell(i++).setCellValue(incidentExpected.getCreationTimestamp());
        row.createCell(i++).setCellValue(incidentExpected.getCloseTimestamp());
        row.createCell(i++).setCellValue(100);
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue(0);
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue(0);
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        row.createCell(i++).setCellValue("");
        Iterator<Cell> iterator = row.iterator();
        Incident incident = incidentParser.createObject(iterator);
        assertNotNull(incident);
        assertEquals(incidentExpected, incident);
    }
}
