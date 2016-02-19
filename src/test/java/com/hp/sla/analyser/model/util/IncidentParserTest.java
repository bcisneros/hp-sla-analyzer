package com.hp.sla.analyser.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.hp.sla.analyser.model.Incident;
import com.hp.sla.analyser.model.TestIncidentBuilder;
import com.hp.sla.analyser.util.ResourcesUtil;

/**
 * Test class for IncidentParser class
 *
 * @author Benjamin Cisneros Barraza
 */
public class IncidentParserTest extends ExcelParserTest<Incident> {

	@Test
	public void shouldCreateTheExpectedObject() throws Exception {
		Incident incidentExpected = TestIncidentBuilder.getInstance().build();
		IncidentParser incidentParser = (IncidentParser) getInstance();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
		Row row = xssfWorkbook.createSheet().createRow(0);
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
		xssfWorkbook.close();
		assertNotNull(incident);
		assertEquals(incidentExpected, incident);
	}

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
	protected ExcelParser<?> getInstance() {
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
			return ExcelReader
					.read(ResourcesUtil.getResourceFromProjectClasspath("files/Incidenttickets-ALLGFITFAIT.xlsx"))
					.getSheetAt(0);
		} catch (IOException ex) {
			fail("This exception is not expected: " + ex);
		}
		return null;
	}
}
