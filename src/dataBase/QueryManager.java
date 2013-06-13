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
    
    private PreparedStatement countAllBetsStmt = null;
    private PreparedStatement countAllProgressionsStmt = null;
    
    private PreparedStatement addBetStmt = null;
    private PreparedStatement addBetInProgressionStmt = null;
    private PreparedStatement addProgressionStmt = null;
    private PreparedStatement getIdForBetInProgressionExistingStmt = null;
    
    //WYŚWIETLANIE
    private PreparedStatement viewProgressionsNamesStmt = null;
    private PreparedStatement viewAllActiveBetsStmt = null;
    private PreparedStatement viewActiveBetsNotInProgressionStmt = null;
    private PreparedStatement viewActiveBetsInProgressionStmt = null;
    private PreparedStatement viewActiveProgressionsStmt = null;
    
    private PreparedStatement viewResolvedBetsNotInProgressionStmt = null;
    private PreparedStatement viewResolvedBetsInProgressionStmt = null;
    private PreparedStatement viewResolvedProgressionsStmt = null;
    
    private PreparedStatement viewWonBetsNotInProgressionStmt = null;
    private PreparedStatement viewLostBetsNotInProgressionStmt = null;
    private PreparedStatement viewCanceledBetsNotInProgressionStmt = null;
    private PreparedStatement viewWonBetsInProgressionStmt = null;
    private PreparedStatement viewLostBetsInProgressionStmt = null;
    private PreparedStatement viewCanceledBetsInProgressionStmt = null;
    private PreparedStatement viewWonProgressionsStmt = null;
    private PreparedStatement viewLostProgressionsStmt = null;
    
    private PreparedStatement viewBetNotInProgInfoStmt = null;
    private PreparedStatement viewBetInProgressionInfoStmt = null;
    private PreparedStatement viewProgressionInfoStmt = null;
    
    private PreparedStatement viewResolvedBetNotInProgInfoStmt = null;
    private PreparedStatement viewResolvedBetInProgInfoStmt = null;
    private PreparedStatement viewResolvedProgressionBalanceStmt = null;
    
    private PreparedStatement viewTodayBetsStmt = null;
    private PreparedStatement viewEndedBetsToUpdateStmt = null; 
    private PreparedStatement viewEndedBetsInProgressionToUpdateStmt = null;
    
    //UPDATES
    private PreparedStatement changeBetStatusStmt = null;
    private PreparedStatement changeWonBetBalanceStmt = null;
    private PreparedStatement changeLostBetBalanceStmt = null;
    private PreparedStatement endProgressionStmt = null;
    
    private PreparedStatement updateBetStmt = null;
    
  
    //ZAPYTANIA
    private static final String countAllBetsQuery = "SELECT count(*) FROM Bets";
    private static final String countAllProgressionsQuery = "SELECT count(*) FROM Progressions";
    
    //INSERTs
    //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
    // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
    // 0 - nie jest czescia progresji, 1 - status nierozstrzygniety, 0 - balance narazie na 0
    private static final String addBetQuery = "INSERT INTO Bets VALUES(?,?,?,?,?,0,1,?,?,0,?)";
    private static final String addBetInProgressionQuery = "INSERT INTO Bets Values(?,?,?,?,?,?,1,?,?,0,?)"; 
    private static final String addProgressionQuery = "INSERT INTO Progressions VALUES(?,?,1)"; // 1 - trwa
   
    private static final String getIdForBetInProgressionExistingQuery = "SELECT ProgressionId FROM Progressions "
            + "WHERE progressionName = ?";
    
    //nazwy progresji aktywnych
    private static final String viewProgressionsNamesQuery = "SELECT ProgressionName FROM Progressions "
            + "WHERE ProgressionStatus = 1";
    //wszystkie aktywne zakłady
    private static final String viewAllActiveBetsQuery = "SELECT * FROM Bets WHERE Status = 1";
    //wszystkie aktywne zaklady, ktore nie sa częścią progresji
    private static final String viewActiveBetsNotInPregressionQuery = "SELECT * FROM Bets WHERE Status = 1 "
            + "AND PartOfProgression = 0";
    //wszystkie aktywne zaklady, ktore sa czescia progresji
    private static final String viewActiveBetsInProgressionQuery = 
            "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Note, b.Type,"
            + " p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
            + " FROM Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId"
            + " AND b.Status = 1 AND b.PartOfProgression NOT LIKE 0 ";
    // wszystkie aktywne progresje
    private static final String viewActiveProgressionsQuery = "SELECT * FROM Progressions "
            + "WHERE ProgressionStatus = 1";
   
    //analogicznie - zakonczone (ze zmienionym statusem) progresje i zaklady
    private static final String viewResolvedBetsNotInProgressionQuery = "SELECT * FROM Bets WHERE "
            + "Status IN (2,3,4) AND PartOfProgression = 0";
    private static final String viewResolvedBetsInProgressionQuery = "SELECT b.BetId, "
            + "b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type, "
            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
            + "FROM Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId "
            + "AND b.Status IN (2,3,4) AND b.PartOfProgression NOT LIKE 0";
    private static final String viewResolvedProgressionsQuery = "SELECT * FROM Progressions "
            + "WHERE ProgressionStatus = 2";
    
    //bet status:   2 - wygrany     3 - przegrany    4 - anulowany
    private static final String viewWonBetsNotInProgressionQuery = "SELECT * FROM Bets WHERE "
            + "Status = 2 AND PartOfProgression = 0";
    private static final String viewLostBetsNotInProgressionQuery = "SELECT * FROM Bets WHERE "
            + "Status = 3 AND PartOfProgression = 0";
    private static final String viewCanceledBetsNotInProgressionQuery = "SELECT * FROM Bets WHERE "
            + "Status = 4 AND PartOfProgression = 0";
    
    private static final String viewWonBetsInProgressionQuery = "SELECT b.BetId, "
            + "b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type, "
            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
            + "FROM Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId "
            + "AND b.Status = 2 AND b.PartOfProgression NOT LIKE 0";
    private static final String viewLostBetsInProgressionQuery = "SELECT b.BetId, "
            + "b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type, "
            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
            + "FROM Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId "
            + "AND b.Status = 3 AND b.PartOfProgression NOT LIKE 0";
    private static final String viewCanceledBetsInProgressionQuery = "SELECT b.BetId, "
            + "b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type, "
            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
            + "FROM Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId "
            + "AND b.Status = 4 AND b.PartOfProgression NOT LIKE 0";

//    private PreparedStatement viewWonProgressionsStmt = null;
//    private PreparedStatement viewLostProgressionsStmt = null;
    
    private static final String viewBetNotInProgInfoQuery = "SELECT * FROM Bets WHERE BetId = ?";
    private static final String viewBetInProgressionInfoQuery = 
            "SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, p.ProgressionName "
            + "FROM Bets b, Progressions p WHERE BetId = ? "
            + "AND b.PartOfProgression = p.ProgressionId";
    private static final String viewProgressionInfoQuery = 
            "SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, b.Balance, p.ProgressionName "
            + "FROM Bets b, Progressions p WHERE ProgressionId = ? "
            + "AND b.PartOfProgression = p.ProgressionId";
    
    //zapytanie sie powatarza - wykonanie na probe
    private static final String viewResolvedBetNotInProgInfoQuery = "SELECT * FROM Bets WHERE BetId = ?";
    private static final String viewResolvedBetInProgInfoQuery = "SELECT b.BetName, b.Date, b.Odd, "
            + "b.Stake, b.Bukmacher, b.Type, b.Balance, p.ProgressionName "
            + "FROM Bets b, Progressions p WHERE BetId = ?";
    private static final String viewResolvedProgressionBalanceQuery = "SELECT SUM(b.Balance) "
            + "as ProgressionBalance FROM Bets b, Progressions p WHERE ProgressionId = ? " 
            + "AND b.PartOfProgression = p.ProgressionId";
            
    
    //zapytania dotyczące daty
    private static final String viewTodayBetsQuery = "SELECT * FROM Bets WHERE "
            + "SUBSTR(Date,0,11) LIKE date()";   
    private static final String viewEndedBetsToUpdateQuery = "SELECT * FROM Bets "
            + "WHERE SUBSTR(Date,0,11) < date() AND Status  = 1 AND "
            + "PartOfProgression = 0";         
    private static final String viewEndedBetsInProgressionToUpdateQuery = 
            "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Note, b.Type, "
            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus FROM "
            + "Bets b, Progressions p WHERE b.PartOfProgression = p.ProgressionId AND "
            + "SUBSTR(b.Date,0,11) < date() AND b.Status  = 1";
    
    //UPDATEs
    private static final String changeBetStatusQuery = "UPDATE Bets SET Status = ? "
            + "WHERE BetId = ?";
    private static final String changeWonBetBalanceQuery = "UPDATE Bets SET "
            + "Balance = round((Odd - 1.0) * Stake, 2) WHERE BetId = ?";
    private static final String changeLostBetBalanceQuery = "UPDATE Bets SET "
            + "Balance = - Stake WHERE BetId = ?";
    //zakonczenie progresji (1 - aktywna, 2 - zamknieta)
    private static final String endProgressionQuery = "UPDATE Progressions SET "
            + "ProgressionStatus = 2 WHERE ProgressionId = ?";
    
    //((1)betName, (2)date, (3)odd, (4)stake, (5)partOfProgression - progressionId,
    // (6)betStatus, (7)bukmacher, (8)note, (9)balance, (10)type)
    // (11)betId
    private static final String updateBetQuery = "UPDATE Bets SET "
            + "BetName = ?, Date = ?, Odd = ?, Stake = ?, PartOfProgression = ?, "
            + "Status = ?, Bukmacher = ?, Note = ?, Balance = ?, Type = ? WHERE "
            + "BetId = ?"; 
    
       // odzielnie zapytanie do pobierania NOTE !!!!
    
       // laczenie w tabelach przez JOIN    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
    public QueryManager(Connection conn)
    {
        this.conn = conn;
    }

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
    
    public void endProgression(int progressionId)
    {
        try
        {
            if(endProgressionStmt == null)
                endProgressionStmt = conn.prepareStatement(endProgressionQuery);
            
            endProgressionStmt.setInt(1, progressionId);
            
            int update = endProgressionStmt.executeUpdate();
            System.out.println(update + " updates. " + "Progression: " + progressionId + 
                    " ended");
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void updateBet(Bet bet)
    {
        try
        {
            if(updateBetStmt == null)
                updateBetStmt = conn.prepareStatement(updateBetQuery);
            
                //((1)betName, (2)date, (3)odd, (4)stake, (5)partOfProgression - progressionId,
                // (6)Status, (7)bukmacher, (8)note, (9)balance, (10)type)
                // (11)betId
            int id = bet.getBetId();
            updateBetStmt.setInt(11, id);
            
            int status = bet.getBetStatus();
            updateBetStmt.setInt(6, status);
            
            updateBetStmt.setString(1, bet.getBetName());
            updateBetStmt.setString(2, bet.getDate());
            updateBetStmt.setDouble(3, bet.getOdd());
            updateBetStmt.setDouble(4, bet.getStake());
            updateBetStmt.setInt(5, bet.getPartOfProgression());          
            updateBetStmt.setString(7, bet.getBukmacher());
            updateBetStmt.setString(8, bet.getNote());
            updateBetStmt.setString(10, bet.getType());
            
            int update = updateBetStmt.executeUpdate();
            System.out.println(update + " updates. " + "Bet: " + id);
            
            //update na podstawie statusu zakladu (wygrany/przegrany/ odwolany-nierozstrzygniety)
            if(status == 1 || status == 4)
                updateBetStmt.setDouble(9, 0.0);
            
            if(status == 2)  //wygrany
            {
                if(changeWonBetBalanceStmt == null)
                    changeWonBetBalanceStmt = conn.prepareStatement(changeWonBetBalanceQuery);
                
                changeWonBetBalanceStmt.setInt(1, id);
                
                int up = changeWonBetBalanceStmt.executeUpdate();
                System.out.println(up + " updates. " + "Bet: " + id + " won!");
            }
            
            if(status == 3)  //przegrany
            {
                if(changeLostBetBalanceStmt == null)
                    changeLostBetBalanceStmt = conn.prepareStatement(changeLostBetBalanceQuery);
                
                changeLostBetBalanceStmt.setInt(1, id);
                
                int up = changeLostBetBalanceStmt.executeUpdate();
                System.out.println(up + " updates. " + "Bet: " + id + " lost!");
            }       
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void changeBetStatus(int newStatus, int betId)
    {
        try
        {         
            if(changeBetStatusStmt == null)
                changeBetStatusStmt = conn.prepareStatement(changeBetStatusQuery);

            changeBetStatusStmt.setInt(1, newStatus);
            changeBetStatusStmt.setInt(2, betId);
            
            int update = changeBetStatusStmt.executeUpdate();
            System.out.println(update + " updates. " + "Bet: " + betId + 
                    " updated to status: " + newStatus);
                       
            //update na podstawie statusu zakladu (wygrany/przegrany)
            if(newStatus == 2)  //wygrany
            {
                if(changeWonBetBalanceStmt == null)
                    changeWonBetBalanceStmt = conn.prepareStatement(changeWonBetBalanceQuery);
                
                changeWonBetBalanceStmt.setInt(1, betId);
                
                int up = changeWonBetBalanceStmt.executeUpdate();
                System.out.println(up + " updates. " + "Bet: " + betId + " won!");
            }
            
            if(newStatus == 3)  //przegrany
            {
                if(changeLostBetBalanceStmt == null)
                    changeLostBetBalanceStmt = conn.prepareStatement(changeLostBetBalanceQuery);
                
                changeLostBetBalanceStmt.setInt(1, betId);
                
                int up = changeLostBetBalanceStmt.executeUpdate();
                System.out.println(up + " updates. " + "Bet: " + betId + " lost!");
            }
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
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
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewEndedBetsInProgToUpdate(LinkedList<BetInProgression> endedBetsInProgToUpdate)
    {
        try
        {
            if(viewEndedBetsInProgressionToUpdateStmt == null)
                viewEndedBetsInProgressionToUpdateStmt = conn.prepareStatement(viewEndedBetsInProgressionToUpdateQuery);
            
                resultSet = viewEndedBetsInProgressionToUpdateStmt.executeQuery();
                
                while(resultSet.next())
                {
                    //"SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Note, b.Type,"
              //+ " p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
              //  public BetInProgression(int betId, String betName, String date, double odd, double stake,
              // String bukmacher, String note, String type, int progressionId, String progressionName)
                BetInProgression endedBetInProg = new BetInProgression(
                        new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5),resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8)),
                                resultSet.getInt(9), 
                                resultSet.getString(10), resultSet.getInt(11));
                
                endedBetsInProgToUpdate.add(endedBetInProg);        
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
      
    public Bet getBetNotInProg(int id)
    {
        try
        {
            if(viewBetNotInProgInfoStmt == null)
                viewBetNotInProgInfoStmt = conn.prepareStatement(viewBetNotInProgInfoQuery);
            
            viewBetNotInProgInfoStmt.setInt(1, id);
            resultSet = viewBetNotInProgInfoStmt.executeQuery();
            Bet bet = null;
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //String bukmacher, String note, String type)
                bet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6),
                        resultSet.getInt(7), resultSet.getString(8),
                        resultSet.getString(9), resultSet.getDouble(10),
                        resultSet.getString(11));
            }
            return bet;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return new Bet();
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
                                resultSet.getInt(9), 
                                resultSet.getString(10), resultSet.getInt(11));
                
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
    
    public void viewWonBetsInProgression(LinkedList<BetInProgression> wonBetsInProgression)
    {
        try
        {
            if(viewWonBetsInProgressionStmt == null)
                viewWonBetsInProgressionStmt = conn.prepareStatement(viewWonBetsInProgressionQuery);
            
            resultSet = viewWonBetsInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
//           "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type"
//            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
//            public BetInProgression(int betId, String betName, String date, double odd, 
//            double stake, int betStatus, String bukmacher, String note, double balance, 
//            String type, int progressionId, String progressionName, int progressionStatus)

                BetInProgression wonBetInProg = new BetInProgression(
                        resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getString(8), 
                        resultSet.getDouble(9), resultSet.getString(10),
                                resultSet.getInt(11), 
                                resultSet.getString(12), resultSet.getInt(13));
               
                wonBetsInProgression.add(wonBetInProg);
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewLostBetsInProgression(LinkedList<BetInProgression> lostBetsInProgression)
    {
        try
        {
            if(viewLostBetsInProgressionStmt == null)
                viewLostBetsInProgressionStmt = conn.prepareStatement(viewLostBetsInProgressionQuery);
            
            resultSet = viewLostBetsInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
//           "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type"
//            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
//            public BetInProgression(int betId, String betName, String date, double odd, 
//            double stake, int betStatus, String bukmacher, String note, double balance, 
//            String type, int progressionId, String progressionName, int progressionStatus)

                BetInProgression lostBetInProg = new BetInProgression(
                        resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getString(8), 
                        resultSet.getDouble(9), resultSet.getString(10),
                                resultSet.getInt(11), 
                                resultSet.getString(12), resultSet.getInt(13));
               
                lostBetsInProgression.add(lostBetInProg);
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewCanceledBetsInProgression(LinkedList<BetInProgression> canceledBetsInProgression)
    {
        try
        {
            if(viewCanceledBetsInProgressionStmt == null)
                viewCanceledBetsInProgressionStmt = conn.prepareStatement(viewCanceledBetsInProgressionQuery);
            
            resultSet = viewCanceledBetsInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
//           "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type"
//            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
//            public BetInProgression(int betId, String betName, String date, double odd, 
//            double stake, int betStatus, String bukmacher, String note, double balance, 
//            String type, int progressionId, String progressionName, int progressionStatus)

                BetInProgression canceledBetInProg = new BetInProgression(
                        resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getString(8), 
                        resultSet.getDouble(9), resultSet.getString(10),
                                resultSet.getInt(11), 
                                resultSet.getString(12), resultSet.getInt(13));
               
                canceledBetsInProgression.add(canceledBetInProg);
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewResolvedBetsInProgression(LinkedList<BetInProgression> resolvedBetsInProgression)
    {
        try
        {
            if(viewResolvedBetsInProgressionStmt == null)
                viewResolvedBetsInProgressionStmt = conn.prepareStatement(viewResolvedBetsInProgressionQuery);
            
            resultSet = viewResolvedBetsInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
//           "SELECT b.BetId, b.BetName, b.Date, b.Odd, b.Stake, b.Status, b.Bukmacher, b.Note, b.Balance, b.Type"
//            + "p.ProgressionId, p.ProgressionName, p.ProgressionStatus "
//            public BetInProgression(int betId, String betName, String date, double odd, 
//            double stake, int betStatus, String bukmacher, String note, double balance, 
//            String type, int progressionId, String progressionName, int progressionStatus)

                BetInProgression newBetInProg = new BetInProgression(
                        resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6),
                        resultSet.getString(7), resultSet.getString(8), 
                        resultSet.getDouble(9), resultSet.getString(10),
                                resultSet.getInt(11), 
                                resultSet.getString(12), resultSet.getInt(13));
                
                resolvedBetsInProgression.add(newBetInProg);             
            }
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewWonBetsNotInProgression(LinkedList<Bet> wonBetsNotInProg)
    {
        try
        {
            if(viewWonBetsNotInProgressionStmt == null)
                viewWonBetsNotInProgressionStmt = conn.prepareStatement(viewWonBetsNotInProgressionQuery);
            
            resultSet = viewWonBetsNotInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //int betStatus, String bukmacher, String note, double balance, String type)
                Bet wonBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7),
                        resultSet.getString(8), resultSet.getString(9), 
                        resultSet.getDouble(10), resultSet.getString(11));

                wonBetsNotInProg.add(wonBet);
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewLostBetsNotInProgression(LinkedList<Bet> lostBetsNotInProg)
    {
        try
        {
            if(viewLostBetsNotInProgressionStmt == null)
                viewLostBetsNotInProgressionStmt = conn.prepareStatement(viewLostBetsNotInProgressionQuery);
            
            resultSet = viewLostBetsNotInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //int betStatus, String bukmacher, String note, double balance, String type)
                Bet lostBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7),
                        resultSet.getString(8), resultSet.getString(9), 
                        resultSet.getDouble(10), resultSet.getString(11));

                lostBetsNotInProg.add(lostBet);
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewCanceledBetsNotInProgression(LinkedList<Bet> canceledBetsNotInProg)
    {
        try
        {
            if(viewCanceledBetsNotInProgressionStmt == null)
                viewCanceledBetsNotInProgressionStmt = conn.prepareStatement(viewCanceledBetsNotInProgressionQuery);
            
            resultSet = viewCanceledBetsNotInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                //public Bet(int betId, String betName, String date, double odd, double stake, 
                //int betStatus, String bukmacher, String note, double balance, String type)
                Bet canceledBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7),
                        resultSet.getString(8), resultSet.getString(9), 
                        resultSet.getDouble(10), resultSet.getString(11));

                canceledBetsNotInProg.add(canceledBet);
            }
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewResolvedBetsNotInProgression(LinkedList<Bet> resolvedBetsNotInProg)
    {
        try
        {
            if(viewResolvedBetsNotInProgressionStmt == null)
                viewResolvedBetsNotInProgressionStmt = conn.prepareStatement(viewResolvedBetsNotInProgressionQuery);
            
            resultSet = viewResolvedBetsNotInProgressionStmt.executeQuery();
            
            while(resultSet.next())
            {                          
                //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
            //public Bet(int betId, String betName, String date, double odd, double stake, 
            //int betStatus, String bukmacher, String note, double balance, String type)
                Bet newBet = new Bet(resultSet.getInt(1), resultSet.getString(2), 
                        resultSet.getString(3), resultSet.getDouble(4), 
                        resultSet.getDouble(5), resultSet.getInt(6), resultSet.getInt(7),
                        resultSet.getString(8), resultSet.getString(9), 
                        resultSet.getDouble(10), resultSet.getString(11));

                resolvedBetsNotInProg.add(newBet); 
            }           
            
            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public void viewResolvedProgressions(LinkedList<Progression> resolvedProgressions)
    {
        try
        {
            if(viewResolvedProgressionsStmt == null)
                viewResolvedProgressionsStmt = conn.prepareStatement(viewResolvedProgressionsQuery);
            
            resultSet = viewResolvedProgressionsStmt.executeQuery();
            
            while(resultSet.next())
            {
                Progression newProgression = new Progression(resultSet.getInt(1),
                        resultSet.getString(2), resultSet.getInt(3));
                resolvedProgressions.add(newProgression);
            }

            resultSet.close();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
       
    public String viewResolvedBetNotInProgInfo(int id)
    {
        try
        {
            if(viewResolvedBetNotInProgInfoStmt == null)
                viewResolvedBetNotInProgInfoStmt = conn.prepareStatement(viewResolvedBetNotInProgInfoQuery);
            
                viewResolvedBetNotInProgInfoStmt.setInt(1, id);
                resultSet = viewResolvedBetNotInProgInfoStmt.executeQuery();
                String info = "";
                             
                while(resultSet.next())
                {
                    //((1)betId, (2)betName, (3)date, (4)odd, (5)stake, (6)partOfProgression - progressionId,
                // (7)betStatus, (8)bukmacher, (9)note, (10)balance, (11)type)
                    info = "Name: " + resultSet.getString(2) + "\nBalance: " + resultSet.getDouble(10)
                        + "\nType: " + resultSet.getString(11) + "\nBukmacher: " + resultSet.getString(8)
                        + "\nDate: " + resultSet.getString(3) + "\nOdd: " + resultSet.getDouble(4) 
                        + "\nStake: " + resultSet.getDouble(5); 
                }
                
                resultSet.close();
                return info;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return "ResolvedBetNotInProgression Info Error";
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
            return "BetNotInProgression Info Error";
        }
    }
    
    public String viewResolvedBetInProgInfo(int id)
    {
        try
        {
            if(viewResolvedBetInProgInfoStmt == null)
                viewResolvedBetInProgInfoStmt = conn.prepareStatement(viewResolvedBetInProgInfoQuery);
            
            viewResolvedBetInProgInfoStmt.setInt(1, id);      
            resultSet = viewResolvedBetInProgInfoStmt.executeQuery();
            String info = "";
            
            while(resultSet.next())
            {
                //SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, b.Balance, p.ProgressionName FROM
                info = "Name: " + resultSet.getString(1) + "\nBalance: " + resultSet.getDouble(7) +
                        "\nType: " + resultSet.getString(6) + "\nBukmacher: " + resultSet.getString(5) +
                        "\nDate: " + resultSet.getString(2) + "\nOdd: " + resultSet.getDouble(3) + 
                        "\nStake: " + resultSet.getDouble(4) + "\nIn progression: " + resultSet.getString(8);               
            }
            
            resultSet.close();
            return info;
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
            return "ResolvedBetInProgression Info Error";
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
    
    public double viewResolvedProgressionBalance(int id)
    {
        try
        {
            if(viewResolvedProgressionBalanceStmt == null)
                viewResolvedProgressionBalanceStmt = conn.prepareStatement(viewResolvedProgressionBalanceQuery);
            
            viewResolvedProgressionBalanceStmt.setInt(1, id);
            resultSet = viewResolvedProgressionBalanceStmt.executeQuery();
            
            double result = resultSet.getDouble(1);
            
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
    
    public String viewProgressionInfo(int id)
    {
        try
        {
            if(viewProgressionInfoStmt == null)
                viewProgressionInfoStmt = conn.prepareStatement(viewProgressionInfoQuery);
            
            viewProgressionInfoStmt.setInt(1, id);
            resultSet = viewProgressionInfoStmt.executeQuery();
            String info = "Progression name: " + resultSet.getString(8);
            
            while(resultSet.next())
            {
              //  "SELECT b.BetName, b.Date, b.Odd, b.Stake, b.Bukmacher, b.Type, p.ProgressionName "
              //+ "FROM Bets b, Progressions p WHERE ProgressionId = ? "
              //+ "AND b.PartOfProgression = p.ProgressionId";
                info += "\nBet name: " + resultSet.getString(1) + "\tType: " + resultSet.getString(6) +
                        "\tBalance: " + resultSet.getDouble(7) +
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
    
    public void commitChanges()
    {
        try
        {
            conn.commit();
        }
        catch(SQLException e)
        {
            for(Throwable t : e)
                System.out.println(t.getMessage());
        }
    }
    
    public Connection getConn() 
    {
        return conn;
    }
}
