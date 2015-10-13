package com.hp.sla.analyser.model;

/**
 * Represents an exception thrown during the SLA analysis of an incident
 *
 * @author Benjamin Cisneros Barraza
 */
class SlaAnalysisException extends Exception {

    public SlaAnalysisException(String message) {
        super(message);
    }
}
