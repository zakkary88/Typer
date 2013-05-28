/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Marcin
 */
public class DataContainer {
   
    public static DataFromDB dataFromDB = null; 
    
    public static int id = 0;
    public static Object object = null;
    
//    public static JList jListEndedBetsToUpdate = new JList();
//    public static JList jListEndedBetsInProgToUpdate = new JList();
//    public static JList jListTodayBets = new JList();
    
    public static DefaultListModel listModelAllActive = new DefaultListModel();
    public static DefaultListModel listModelActiveNotInProg = new DefaultListModel();
    public static DefaultListModel listModelActiveInProg = new DefaultListModel();
    public static DefaultListModel listModelProgressions = new DefaultListModel();
    
    public static DefaultListModel listModelTodayBets = new DefaultListModel();
    public static DefaultListModel listModelEndedBetsToUpdate = new DefaultListModel();
    public static DefaultListModel listModelEndedBetsInProgToUpdate = new DefaultListModel();
    
    public static DefaultListModel listModelResolvedBetsNotInProg = new DefaultListModel();
    public static DefaultListModel listModelResolvedBetsInProg = new DefaultListModel();
    public static DefaultListModel listModelResolvedProgressions = new DefaultListModel();
    
    public static void fillAllActiveBetsList()
    {
        for(Bet b : dataFromDB.getBets())
        {
            listModelAllActive.addElement(b);
        }
    }
    
    public static void fillActiveBetsNotInProgression()
    {
        for(Bet bnip : dataFromDB.getBetsNotInProg())
        {
            listModelActiveNotInProg.addElement(bnip);
        }
    }
    
    public static void fillActiveBetsInProgression()
    {   
        for(BetInProgression bip : dataFromDB.getBetsInProg())
        {
            listModelActiveInProg.addElement(bip);
        }
    }
    
    public static void fillActiveProgressions()
    {     
        for(Progression p : dataFromDB.getProgressions())
        {
            listModelProgressions.addElement(p);
        }
    }
    
    public static void fillTodayBets()
    {
        for(Bet b : dataFromDB.getTodayBets())
            listModelTodayBets.addElement(b);
        //jListTodayBets.setModel(listModelTodayBets);
    }
    
    public static void fillEndedBetsToUpdate()
    {
        for(Bet b : dataFromDB.getEndedBetsToUpdate())
            listModelEndedBetsToUpdate.addElement(b);
        //jListEndedBetsToUpdate.setModel(listModelEndedBetsToUpdate);
    }
    
    public static void fillEndedBetsInProgToUpdate()
    {
        for(BetInProgression bip : dataFromDB.getEndedBetsInProgToUpdate())
            listModelEndedBetsInProgToUpdate.addElement(bip);
        //jListEndedBetsInProgToUpdate.setModel(listModelEndedBetsInProgToUpdate);
    }
    
    public static void fillResolvedBetsNotInProg()
    {
        for(Bet b : dataFromDB.getResolvedBetsNotInProg())
            listModelResolvedBetsNotInProg.addElement(b);
    }
    
    public static void fillResolvedBetsInProg()
    {
        for(BetInProgression bip : dataFromDB.getResolvedBetsInProg())
            listModelResolvedBetsInProg.addElement(bip);
    }
    
    public static void fillResolvedProgressions()
    {
        for(Progression p : dataFromDB.getResolvedPregressions())
            listModelResolvedProgressions.addElement(p);
    }
}
