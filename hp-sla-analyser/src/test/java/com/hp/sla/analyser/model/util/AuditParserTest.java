package com.hp.sla.analyser.model.util;

import static com.hp.sla.analyser.model.AuditsBuilder.anAudit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.hp.sla.analyser.model.Audit;
import com.hp.sla.analyser.util.ResourcesUtil;

/**
 *
 * @author Mallinali Ramirez Corona
 */
public class AuditParserTest extends ExcelParserTest<Audit> {

	private static final Timestamp DEFAULT_SYSTEM_MODIFIED_TIME = Timestamp.valueOf("2015-09-17 18:44:38");
	private static final String DEFAULT_INCIDENT_ID = "IM20941354";
	private static final String DEFAULT_PREVIOUS_ASSIGNMENT_GROUP = "W-INCFLS-HPIT-MONITORING-RESTORATION";
	private static final String DEFAULT_CURRENT_ASSIGNMENT_GROUP = "W-INCLV3-HPIT-BLUES-APPLICATIONS";

	@Test
	public void testCreateObject() throws Exception {
		Audit auditExpected = getDefaultAudit();
		AuditParser auditParser = (AuditParser) this.getInstance();
		Iterator<Cell> iterator = createCellIterator(auditExpected);
		Audit audit = auditParser.createObject(iterator);
		assertNotNull(audit);
		assertEquals(auditExpected, audit);
	}

	@Override
	protected Object getLastElementExpected() {
		return anAudit().incidentID("IM20929991").currentAssignmentGroup("W-INCFLS-HPIT-BIZAPPS-CORP-FUNCTIONS")
				.previousAssignmentGroup("null").systemTime(Timestamp.valueOf("2015-09-16 00:08:23")).build();
	}

	@Override
	protected Object getFirstElementExpected() {
		return getDefaultAudit();
	}

	@Override
	protected int getExpectedSize() {
		return 3040;
	}

	@Override
	protected Sheet getSheetToTest() {
		try {
			return ExcelReader
					.read(ResourcesUtil.getResourceFromProjectClasspath("files/SSITRTBAGassignmentaudit.xlsx"))
					.getSheetAt(0);
		} catch (IOException ex) {
			fail("This exception is not expected: " + ex);
		}
		return null;
	}

	@Override
	protected ExcelParser<?> getInstance() {
		return new AuditParser();
	}

	private Audit getDefaultAudit() {
		return anAudit().incidentID(DEFAULT_INCIDENT_ID).currentAssignmentGroup(DEFAULT_CURRENT_ASSIGNMENT_GROUP)
				.previousAssignmentGroup(DEFAULT_PREVIOUS_ASSIGNMENT_GROUP).systemTime(DEFAULT_SYSTEM_MODIFIED_TIME)
				.build();
	}

	private Iterator<Cell> createCellIterator(Audit auditExpected) throws Exception {
		XSSFWorkbook wb = new XSSFWorkbook();
		Row row = wb.createSheet().createRow(0);
		int i = 0;
		row.createCell(i++).setCellValue(auditExpected.getFieldDisplayName());
		row.createCell(i++).setCellValue(auditExpected.getFieldName());
		row.createCell(i++).setCellValue(auditExpected.getIncidentID());
		if (auditExpected.getLogicalDeleteFlag()) {
			row.createCell(i++).setCellValue("y");
		} else {
			row.createCell(i++).setCellValue("n");
		}
		row.createCell(i++).setCellValue(auditExpected.getNewVaueText());
		row.createCell(i++).setCellValue(auditExpected.getPreviousValueText());
		row.createCell(i++).setCellValue(0);
		row.createCell(i++).setCellValue(auditExpected.getSystemModifiedUser());
		row.createCell(i++).setCellValue(auditExpected.getSystemModifiedTime());
		wb.close();
		return row.iterator();
	}
}
