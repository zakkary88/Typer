/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.util.LinkedList;

/**
 *
 * @author Marcin
 * 
 * Klasa przetrzymuje w listach tabele Bets i Progressions
 */
public class DataFromDB {
    
    private LinkedList<Bet> bets = new LinkedList<Bet>();
    private LinkedList<Bet> betsNotInProg = new LinkedList<Bet>();
    private LinkedList<BetInProgression> betsInProg = new LinkedList<BetInProgression>();
    private LinkedList<Progression> progressions = new LinkedList<Progression>();    
     
    private LinkedList<Bet> todayBets = new LinkedList<Bet>();
    private LinkedList<Bet> endedBetsToUpdate = new LinkedList<Bet>();
    
    //polaczenie
    private ConnectionManager connectionManager = new ConnectionManager();
    private QueryManager queryManager = new QueryManager(connectionManager.getConnection());
    
    public DataFromDB()
    {
        fillLists();
    }
    
    private void fillLists()
    {
        queryManager.viewActiveBetsOnList(bets);
        queryManager.viewActiveBetsNotInProgression(betsNotInProg);
        queryManager.viewActiveProgressions(progressions);
        queryManager.viewActiveBetsInProgression(betsInProg);
        queryManager.viewTodayBets(todayBets);
        queryManager.viewEndedBetsToUpdate(endedBetsToUpdate);
    }
    
    public String getBetNotInProgInfo(Bet selectedBet)
    {
        return queryManager.viewBetNotInProgInfo(selectedBet.getBetId());
    }
    
    public String getBetInProgressionInfo(BetInProgression selectedBet)
    {
        return queryManager.viewBetInProgressionInfo(selectedBet.getBetId());
    }
    
    public String getProgressionInfo(Progression selectedProgression)
    {
        return queryManager.viewProgressionInfo(selectedProgression.getProgressionId());
    }

    public LinkedList<Bet> getBets() 
    {
        return bets;
    }
    
    public LinkedList<Bet> getBetsNotInProg() 
    {
        return betsNotInProg;
    }

    public LinkedList<BetInProgression> getBetsInProg() 
    {
        return betsInProg;
    }

    public LinkedList<Progression> getProgressions() 
    {
        return progressions;
    }
       
    public LinkedList<Bet> getTodayBets() 
    {
        return todayBets;
    }
    
    public LinkedList<Bet> getEndedBetsToUpdate()
    {
        return endedBetsToUpdate;
    }
}
