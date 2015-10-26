package com.hp.sla.analyser.model;

/**
 * Represents an error during the process of report generation.
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaReportGenerationException extends Exception {

    private static final long serialVersionUID = 3226961927176235765L;

    /**
     * Constructor who takes a String and call super class constructor
     *
     * @param message
     */
    SlaReportGenerationException(String message) {
        super(message);
    }

}
