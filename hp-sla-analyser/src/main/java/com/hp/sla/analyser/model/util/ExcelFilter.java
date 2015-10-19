package com.hp.sla.analyser.model.util;

import java.io.File;

/**
 *
 * @author ramirmal
 */
public class ExcelFilter extends javax.swing.filechooser.FileFilter {

    @Override
    public boolean accept(File file) {
        // Allow only directories, or files with ".txt" extension
        return file.isDirectory() || file.getAbsolutePath().endsWith(".xlsx") || file.getAbsolutePath().endsWith(".xls");
    }

    @Override
    public String getDescription() {
        // This description will be displayed in the dialog,
        // hard-coded = ugly, should be done via I18N
        return "Excel documents (*.xlsx/*.xls)";
    }
}
