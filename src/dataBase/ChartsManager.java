/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.awt.BorderLayout;


/**
 *
 * @author Marcin
 */
public class ChartsManager extends javax.swing.JPanel {

    private ChartsGenerator chartsGenerator = null;
    /**
     * Creates new form ChartsManager
     */
    public ChartsManager() {
        initComponents();
        this.setSize(400, 400);
        
        chartsGenerator = new ChartsGenerator();
        
        setFields();
    }
    
    private void setFields()
    {
        jComboBoxChartType.addItem("Efficiency");
        jComboBoxChartType.addItem("Efficiency in progressions");
        jComboBoxChartType.addItem("Efficiency not in progressions");
        jComboBoxChartType.addItem("Yield");
        jComboBoxChartType.addItem("Yield in progressions");
        jComboBoxChartType.addItem("Yield not in progressions");
        jComboBoxChartType.addItem("Won/Lost by months");
        jComboBoxChartType.addItem("Won/Lost in progressions by months");
        jComboBoxChartType.addItem("Won/Lost not in progressions by months");
        
        jPanelChart.setLayout(new BorderLayout());       
    }
          

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxChartType = new javax.swing.JComboBox();
        jPanelChart = new javax.swing.JPanel();

        jComboBoxChartType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxChartTypeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelChartLayout = new javax.swing.GroupLayout(jPanelChart);
        jPanelChart.setLayout(jPanelChartLayout);
        jPanelChartLayout.setHorizontalGroup(
            jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelChartLayout.setVerticalGroup(
            jPanelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 252, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBoxChartType, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 200, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBoxChartType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxChartTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxChartTypeActionPerformed
            
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() 
            {      
                jComboBoxChartType.removeAll();
                
                if(jComboBoxChartType.getSelectedItem().toString().equals("Efficiency"))
                    jPanelChart.add(chartsGenerator.drawEfficiencyChart(), BorderLayout.CENTER);
           
                if(jComboBoxChartType.getSelectedItem().toString().equals("Efficiency in progressions"))
                    jPanelChart.add(chartsGenerator.drawEfficiencyInProgressionChart(), BorderLayout.CENTER);
                    
                if(jComboBoxChartType.getSelectedItem().toString().equals("Efficiency not in progressions"))
                    jPanelChart.add(chartsGenerator.drawEfficiencyNotInProgChart(), BorderLayout.CENTER);
        
                if(jComboBoxChartType.getSelectedItem().toString().equals("Yield"))
                    jPanelChart.add(chartsGenerator.drawYieldByDatesChart(), BorderLayout.CENTER);
                
                if(jComboBoxChartType.getSelectedItem().toString().equals("Yield in progressions"))
                    jPanelChart.add(chartsGenerator.drawYieldByDatesInProgChart(), BorderLayout.CENTER);
                
                if(jComboBoxChartType.getSelectedItem().toString().equals("Yield not in progressions"))
                    jPanelChart.add(chartsGenerator.drawYieldByDatesNotInProgChart(), BorderLayout.CENTER);
                
                if(jComboBoxChartType.getSelectedItem().toString().equals("Won/Lost by months"))
                    jPanelChart.add(chartsGenerator.drawWonLostBarChart(), BorderLayout.CENTER);
                
                if(jComboBoxChartType.getSelectedItem().toString().equals("Won/Lost in progressions by months"))
                    jPanelChart.add(chartsGenerator.drawWonLostInProgBarChart(), BorderLayout.CENTER);
                
                if(jComboBoxChartType.getSelectedItem().toString().equals("Won/Lost not in progressions by months"))
                    jPanelChart.add(chartsGenerator.drawWonLostNotInProgBarChart(), BorderLayout.CENTER);

                jPanelChart.validate();
            }
        });

        
    }//GEN-LAST:event_jComboBoxChartTypeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBoxChartType;
    private javax.swing.JPanel jPanelChart;
    // End of variables declaration//GEN-END:variables
}
