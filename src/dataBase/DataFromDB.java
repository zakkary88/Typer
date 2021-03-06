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
    
    private LinkedList<Bet> resolvedBetsNotInProg = new LinkedList<Bet>();
    private LinkedList<BetInProgression> resolvedBetsInProg = new LinkedList<BetInProgression>();
    private LinkedList<Progression> resolvedProgressions = new LinkedList<Progression>();
    
    private LinkedList<Bet> wonBetsNotInProg = new LinkedList<Bet>();
    private LinkedList<Bet> lostBetsNotInProg = new LinkedList<Bet>();
    private LinkedList<Bet> canceledBetsNotInProg = new LinkedList<Bet>();
    private LinkedList<BetInProgression> wonBetsInProg = new LinkedList<BetInProgression>();
    private LinkedList<BetInProgression> lostBetsInProg = new LinkedList<BetInProgression>();
    private LinkedList<BetInProgression> canceledBetsInProg = new LinkedList<BetInProgression>();
    
    private double resolvedProgressionBalance = 0.0;
    
    //polaczenie
    private ConnectionManager connectionManager = new ConnectionManager();
    private QueryManager queryManager = new QueryManager(connectionManager.getConnection());
    
    public DataFromDB()
    {
        fillLists();
    }
    
    public void fillLists()
    {
        queryManager.viewActiveBetsOnList(bets);
        queryManager.viewActiveBetsNotInProgression(betsNotInProg);
        queryManager.viewActiveProgressions(progressions);
        queryManager.viewActiveBetsInProgression(betsInProg);
        
        queryManager.viewTodayBets(todayBets);
        queryManager.viewEndedBetsToUpdate(endedBetsToUpdate);
        queryManager.viewEndedBetsInProgToUpdate(endedBetsInProgToUpdate);
        
        queryManager.viewResolvedBetsNotInProgression(resolvedBetsNotInProg);
        queryManager.viewResolvedBetsInProgression(resolvedBetsInProg);
        queryManager.viewResolvedProgressions(resolvedProgressions);
        
        queryManager.viewWonBetsNotInProgression(wonBetsNotInProg);
        queryManager.viewLostBetsNotInProgression(lostBetsNotInProg);
        queryManager.viewCanceledBetsNotInProgression(canceledBetsNotInProg);
        
        queryManager.viewWonBetsInProgression(wonBetsInProg);
        queryManager.viewLostBetsInProgression(lostBetsInProg);
        queryManager.viewCanceledBetsInProgression(canceledBetsInProg);
    }
    
    public void clearAllLists()
    {
        bets.clear();
        betsNotInProg.clear();
        progressions.clear();
        betsInProg.clear();
        
        todayBets.clear();
        endedBetsToUpdate.clear();
        endedBetsInProgToUpdate.clear();
        
        resolvedBetsInProg.clear();
        resolvedBetsNotInProg.clear();
        resolvedProgressions.clear();
        
        wonBetsInProg.clear();
        lostBetsInProg.clear();
        canceledBetsInProg.clear();
        
        wonBetsNotInProg.clear();
        lostBetsNotInProg.clear();
        canceledBetsNotInProg.clear();
    }
    
    //zrzutuje wszystkie zaklady w gore do Bet (niewazne, na tej liscie nie wyswietla sie info)
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
    
    public int getWonBetNotInProgIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(Bet wbnip : wonBetsNotInProg)
        {
            if(wbnip.getBetId() == id)
                result = index;
            index++;
        }
        return result;
    }
    
    public int getLostBetNotInProgIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(Bet lbnip : lostBetsNotInProg)
        {
            if(lbnip.getBetId() == id)
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
    
    public int getLostBetInProgIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(BetInProgression lbip : lostBetsInProg)
        {
            if(lbip.getBetId() == id)
                result = index;
            index++;
        }
        return result;
    }
    
    public int getWonBetInProgIndexById(int id)
    {
        int index = 0;
        int result = 0;
        for(BetInProgression wbip : wonBetsInProg)
        {
            if(wbip.getBetId() == id)
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
    
    public int getProgressionsIndexById(int progressionId)
    {
        int index = 0;
        int result = 0;
        for(Progression p : progressions)
        {
            if(p.getProgressionId() == progressionId)
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
    
    public String getResolvedBetNotInProgInfo(Bet selectedBet)
    {
        return queryManager.viewResolvedBetNotInProgInfo(selectedBet.getBetId());
    }
    
    public String getResolvedBetInProgInfo(BetInProgression selectedBet)
    {
        return queryManager.viewResolvedBetInProgInfo(selectedBet.getBetId());
    }
    
    public String getResolvedProgressionInfo(Progression selectedProgression)
    {
        return queryManager.viewProgressionInfo(selectedProgression.getProgressionId());
    }
    
    public double getResolvedProgressionBalance(Progression selectedProgression)
    {
        return queryManager.viewResolvedProgressionBalance(selectedProgression.getProgressionId());
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
    
    public LinkedList<Bet> getResolvedBetsNotInProg() 
    {
        return resolvedBetsNotInProg;
    }
    
        public LinkedList<BetInProgression> getResolvedBetsInProg() 
    {
        return resolvedBetsInProg;
    }
    
    public LinkedList<Progression> getResolvedPregressions() 
    {
        return resolvedProgressions;
    }
    
    public LinkedList<Progression> getResolvedProgressions() 
    {
        return resolvedProgressions;
    }

    public LinkedList<Bet> getWonBetsNotInProg() 
    {
        return wonBetsNotInProg;
    }

    public LinkedList<Bet> getLostBetsNotInProg() 
    {
        return lostBetsNotInProg;
    }

    public LinkedList<Bet> getCanceledBetsNotInProg() 
    {
        return canceledBetsNotInProg;
    }

    public LinkedList<BetInProgression> getWonBetsInProg() 
    {
        return wonBetsInProg;
    }

    public LinkedList<BetInProgression> getLostBetsInProg() 
    {
        return lostBetsInProg;
    }

    public LinkedList<BetInProgression> getCanceledBetsInProg() 
    {
        return canceledBetsInProg;
    }

    public double getResolvedProgressionBalance() 
    {
        return resolvedProgressionBalance;
    }
    
    public QueryManager getQueryManager() 
    {
        return queryManager;
    }
}
    

