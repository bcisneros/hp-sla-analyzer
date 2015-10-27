package com.hp.sla.analyser.view;

import com.hp.sla.analyser.model.BaseSlaReportGeneratorObserver;
import com.hp.sla.analyser.model.Incident;
import com.hp.sla.analyser.model.SlaReportGenerationException;
import com.hp.sla.analyser.model.SlaReportGenerator;
import com.hp.sla.analyser.model.SlaReportGeneratorObserver;
import com.hp.sla.analyser.model.util.ExcelFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * The main form to perform an SLA analysis
 *
 * @author Benjamin Cisneros Barraza
 */
public class SlaAnalyserMainForm extends javax.swing.JFrame {

    private final SlaReportGenerationProgressDialog progressDialog;
    private final transient SlaReportGeneratorObserver observer = new SwingSlaReportGenerator();

    /**
     * Creates new form SlaAnalyserMainForm
     */
    public SlaAnalyserMainForm() {
        super("SLA Report");
        setResizable(false);
        initComponents();
        setLocationRelativeTo(null);
        progressDialog = new SlaReportGenerationProgressDialog(null, true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        generateSLAButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        file1NameLabel = new javax.swing.JLabel();
        incidentsFileTextField = new javax.swing.JTextField();
        browseIncidentsFileButton = new javax.swing.JButton();
        file2NameLabel = new javax.swing.JLabel();
        assignmentAuditsTextField = new javax.swing.JTextField();
        browseAssignmentAuditsFileButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        outputDirectoryLabel = new javax.swing.JLabel();
        outputDirectoryTextField = new javax.swing.JTextField();
        browseOutputDirectoryButton = new javax.swing.JButton();

        fileChooser.setFileFilter(new ExcelFilter());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        generateSLAButton.setText("Generate SLA Report");
        generateSLAButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateSLAButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Input Files"));

        file1NameLabel.setText("Incident Ticket File:");

        incidentsFileTextField.setEditable(false);

        browseIncidentsFileButton.setText("Browse...");
        browseIncidentsFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseIncidentsFileButtonActionPerformed(evt);
            }
        });

        file2NameLabel.setText("Assigment Audit File:");

        assignmentAuditsTextField.setEditable(false);

        browseAssignmentAuditsFileButton.setText("Browse...");
        browseAssignmentAuditsFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseAssignmentAuditsFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(file2NameLabel)
                    .addComponent(file1NameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(incidentsFileTextField)
                    .addComponent(assignmentAuditsTextField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(browseAssignmentAuditsFileButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(browseIncidentsFileButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(file1NameLabel)
                    .addComponent(incidentsFileTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseIncidentsFileButton))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(file2NameLabel)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(assignmentAuditsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(browseAssignmentAuditsFileButton)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Output Files"));

        outputDirectoryLabel.setText("Output Directory:");

        outputDirectoryTextField.setEditable(false);
        outputDirectoryTextField.setText("C:\\temp\\");

            browseOutputDirectoryButton.setText("Browse...");
            browseOutputDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    browseOutputDirectoryButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(outputDirectoryLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(outputDirectoryTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 367, Short.MAX_VALUE)
                    .addGap(9, 9, 9)
                    .addComponent(browseOutputDirectoryButton)
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(outputDirectoryLabel)
                        .addComponent(outputDirectoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(browseOutputDirectoryButton))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addGap(224, 224, 224)
                    .addComponent(generateSLAButton, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addGap(208, 208, 208))
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(generateSLAButton)
                    .addGap(22, 22, 22))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void browseIncidentsFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseIncidentsFileButtonActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            String fileName = file.toString();
            incidentsFileTextField.setText(fileName);
        }
    }//GEN-LAST:event_browseIncidentsFileButtonActionPerformed

    private void browseAssignmentAuditsFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseAssignmentAuditsFileButtonActionPerformed
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            String fileName = file.toString();
            assignmentAuditsTextField.setText(fileName);
        }
    }//GEN-LAST:event_browseAssignmentAuditsFileButtonActionPerformed

    private void generateSLAButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateSLAButtonActionPerformed
        final SlaReportGenerator slaRG = new SlaReportGenerator();
        slaRG.setObserver(observer);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progressDialog.setVisible(true);
            }
        });
        new SwingWorker<Object, Object>() {
            @Override
            protected Object doInBackground() throws Exception {

                try {
                    slaRG.generateReport(incidentsFileTextField.getText(),
                            assignmentAuditsTextField.getText(),
                            outputDirectoryTextField.getText());
                } catch (SlaReportGenerationException slaReportGenerationException) {
                    JOptionPane.showMessageDialog(null, "Could not generate the report due: " + slaReportGenerationException, "SLA Report Generation Error", JOptionPane.ERROR_MESSAGE);
                }
                return null;
            }
        }.execute();
    }//GEN-LAST:event_generateSLAButtonActionPerformed

    private void browseOutputDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseOutputDirectoryButtonActionPerformed
        JFileChooser directoryChooser = new JFileChooser("C:\\temp\\");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnedValue = directoryChooser.showOpenDialog(this);
        if (JFileChooser.APPROVE_OPTION == returnedValue) {
            outputDirectoryTextField.setText(directoryChooser.getSelectedFile().getPath() + "\\");
        }
    }//GEN-LAST:event_browseOutputDirectoryButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField assignmentAuditsTextField;
    private javax.swing.JButton browseAssignmentAuditsFileButton;
    private javax.swing.JButton browseIncidentsFileButton;
    private javax.swing.JButton browseOutputDirectoryButton;
    private javax.swing.JLabel file1NameLabel;
    private javax.swing.JLabel file2NameLabel;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton generateSLAButton;
    private javax.swing.JTextField incidentsFileTextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel outputDirectoryLabel;
    private javax.swing.JTextField outputDirectoryTextField;
    // End of variables declaration//GEN-END:variables

    private class SwingSlaReportGenerator extends BaseSlaReportGeneratorObserver {

        public SwingSlaReportGenerator() {
        }

        @Override
        public void onStartReportGeneration(SlaReportGenerator slaReportGenerator) {
            progressDialog.clearComponents();
        }

        @Override
        public void onFinalizeReportGeneration(SlaReportGenerator slaReportGenerator) {
            JOptionPane.showMessageDialog(progressDialog, "Report created correctly:\n"
                    + slaReportGenerator.getGeneratedReportFile(), "Report Generation Process",
                    JOptionPane.INFORMATION_MESSAGE);
            progressDialog.doClose(SlaReportGenerationProgressDialog.RET_CANCEL);
        }

        @Override
        public void notifyProcessPhase(SlaReportGenerator aThis, String string) {
            progressDialog.appendMessage(string);
        }

        @Override
        public void reportCurrentIncident(Incident incident, int i) {
            progressDialog.appendMessage("Analyzing " + incident.getId() + " incident.");
            progressDialog.showProgress(i, observer.getTotal());
        }

        @Override
        public void onReportGenerationError(Exception ex) {
            progressDialog.doClose(SlaReportGenerationProgressDialog.RET_CANCEL);
        }
    }
}
