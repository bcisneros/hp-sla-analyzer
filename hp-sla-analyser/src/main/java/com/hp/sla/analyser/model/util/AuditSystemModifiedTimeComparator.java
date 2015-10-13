package com.hp.sla.analyser.model.util;

import com.hp.sla.analyser.model.Audit;
import java.util.Comparator;

/**
 *
 * @author Benjamin Cisneros Barraza
 */
public class AuditSystemModifiedTimeComparator implements Comparator<Audit> {

    @Override
    public int compare(Audit audit1, Audit audit2) {
        return audit1.getSystemModifiedTime().compareTo(audit2.getSystemModifiedTime());
    }

}
