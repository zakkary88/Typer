/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marcin
 */
public class ConnectionStatic {
    
    private static Connection conn = null;
    
    public ConnectionStatic()
    {
        connectToDB();
    }
    
    public static void connectToDB()
    {           
        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch(ClassNotFoundException e)
        {  
            System.out.println(e);
        }

        try
        {
            conn = DriverManager.getConnection("jdbc:sqlite:TyperDB");
            System.out.println("Polaczenie z SQLite");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public static void closeConnection()
    {
        try
        {
            conn.close();
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }     
    }
    
    public static Connection getConnection()
    {
        return conn;
    }
}
