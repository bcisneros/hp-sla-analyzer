package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.io.Serializable;
import java.util.Comparator;

/**
 * This class compares two Audit objects based in the field ModifiedTime
 * @author Benjamin Cisneros Barraza
 */
public class AuditSystemModifiedTimeComparator implements Comparator<Audit>, Serializable {

    private static final long serialVersionUID = -2678745435609373548L;

    @Override
    public int compare(Audit audit1, Audit audit2) {
        return audit1.getSystemModifiedTime().compareTo(audit2.getSystemModifiedTime());
    }

}
