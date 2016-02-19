package com.hp.sla.analyser.model.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * This is a generic class to parse an Excel document and convert its data rows
 * to Audit or Incident Lists.
 *
 * @author Mallinali Ramirez Corona
 * @param <T>
 *            AuditParser or IncidentParser
 */
public abstract class ExcelParser<T> {

	private static final Logger logger = Logger.getLogger(IncidentParser.class);

	/**
	 * Create a new object from the contents in the row
	 *
	 * @param cellIterator
	 *            the cell iterator of a row in a sheet that contain the data to
	 *            create a new object
	 * @return a new object
	 */
	public abstract T createObject(Iterator<Cell> cellIterator);

	/**
	 * Defines the String header of data to parse
	 *
	 * @return
	 */
	public abstract String getStartDataIndicator();

	/**
	 * This method takes an excel sheet and obtains the data contained in the
	 * rows that represent tickets or audits
	 *
	 * @param sheet
	 *            the excel sheet from where the objects will be extracted
	 * @return a list of objects (Tickets or Audits)
	 * @throws com.hp.sla.analyser.model.util.ExcelParsingException
	 */
	public List<T> parseDocument(Sheet sheet) throws ExcelParsingException {
		List<T> objects = new ArrayList<>();
		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();
		logger.info("Start parsing document.");
		boolean dataStart = false;
		// Traversing over each row of XLSX file
		logger.info("Iterating the XLSX file.");
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			// For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			if (dataStart) {
				/*
				 * After the data in both files there are unusable rows On Audit
				 * parser the last line has just one cell written On Incident
				 * parser it will end in the row that contains the word 'Grand
				 * Total' that is the penultimate row
				 */
				final String firstCellValue = row.getCell(0).getStringCellValue();
				if (row.getLastCellNum() <= 1 || firstCellValue.equals("Grand Total")
						|| firstCellValue.startsWith("HP")) {
					break;
				}
				T obj = createObject(cellIterator);
				objects.add(obj);
			} else if (cellIterator.hasNext()
					&& cellIterator.next().getStringCellValue().equals(getStartDataIndicator())) {
				dataStart = true;
			}

		}
		if (!dataStart) {
			throw new ExcelParsingException("Required columns not found");
		}
		return objects;
	}

}
