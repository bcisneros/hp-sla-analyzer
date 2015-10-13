/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.sla.analyser.model;
import java.util.Date;  
import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Audit other = (Audit) obj;
        if (!Objects.equals(this.fieldDisplayName, other.fieldDisplayName)) {
            return false;
        }
        if (!Objects.equals(this.fieldName, other.fieldName)) {
            return false;
        }
        if (!Objects.equals(this.incidentID, other.incidentID)) {
            return false;
        }
        if (!Objects.equals(this.logicalDeleteFlag, other.logicalDeleteFlag)) {
            return false;
        }
        if (!Objects.equals(this.newVaueText, other.newVaueText)) {
            return false;
        }
        if (!Objects.equals(this.previousValueText, other.previousValueText)) {
            return false;
        }
        if (this.recordNumber != other.recordNumber) {
            return false;
        }
        if (!Objects.equals(this.systemModifiedUser, other.systemModifiedUser)) {
            return false;
        }
        if (!Objects.equals(this.systemModifiedTime, other.systemModifiedTime)) {
            return false;
        }
        return true;
    }
    
    
      
    @Override
    public String toString(){
        return "["+fieldDisplayName+", "+fieldName+", "+incidentID+", "+
                logicalDeleteFlag+", "+newVaueText+", "+previousValueText+", "+
                +recordNumber+", "+systemModifiedUser+", "+systemModifiedTime+"]";
    }
    
    
}
