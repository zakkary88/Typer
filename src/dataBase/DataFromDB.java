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
    private LinkedList<BetInProgression> endedBetsInProgToUpdate = new LinkedList<BetInProgression>();
    
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
        queryManager.viewEndedBetsInProgToUpdate(endedBetsInProgToUpdate);
    }
    
    //zrzutuje wszystkie do Bet (niewazne, na tej liscie nie wyswietla sie info)
    public int getActiveBetIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(Bet b : bets)
        {
            if(b.getBetId() == id)
                result = index;
            index++;              
        }    
        return result;
    }
    
    public int getActiveBetNotInProgIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(Bet bnip : betsNotInProg)
        {
            if(bnip.getBetId() == id)
                result = index;
            index++;              
        }    
        return result;
    }
    
    public int getActiveBetInProgIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(BetInProgression bip : betsInProg)
        {
            if(bip.getBetId() == id)
                result = index;
            index++;              
        }    
        return result;
    }
    
    public int getEndedBetToUpdateIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(Bet b : endedBetsToUpdate)
        {
            if(b.getBetId() == id)
                result = index;
            index++;              
        }    
        return result;
    }
    
    public int getEndedBetInProgToUpdateIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(BetInProgression bip : endedBetsInProgToUpdate)
        {
            if(bip.getBetId() == id)
                result = index;
            index++;              
        }    
        return result;
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
    
    public LinkedList<BetInProgression> getEndedBetsInProgToUpdate() 
    {
        return endedBetsInProgToUpdate;
    }
    
    public QueryManager getQueryManager() 
    {
        return queryManager;
    }
}
