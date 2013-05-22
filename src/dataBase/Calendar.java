/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import javax.swing.JComboBox;

/**
 *
 * @author Marcin
 */
public class Calendar {
    
    private JComboBox jComboBoxDay = null;
    private JComboBox jComboBoxMonth = null;
    private JComboBox jComboBoxYear = null;
    private JComboBox jComboBoxHour = null;
    private JComboBox jComboBoxMinute = null;
    
    public Calendar(JComboBox jComboBoxDay, JComboBox jComboBoxMonth,
            JComboBox jComboBoxYear, JComboBox jComboBoxHour, JComboBox jComboBoxMinute)
    {
        this.jComboBoxDay = jComboBoxDay;
        this.jComboBoxMonth = jComboBoxMonth;
        this.jComboBoxYear = jComboBoxYear;
        this.jComboBoxHour = jComboBoxHour;
        this.jComboBoxMinute = jComboBoxMinute;
    }
    
    public void fillComboBoxDateTime()
    {
        for(int i=1; i<=31; i++)
        {
            if(i < 10)
                jComboBoxDay.addItem("0" + i);
            else
                jComboBoxDay.addItem(i);
        }
        
        for(int i=1; i<=12; i++)
        {
            if(i < 10)
                jComboBoxMonth.addItem("0" + i);
            else
                jComboBoxMonth.addItem(i);
        }
     
        for(int i=2013; i<=2053; i++)
            jComboBoxYear.addItem(i);
        
        for(int i=0; i<=55; i++)
        {
            if(i%5 == 0)
            {
                if(i < 10)
                   jComboBoxMinute.addItem("0" + i); 
                else
                    jComboBoxMinute.addItem(i);
            }
        }
                   
        for(int i=1; i<=24; i++)
        {
            if(i < 10)
                jComboBoxHour.addItem("0" + i);
            else
                jComboBoxHour.addItem(i);
        }          
    }
    
    public String setDate()
    {      
        //YYYY-MM-DD HH:MM
        return jComboBoxYear.getSelectedItem().toString() + "-" + 
                jComboBoxMonth.getSelectedItem().toString()+ "-" +
                jComboBoxDay.getSelectedItem().toString() + " " + 
                jComboBoxHour.getSelectedItem().toString() +
                ":" + jComboBoxMinute.getSelectedItem().toString();
    }
    
}
