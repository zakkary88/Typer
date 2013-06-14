/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

/**
 *
 * @author Marcin
 * 
 * Obiekt klasy jest tworzony, gdy zostanie zaznaczona opcja - Part of progession
 * przy dodawaniu nowego zakładu.
 */
public class BetInProgression extends Bet{
    
    private Progression progression;
    
    public BetInProgression() {}
    
    public BetInProgression(int betId, String betName, String date, double odd, double stake,
            String bukmacher, String note, String type, int progressionId, String progressionName)
    {
        super(betId, betName, date, odd, stake, progressionId, bukmacher, note, type);
        progression = new Progression(progressionId, progressionName);
    }
    
    public BetInProgression(int betId, String betName, String date, double odd, 
            double stake, String bukmacher, String note, String type, 
            int progressionId, String progressionName, int progressionStatus)
    {
        super(betId, betName, date, odd, stake, progressionId, bukmacher, note, type);
        progression = new Progression(progressionId, progressionName, progressionStatus);
    }
    
    public BetInProgression(int betId, String betName, String date, double odd, 
            double stake, int betStatus, String bukmacher, String note, double balance, 
            String type, int progressionId, String progressionName, int progressionStatus)
    {
        //public Bet(int betId, String betName, String date, double odd, double stake, int partOfProgression,
        //    int betStatus, String bukmacher, String note, double balance, String type)
        super(betId, betName, date, odd, stake, progressionId, betStatus, bukmacher, note, balance, type);
        progression = new Progression(progressionId, progressionName, progressionStatus);
    }
       
    public BetInProgression(Bet bet, int progressionId, String progressionName)
    {
        super(bet.getBetId(), bet.getBetName(), bet.getDate(), bet.getOdd(), 
                bet.getStake(), progressionId, bet.getBukmacher(), bet.getNote(), bet.getType());
        progression = new Progression(progressionId, progressionName);
    }
    
    public BetInProgression(Bet bet, int progressionId, String progressionName, int progressionStatus)
    {
        super(bet.getBetId(), bet.getBetName(), bet.getDate(), bet.getOdd(),
                bet.getStake(), progressionId, bet.getBukmacher(), bet.getNote(), bet.getType());
        progression = new Progression(progressionId, progressionName, progressionStatus);
    }
    
    @Override
    public String toString()
    {
        return betId + " " + betName + " | " + progression.getProgressionName() 
                + " " + progression.getProgressionId();
    }
    
    public Progression getProgression() 
    {
        return progression;
    }
}
