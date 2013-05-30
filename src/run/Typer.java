/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package run;

import dataBase.ConnectionStatic;
import javax.swing.JFrame;


/**
 *
 * @author Marcin
 */
public class Typer {


    public static void main(String[] args) 
    {      
        //ConnectionStatic.connectToDB();
        
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //ConnectionStatic.closeConnection();
        //mainFrame.getConnectionManager().closeConnection();
    }
}
