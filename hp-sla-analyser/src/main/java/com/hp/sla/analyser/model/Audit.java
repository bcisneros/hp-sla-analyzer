/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model;
import java.util.Date;  

/**
 *
 * @author ramirmal
 */
public class Audit {
    public String fieldDisplayName;
    public String fieldName;
    public String incidentID;
    public Boolean logicalDeleteFlag;
    public String newVaueText;
    public String previousValueText;
    public int recordNumber;
    public String systemModifiedUser;
    public Date systemModifiedTime;     

    public String getFieldDisplayName() {
        return fieldDisplayName;
    }

    public void setFieldDisplayName(String fieldDisplayName) {
        this.fieldDisplayName = fieldDisplayName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getIncidentID() {
        return incidentID;
    }

    public void setIncidentID(String incidentID) {
        this.incidentID = incidentID;
    }

    public Boolean getLogicalDeleteFlag() {
        return logicalDeleteFlag;
    }

    public void setLogicalDeleteFlag(Boolean logicalDeleteFlag) {
        this.logicalDeleteFlag = logicalDeleteFlag;
    }

    public String getNewVaueText() {
        return newVaueText;
    }

    public void setNewVaueText(String newVaueText) {
        this.newVaueText = newVaueText;
    }

    public String getPreviousValueText() {
        return previousValueText;
    }

    public void setPreviousValueText(String previousValueText) {
        this.previousValueText = previousValueText;
    }

    public int getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }

    public String getSystemModifiedUser() {
        return systemModifiedUser;
    }

    public void setSystemModifiedUser(String systemModifiedUser) {
        this.systemModifiedUser = systemModifiedUser;
    }

    public Date getSystemModifiedTime() {
        return systemModifiedTime;
    }

    public void setSystemModifiedTime(Date systemModifiedTime) {
        this.systemModifiedTime = systemModifiedTime;
    }
    
    
    
    
}
