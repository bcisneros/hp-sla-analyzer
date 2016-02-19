package com.hp.sla.analyser.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.SheetBuilder;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

/**
 *
 * @author Benjamin Cisneros Barraza
 * @param <T>
 */
public class ExcelParserTest<T> {

	@Test
	public final void testParseDocument() {
		try {
			List<?> list = getInstance().parseDocument(getSheetToTest());
			assertNotNull(list);
			assertFalse("The list must not be empty", list.isEmpty());
			assertEquals("The expected size of the list is " + getExpectedSize(), getExpectedSize(), list.size());
			assertEquals("The first element must be the first element expected", getFirstElementExpected(),
					list.get(0));
			assertEquals("The last element must be the last element expected", getLastElementExpected(),
					list.get(list.size() - 1));
		} catch (ExcelParsingException ex) {
			fail("No exception was expected:" + ex);
		}
	}

	protected ExcelParser<?> getInstance() {
		return new ExcelParserImpl();
	}

	protected Sheet getSheetToTest() {
		return new SheetBuilder(new XSSFWorkbook(),
				new String[][] { { "Header1", "Header2" }, { "Key1", "Data1" }, { "Key2", "Data2" } }).build();
	}

	protected int getExpectedSize() {
		return 2;
	}

	protected Object getFirstElementExpected() {
		Map<String, String> returnObject = new HashMap<>();
		returnObject.put("Key1", "Data1");
		return returnObject;
	}

	protected Object getLastElementExpected() {
		Map<String, String> returnObject = new HashMap<>();
		returnObject.put("Key2", "Data2");
		return returnObject;
	}

	private class ExcelParserImpl extends ExcelParser<Map<String, String>> {

		@Override
		public Map<String, String> createObject(Iterator<Cell> cellIterator) {
			Map<String, String> returnObject = new HashMap<>();
			returnObject.put(cellIterator.next().getStringCellValue(), cellIterator.next().getStringCellValue());
			return returnObject;
		}

		@Override
		public String getStartDataIndicator() {
			return "Header1";
		}
	}

}
