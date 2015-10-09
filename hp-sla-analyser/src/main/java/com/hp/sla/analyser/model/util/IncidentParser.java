/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model.util;

import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 *
 * @author ramirmal
 */
public class IncidentParser extends ExcelParser{

    @Override
    public List parse(XSSFSheet sheet) {
        return parseDocument(sheet, "Incident Identifier");
    }

   
    
}
