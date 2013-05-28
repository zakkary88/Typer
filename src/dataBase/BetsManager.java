/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BetsManager.java
 *
 * Created on 2013-04-04, 19:49:57
 */
package dataBase;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

/**
 *
 * @author Marcin
 */
public class BetsManager extends javax.swing.JPanel {

    private DataFromDB dataFromDB = null; 
    
//    private DefaultListModel listModelAllActive = new DefaultListModel();
//    private DefaultListModel listModelActiveNotInProg = new DefaultListModel();
//    private DefaultListModel listModelActiveInProg = new DefaultListModel();
//    private DefaultListModel listModelProgressions = new DefaultListModel();
    
//    private DefaultListModel listModelTodayBets = new DefaultListModel();
//    private DefaultListModel listModelEndedBetsToUpdate = new DefaultListModel();
//    private DefaultListModel listModelEndedBetsInProgToUpdate = new DefaultListModel();

    //mozliwe, ze nie beda zmiennee potrzebne!!! tylko datacontainer
    //uaktuanic dzialanie list w oparciu o static datacontainer
    
    public BetsManager() 
    {
        initComponents();
        this.setSize(400, 400);
        
        dataFromDB = new DataFromDB();
        
        //zmiany
        DataContainer.dataFromDB = dataFromDB;
        //
        
        setFields();
        fillLists();
             
    }
    
    private void setFields()
    {
        jLabel2.setText("Today events:");
        jLabel3.setText("Ended events to update:");
        jLabel4.setText("Ended events in progression:");
        
        jLabel1.setText("Bet status:");
        jTextAreaBetInfo.setText("Choose other Bet Status to view bet information.");
        jTextAreaBetInfo.setEditable(false);
        fillJComboBoxStatus();
        
        jButtonUpdate.setText("Update");
        jLabelInformation.setText("");
        
        jListTodayBets.setEnabled(false);
    }
       
    //zmiany -  nie bylo datacontainer
    private void fillLists()
    {
        DataContainer.fillActiveBetsInProgression();
        DataContainer.fillActiveBetsNotInProgression();
        DataContainer.fillAllActiveBetsList();
        DataContainer.fillActiveProgressions();
        
        DataContainer.fillTodayBets();
        DataContainer.fillEndedBetsToUpdate();
        DataContainer.fillEndedBetsInProgToUpdate();
        fillTodayBets();
        fillEndedBetsToUpdate();
        fillEndedBetsInProgToUpdate();
        
        DataContainer.fillResolvedBetsNotInProg();
        DataContainer.fillResolvedBetsInProg();
        DataContainer.fillResolvedProgressions();
    }
    
    private void fillJComboBoxStatus()
    {
        //aktywne
        jComboBoxBetStatus.addItem("All active bets");
        jComboBoxBetStatus.addItem("Active bets not in progressions");
        jComboBoxBetStatus.addItem("Active bets in progressions");
        jComboBoxBetStatus.addItem("Active progressions");
        
        //zakonczone
        jComboBoxBetStatus.addItem("Resolved bets not in progressions");
        jComboBoxBetStatus.addItem("Resolved bets in progressions");
        jComboBoxBetStatus.addItem("Resolved progressions");
    }
    
    // NIE WSZYSTKIE SA UZYWANE
    /*
    private void fillAllActiveBetsList()
    {
        for(Bet b : dataFromDB.getBets())
        {
            listModelAllActive.addElement(b);
        }
    }
    
    private void fillActiveBetsNotInProgression()
    {
        for(Bet bnip : dataFromDB.getBetsNotInProg())
        {
            listModelActiveNotInProg.addElement(bnip);
        }
    }
    
    private void fillActiveBetsInProgression()
    {   
        for(BetInProgression bip : dataFromDB.getBetsInProg())
        {
            listModelActiveInProg.addElement(bip);
        }
    }
    
    private void fillActiveProgressions()
    {     
        for(Progression p : dataFromDB.getProgressions())
        {
            listModelProgressions.addElement(p);
        }
    }
    */
    
    //to tez mozna do container przeniesc, ale setModel idzie do konstuktora u gory
    private void fillTodayBets()
    {
//        for(Bet b : dataFromDB.getTodayBets())
//            DataContainer.listModelTodayBets.addElement(b);
        jListTodayBets.setModel(DataContainer.listModelTodayBets);
    }
    
    private void fillEndedBetsToUpdate()
    {
//        for(Bet b : dataFromDB.getEndedBetsToUpdate())
//            DataContainer.listModelEndedBetsToUpdate.addElement(b);
        jListEndedBetsToUpdate.setModel(DataContainer.listModelEndedBetsToUpdate);
    }
    
    private void fillEndedBetsInProgToUpdate()
    {
//        for(BetInProgression bip : dataFromDB.getEndedBetsInProgToUpdate())
//            DataContainer.listModelEndedBetsInProgToUpdate.addElement(bip);
        jListEndedBetsInProgToUpdate.setModel(DataContainer.listModelEndedBetsInProgToUpdate);
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jComboBoxBetStatus = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListBets = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaBetInfo = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListTodayBets = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListEndedBetsToUpdate = new javax.swing.JList();
        jButtonUpdate = new javax.swing.JButton();
        jLabelInformation = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListEndedBetsInProgToUpdate = new javax.swing.JList();
        jSeparator1 = new javax.swing.JSeparator();

        jLabel1.setText("jLabel1");

        jComboBoxBetStatus.setModel(new javax.swing.DefaultComboBoxModel(new String[] { }));
        jComboBoxBetStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxBetStatusActionPerformed(evt);
            }
        });

        jListBets.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListBets.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListBetsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jListBets);

        jTextAreaBetInfo.setColumns(20);
        jTextAreaBetInfo.setRows(5);
        jScrollPane2.setViewportView(jTextAreaBetInfo);

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jListTodayBets.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jListTodayBets);

        jListEndedBetsToUpdate.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListEndedBetsToUpdate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListEndedBetsToUpdateValueChanged(evt);
            }
        });
        jScrollPane4.setViewportView(jListEndedBetsToUpdate);

        jButtonUpdate.setText("jButton1");
        jButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateActionPerformed(evt);
            }
        });

        jLabelInformation.setText("jLabel4");

        jLabel4.setText("jLabel4");

        jListEndedBetsInProgToUpdate.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListEndedBetsInProgToUpdate.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListEndedBetsInProgToUpdateValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(jListEndedBetsInProgToUpdate);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButtonUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel3)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jLabelInformation)
                    .addComponent(jComboBoxBetStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jSeparator1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(jButtonUpdate)
                .addGap(7, 7, 7)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxBetStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelInformation)
                .addGap(18, 18, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    //TODO
    //przy przelaczaniu combobox na x.getSelectedValue pojawia sie NULL !!!
    
    //doszlo data container
    private void jComboBoxBetStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxBetStatusActionPerformed
        
        //problem selekcji
        //jListBets.clearSelection();
        jListBets.setSelectedIndex(0);
            
//        Timer timer = new Timer();
//        timer.schedule(new TimerTask(), 100);
        
//        if(jComboBoxBetStatus.getSelectedItem().toString().equals("All active bets"))
//                {           
//                    jListBets.setModel(listModelAllActive);
//                   // jListBets.repaint();
//
//                    jListBets.updateUI();
//                    jTextAreaBetInfo.setText("Choose other Bet Status to view bet information.");
//                }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            
            public void run() {
                
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("All active bets"))
                {           
                    jListBets.setModel(DataContainer.listModelAllActive);
                   // jListBets.repaint();

                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("Choose other Bet Status to view bet information.");
                }       
                
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("Active bets not in progressions"))
                {
                    jListBets.setModel(DataContainer.listModelActiveNotInProg);
                    //jListBets.repaint();
                    //jListBets.setSelectedIndex(0);
                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("");
                }
                
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("Active bets in progressions"))
                {
                    jListBets.setModel(DataContainer.listModelActiveInProg);
                    //jListBets.repaint();
                    //jListBets.setSelectedIndex(0);
                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("");
                }
        
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("Active progressions"))
                {
                    jListBets.setModel(DataContainer.listModelProgressions);
                    //jListBets.repaint();
                    //jListBets.setSelectedIndex(0);
                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("");
                }
        
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("Resolved bets not in progressions"))
                {
                    jListBets.setModel(DataContainer.listModelResolvedBetsNotInProg);
                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("");
                }
        
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("Resolved bets in progressions"))
                {
                    jListBets.setModel(DataContainer.listModelResolvedBetsInProg);
                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("");
                }
        
                if(jComboBoxBetStatus.getSelectedItem().toString().equals("Resolved progressions"))
                {
                    jListBets.setModel(DataContainer.listModelResolvedProgressions);
                    jListBets.updateUI();
                    jTextAreaBetInfo.setText("");
                }
            }
        });
         
    }//GEN-LAST:event_jComboBoxBetStatusActionPerformed
    
    //TODO
    //przy przelaczaniu combobox na x.getSelectedValue pojawia sie NULL !!!
    private void jListBetsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListBetsValueChanged
        
       // String info = "";
        //jListBets.setSelectedIndex(0);        // DZIAŁA !!!
   
        
            java.awt.EventQueue.invokeLater(new Runnable() {
                
                public void run() {
               
                    String info = "";
                    
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("All active bets"))
                    {
                        info = "Choose other Bet Status to view bet information.";           
                    }
        
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("Active bets not in progressions"))
                    {
                        info = dataFromDB.getBetNotInProgInfo((Bet)jListBets.getSelectedValue());
                    }
        
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("Active bets in progressions"))
                    {
                        info = dataFromDB.getBetInProgressionInfo((BetInProgression)jListBets.getSelectedValue());
                    }
            
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("Active progressions"))
                    {
                        info = dataFromDB.getProgressionInfo((Progression)jListBets.getSelectedValue());       
                    }
        
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("Resolved bets not in progressions"))
                    {
                        info = dataFromDB.getResolvedBetNotInProgInfo((Bet) jListBets.getSelectedValue());
                    }
                    
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("Resolved bets in progressions"))
                    {
                        info = dataFromDB.getResolvedBetInProgInfo((BetInProgression) jListBets.getSelectedValue());
                    }
                    
                    if(jComboBoxBetStatus.getSelectedItem().toString().equals("Resolved progressions"))
                    {
                        double balance = dataFromDB.getResolvedProgressionBalance((Progression) jListBets.getSelectedValue());
                        info = "Progression balance: " + Double.toString(balance) + "\n" +
                                dataFromDB.getProgressionInfo((Progression) jListBets.getSelectedValue());
                    }
        
                    jTextAreaBetInfo.setText(info);
            }
        });    
    }//GEN-LAST:event_jListBetsValueChanged

    private void jButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateActionPerformed
        
        if(jListEndedBetsToUpdate.getSelectedValue() == null
                && jListEndedBetsInProgToUpdate.getSelectedValue() == null)
        {
            jLabelInformation.setText("Choose ended bet to update from one of lists in the top.");
        }
        else        
        {
            String betInfo = "";
            
            if(jListEndedBetsToUpdate.getSelectedValue() != null)
            {
                DataContainer.object = DataContainer.listModelEndedBetsToUpdate.getElementAt
                        (jListEndedBetsToUpdate.getSelectedIndex());
                Bet selectedBet = (Bet) DataContainer.object;
                int id = selectedBet.getBetId();
                DataContainer.id = id;
                betInfo = DataContainer.dataFromDB.getBetNotInProgInfo(selectedBet);
            }
            
            if(jListEndedBetsInProgToUpdate.getSelectedValue() != null)
            {
                DataContainer.object = DataContainer.listModelEndedBetsInProgToUpdate.getElementAt
                        (jListEndedBetsInProgToUpdate.getSelectedIndex());
                BetInProgression selectedBet = (BetInProgression) DataContainer.object;
                int id = selectedBet.getBetId();
                DataContainer.id = id;
                betInfo = DataContainer.dataFromDB.getBetInProgressionInfo(selectedBet);
            }         
            
            //otwiera okno - wszystkie zmiany zachodza w UpdateBet po kliknieciu Confirm
            UpdateBet updateBet = new UpdateBet();
            updateBet.setVisible(true);
            updateBet.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            updateBet.getLists(jListEndedBetsToUpdate, jListEndedBetsInProgToUpdate, jListTodayBets);
      
            updateBet.getjTextAreaInfo().setText(betInfo);
        }
    }//GEN-LAST:event_jButtonUpdateActionPerformed

    private void jListEndedBetsToUpdateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListEndedBetsToUpdateValueChanged
        
        jListEndedBetsInProgToUpdate.clearSelection();
    }//GEN-LAST:event_jListEndedBetsToUpdateValueChanged

    private void jListEndedBetsInProgToUpdateValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListEndedBetsInProgToUpdateValueChanged
        
        jListEndedBetsToUpdate.clearSelection();
    }//GEN-LAST:event_jListEndedBetsInProgToUpdateValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox jComboBoxBetStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelInformation;
    private javax.swing.JList jListBets;
    private javax.swing.JList jListEndedBetsInProgToUpdate;
    private javax.swing.JList jListEndedBetsToUpdate;
    private javax.swing.JList jListTodayBets;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextAreaBetInfo;
    // End of variables declaration//GEN-END:variables
}
