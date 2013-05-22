/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.JComboBox;

/**
 *
 * @author Marcin
 */
public class QueryManager {
    
    private Connection conn = null;
    private ResultSet resultSet = null;
    
    private PreparedStatement viewResolvedBetsStms = null;
    private PreparedStatement viewWonBetsStmt = null;
    private PreparedStatement viewLostBetsStmt = null;
    
    private PreparedStatement countAllBetsStmt = null;
    private PreparedStatement countAllProgressionsStmt = null;
    
    private PreparedStatement addBetStmt = null;
    private PreparedStatement addBetInProgressionStmt = null;
    private PreparedStatement addProgressionStmt = null;
    private PreparedStatement getIdForBetInProgressionExistingStmt = null;
    
    private PreparedStatement viewProgressionsNamesStmt = null;
    private PreparedStatement viewAllActiveBetsStmt = null;
    private PreparedStatement viewActiveBetsNotInProgressionStmt = null;
    private PreparedStatement viewActiveBetsInProgressionStmt = null;
    private PreparedStatement viewActiveProgressionsStmt = null;
    
    private PreparedStatement viewBetNotInProgInfoStmt = null;
    private PreparedStatement viewBetInProgressionInfoStmt = null;
    private PreparedStatement viewProgressionInfoStmt = null;
    
    private PreparedStatement viewTodayBetsStmt = null;
    private PreparedStatement viewEndedBetsToUpdateStmt = null;
            
    private static final String viewResolvedBetsQuery = "SELECT * FROM Bets WHERE Status = 2 OR Status = 3";  // OR ??
    private static final String viewWonBetsQuery = "SELECT * FROM Bets WHERE Status = 2";
    private static final String viewLostBetsQuery = "SELECT * FROM Bets WHERE Status = 3";        
    
    private static final String countAllBetsQuery = "SELECT count(*) FROM Bets";
    private static final String countAllProgressionsQuery = "SELECT count(*) FROM Progressions";
   
    // KLUCZE OBCE ???   
    //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
    // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
    // 0 - nie jest czescia progresji, 1 - status nierozstrzygniety, 0 - saldo narazie na 0
    private static final String addBetQuery = "INSERT INTO Bets VALUES(?,?,?,?,?,0,1,?,?,0,?)";
    private static final String addBetInProgressionQuery = "INSERT INTO Bets Values(?,?,?,?,?,?,1,?,?,0,?)"; 
    private static final String addProgressionQuery = "INSERT INTO Progressions VALUES(?,?,1)"; // 1 - trwa
    private static final String getIdForBetInProgressionExistingQuery = "SELECT ProgressionId FROM Progressions "
            + "WHERE progressionName = ?";
    
    //nazwy progresji
    private static final String viewProgressionsNamesQuery = "SELECT ProgressionName FROM Progressions";
    //wszystkie aktywne zakłady
    private static final String viewAllActiveBetsQuery = "SELECT * FROM Bets WHERE Status = 1";
    //wszystkie aktywne zaklady, ktore nie sa częścią progresji
    private static final String viewActiveBetsNotInPregressionQuery = "SELECT * FROM Bets WHERE Status = 1 AND PartOfProgression = 0";
    //wszystkie aktywne zaklady, ktore sa czescia progresji
    private static final String viewActiveBetsInProgressionQuery = 
            "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Note, b.Type,"
            + " p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
            + " FROM Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId"
            + " AND b.Status = 1 AND b.PartOfProgression NOT LIKE 0 ";
    // wszystkie aktywne progresje
    private static final String viewActiveProgressionsQuery = "SELECT * FROM Progressions WHERE ProgressionStatus = 1";
    // PROGRESJE ZAKONCZONE TEZ TRZEBA WYSWIETLC !!!!!!!!!!!!!!
    
    private static final String viewBetNotInProgInfoQuery = "SELECT * FROM Bets WHERE BetId = ?";
    private static final String viewBetInProgressionInfoQuery = 
            "SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, p.ProgressionName "
            + "FROM Bets b, Progressions p WHERE BetId = ?";
    private static final String viewProgressionInfoQuery = 
            "SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, p.ProgressionName "
            + "FROM Bets b, Progressions p WHERE ProgressionId = ? "
            + "AND b.PartOfProgression = p.ProgressionId";
    
    //zapytania dotyczące daty
    private static final String viewTodayBetsQuery = "SELECT * FROM Bets WHERE "
            + "SUBSTR(Date,0,11) LIKE date()";
    private static final String viewEndedBetsToUpdateQuery = "SELECT * FROM Bets "
            + "WHERE SUBSTR(Date,0,11) < date() AND Status  = 1";       //SPRAWDZIC, CZY DOBRZE!!!
    
    
       // odzielnie zapytanie do pobierania NOTE !!!!
    
       // laczenie w tabelach przez JOIN    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    public QueryManager(Connection conn)
    {
        this.conn = conn;
    }
    
//    public QueryManager()
//    {
//        this.conn = ConnectionStatic.getConnection();
//    }
//    
    public int countAllBets()
    {
        try
        {
            if(countAllBetsStmt == null)
                countAllBetsStmt = conn.prepareStatement(countAllBetsQuery);
            
            resultSet = countAllBetsStmt.executeQuery();
            
            int result = resultSet.getInt(1);
            
            resultSet.close();
            return result;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return -1;
        }
    }
    
    public int countAllProgressions()
    {
        try{    
            if(countAllProgressionsStmt == null)
                countAllProgressionsStmt = conn.prepareStatement(countAllProgressionsQuery);

            resultSet = countAllProgressionsStmt.executeQuery();

            int result = resultSet.getInt(1);

            resultSet.close();
            return result;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return -1;
        }     
    }
    
    private int getIdForBetInProgressionExisting(String progressionName)
    {
        try
        {
            if(getIdForBetInProgressionExistingStmt == null)
                getIdForBetInProgressionExistingStmt = conn.prepareStatement(getIdForBetInProgressionExistingQuery);
            
            getIdForBetInProgressionExistingStmt.setString(1, progressionName);
            
            resultSet = getIdForBetInProgressionExistingStmt.executeQuery();
            
            int result = resultSet.getInt(1);
            
            resultSet.close();
            return result;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return -1;
        }
    }
    
    private int setId()
    {
        return countAllBets() +  1;
    }
    
    private int setIdForProgression()
    {
        return countAllProgressions() + 1;
    }
                
    public void addBet(String betName, String date, double odd, double stake,
            String bukmacher, String note, String type)
    {
        try
        {
            if(addBetStmt == null)
                addBetStmt = conn.prepareStatement(addBetQuery);
            
            int id = setId();
            
            addBetStmt.setInt(1, id);
            addBetStmt.setString(2, betName);
            addBetStmt.setString(3, date);
            addBetStmt.setDouble(4, odd);
            addBetStmt.setDouble(5, stake);
            addBetStmt.setString(6, bukmacher);
            addBetStmt.setString(7, note);
            addBetStmt.setString(8, type);
            
            int result = addBetStmt.executeUpdate();
            System.out.println(result + " inserted");
            System.out.println(id + " " + betName);          
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void addBetInProgressionExisting (String betName, String date, double odd, double stake,
             String bukmacher, String note, String type, String progressionName)
    {
        try
        {
            if(addBetInProgressionStmt == null)
                addBetInProgressionStmt = conn.prepareStatement(addBetInProgressionQuery);       
            
            int betId = setId();
            int progressionId = getIdForBetInProgressionExisting(progressionName);

            addBetInProgressionStmt.setInt(1, betId);
            addBetInProgressionStmt.setString(2, betName);
            addBetInProgressionStmt.setString(3, date);
            addBetInProgressionStmt.setDouble(4, odd);
            addBetInProgressionStmt.setDouble(5, stake);
            addBetInProgressionStmt.setInt(6, progressionId);
            addBetInProgressionStmt.setString(7, bukmacher);
            addBetInProgressionStmt.setString(8, note);
            addBetInProgressionStmt.setString(9, type);
            
            int result = addBetInProgressionStmt.executeUpdate();
            System.out.println(result + " inserted");
            System.out.println(betId + " " + betName);
            
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
     
    public void addBetInProgressionNew(String betName, String date, double odd, double stake,
            String bukmacher, String note, String type, String progressionName)
    {
        try
        {                      
            //dodaje zakład
            if(addBetInProgressionStmt == null)
                addBetInProgressionStmt = conn.prepareStatement(addBetInProgressionQuery);
            
            if(addProgressionStmt == null)
                addProgressionStmt = conn.prepareStatement(addProgressionQuery);
            
            int betId = setId();
            int progressionId = setIdForProgression();

            addBetInProgressionStmt.setInt(1, betId);
            addBetInProgressionStmt.setString(2, betName);
            addBetInProgressionStmt.setString(3, date);
            addBetInProgressionStmt.setDouble(4, odd);
            addBetInProgressionStmt.setDouble(5, stake);
            addBetInProgressionStmt.setInt(6, progressionId);
            addBetInProgressionStmt.setString(7, bukmacher);
            addBetInProgressionStmt.setString(8, note);
            addBetInProgressionStmt.setString(9, type);
            
            int result = addBetInProgressionStmt.executeUpdate();
            System.out.println(result + " inserted");
            System.out.println(betId + " " + betName); 
            
            
            //dodaje progresję          
            addProgressionStmt.setInt(1, progressionId);
            addProgressionStmt.setString(2, progressionName);
            
            result = addProgressionStmt.executeUpdate();
            System.out.println(result + " inserted");
            System.out.println(progressionId + " " + progressionName);                  
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }   
    }
    
    public void fillComboBoxExisitingProgression(JComboBox jComboBoxExistingProgression)
    {
        try
        {
            if(viewProgressionsNamesStmt == null)
                viewProgressionsNamesStmt = conn.prepareStatement(viewProgressionsNamesQuery);
            
            resultSet = viewProgressionsNamesStmt.executeQuery();
            while(resultSet.next())
            {
                jComboBoxExistingProgression.addItem(resultSet.getString(1));
            }
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }      
    }
    
    public void viewEndedBetsToUpdate(LinkedList<Bet> endedBetsToUpdate)
    {
        try
        {
            if(viewEndedBetsToUpdateStmt == null)
                viewEndedBetsToUpdateStmt = conn.prepareStatement(viewEndedBetsToUpdateQuery);
            
                resultSet = viewEndedBetsToUpdateStmt.executeQuery();
                
                while(resultSet.next())
                {
                    //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                    // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                    //public Bet(int betId, String betName, String date, double odd, double stake, 
                    //String bukmacher, String note, String type)
                    Bet endedBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getString(8),
                        resultSet.getString(9), resultSet.getString(11));
                    //dodaje od razu jako nierozstrzygniete - konstruktor
                    endedBetsToUpdate.add(endedBet);
                    //System.out.println(resultSet.getString(3));
                }
                
                resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewTodayBets(LinkedList<Bet> todayBets)
    {
        try
        {
            if(viewTodayBetsStmt == null)
                viewTodayBetsStmt = conn.prepareStatement(viewTodayBetsQuery);
            
            resultSet = viewTodayBetsStmt.executeQuery();
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //String bukmacher, String note, String type)
                Bet todayBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getString(8),
                        resultSet.getString(9), resultSet.getString(11));
                //dodaje od razu jako nierozstrzygniete - konstruktor
               todayBets.add(todayBet);
               //System.out.println(resultSet.getString(3));
            }
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewActiveProgressions(LinkedList<Progression> progressions)
    {
        try
        {
            if(viewActiveProgressionsStmt == null)
                viewActiveProgressionsStmt = conn.prepareStatement(viewActiveProgressionsQuery);
            
            resultSet = viewActiveProgressionsStmt.executeQuery();
            
            while(resultSet.next())
            {
                Progression newProgression = new Progression(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getInt(3));
                progressions.add(newProgression);
            }
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewActiveBetsOnList(LinkedList<Bet> bets)
    {
        try
        {
            if(viewAllActiveBetsStmt == null)
                viewAllActiveBetsStmt = conn.prepareStatement(viewAllActiveBetsQuery);
            
            resultSet = viewAllActiveBetsStmt.executeQuery();
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //String bukmacher, String note, String type)
                Bet newBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getString(8),
                        resultSet.getString(9), resultSet.getString(11));
                //dodaje od razu jako nierozstrzygniete - konstruktor
                bets.add(newBet);                                             
            }
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }      
    }
    
    public void viewActiveBetsNotInProgression(LinkedList<Bet> betsNotInProg)
    {
        try
        {
            if(viewActiveBetsNotInProgressionStmt == null)
                viewActiveBetsNotInProgressionStmt = conn.prepareStatement(viewActiveBetsNotInPregressionQuery);
            
            resultSet = viewActiveBetsNotInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //String bukmacher, String note, String type)
                Bet newBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getString(8),
                        resultSet.getString(9), resultSet.getString(11));
                //dodaje od razu jako nierozstrzygniete - konstruktor
                betsNotInProg.add(newBet); 
            }           
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewActiveBetsInProgression(LinkedList<BetInProgression> betsInProgression)
    {
        try
        {
            if(viewActiveBetsInProgressionStmt == null)
                viewActiveBetsInProgressionStmt = conn.prepareStatement(viewActiveBetsInProgressionQuery);
            
            resultSet = viewActiveBetsInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
              //"SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Note, b.Type,"
              //+ " p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
              //  public BetInProgression(int betId, String betName, String date, double odd, double stake,
              // String bukmacher, String note, String type, int progressionId, String progressionName)

                BetInProgression newBetInProg = new BetInProgression(
                        new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5),resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8)),
                                new Progression(resultSet.getInt(9), 
                                resultSet.getString(10), resultSet.getInt(11)));
                
                betsInProgression.add(newBetInProg);
            }
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    
    public String viewBetNotInProgInfo(int id)
    {
        try
        {
            if(viewBetNotInProgInfoStmt == null)
                viewBetNotInProgInfoStmt = conn.prepareStatement(viewBetNotInProgInfoQuery);
            
            viewBetNotInProgInfoStmt.setInt(1, id);      
            resultSet = viewBetNotInProgInfoStmt.executeQuery();
            String info = "";
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                info = "Name: " + resultSet.getString(2) + "\nType: " + resultSet.getString(11)
                        + "\nDate: " + resultSet.getString(3) + "\nOdd: " + resultSet.getDouble(4) 
                        + "\nStake: " + resultSet.getDouble(5) +  "\nBukmacher: " + resultSet.getString(8);
            }
            
            resultSet.close();
            return info;
        }
        catch(SQLException e)
        {
            for(Throwable t :e)
                System.out.println(t.getMessage());
            return "BetNotInProgression Error";
        }
    }
    
    public String viewBetInProgressionInfo(int id)
    {
        try
        {
            if(viewBetInProgressionInfoStmt == null)
                viewBetInProgressionInfoStmt = conn.prepareStatement(viewBetInProgressionInfoQuery);
            
            viewBetInProgressionInfoStmt.setInt(1, id);
            resultSet = viewBetInProgressionInfoStmt.executeQuery();
            String info = "";
            
            while(resultSet.next())
            {
                //"SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, p.ProgressionName "
                //+ "FROM Bets b, Progressions p WHERE BetId = ?";
                info = "Name: " + resultSet.getString(1) + "\nType: " + resultSet.getString(6) +
                        "\nDate: " + resultSet.getString(2) + "\nOdd: " + resultSet.getDouble(3) + 
                        "\nStake: " + resultSet.getDouble(4) + "\nBukmacher: " + resultSet.getString(5) + 
                        "\nIn progression: " + resultSet.getString(7);
            }
            
            resultSet.close();
            return info;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return "BetInProgression Error";
        }
    }
    
    public String viewProgressionInfo(int id)
    {
        try
        {
            if(viewProgressionInfoStmt == null)
                viewProgressionInfoStmt = conn.prepareStatement(viewProgressionInfoQuery);
            
            viewProgressionInfoStmt.setInt(1, id);
            resultSet = viewProgressionInfoStmt.executeQuery();
            String info = "Progression name: " + resultSet.getString(7);
            
            while(resultSet.next())
            {
              //  "SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, p.ProgressionName "
              //+ "FROM Bets b, Progressions p WHERE ProgressionId = ? "
              //+ "AND b.PartOfProgression = p.ProgressionId";
                info += "\nBet name: " + resultSet.getString(1) + "\"nType: " + resultSet.getString(6) +
                        "\tDate: " + resultSet.getString(2) + "\tOdd: " + resultSet.getDouble(3) + 
                        "\tStake: " + resultSet.getDouble(4) + "\tBukmacher: " + resultSet.getString(5);
            }
            
            resultSet.close();
            return info;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return "Progression Error";
        }
    }
}
