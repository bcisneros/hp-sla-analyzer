/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author ramirmal
 * @param <T> AuditParser or IncidentParser
 */
public abstract class ExcelParser<T> {

    public abstract List<T> parseDocument(Sheet sheet);
}
