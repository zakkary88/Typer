/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import javax.swing.DefaultListModel;

/**
 *
 * @author Marcin
 */
public class CurrentEvents extends javax.swing.JPanel {
    
    // kolejne niepotrzebne polaczenie !!!!!!!!!!!!!
    DataFromDB dataFromDB = new DataFromDB();
    
    private DefaultListModel listModelTodayBets = new DefaultListModel();
    
    public CurrentEvents() {
        initComponents();
        
        setFields();
        fillLists();
    }
    
    private void setFields()
    {
        jLabel1.setText("Today events:");
    }
    
    private void fillLists()
    {
        for(Bet b : dataFromDB.getTodayBets())
            listModelTodayBets.addElement(b);
        jListTodayEvents.setModel(listModelTodayBets);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListTodayEvents = new javax.swing.JList();

        jLabel1.setText("jLabel1");

        jScrollPane1.setViewportView(jListTodayEvents);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(328, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(204, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListTodayEvents;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
