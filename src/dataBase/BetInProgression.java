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
    
    public BetInProgression(int betId, String betName, String date, double odd, double stake,
            String bukmacher, String note, String type, int progressionId, String progressionName)
    {
        super(betId, betName, date, odd, stake, bukmacher, note, type);
        progression = new Progression(progressionId, progressionName);
    }
    
    public BetInProgression(int betId, String betName, String date, double odd, 
            double stake, String bukmacher, String note, String type, 
            int progressionId, String progressionName, int progressionStatus)
    {
        super(betId, betName, date, odd, stake, bukmacher, note, type);
        progression = new Progression(progressionId, progressionName, progressionStatus);
    }
    
    public BetInProgression(Bet bet, Progression progression)
    {
        super(bet.getBetId(), bet.getBetName(), bet.getDate(), bet.getOdd(), 
                bet.getStake(), bet.getBukmacher(), bet.getNote(), bet.getType());
        this.progression = progression;
    }
    
    public BetInProgression(Bet bet, int progressionId, String progressionName)
    {
        super(bet.getBetId(), bet.getBetName(), bet.getDate(), bet.getOdd(), 
                bet.getStake(), bet.getBukmacher(), bet.getNote(), bet.getType());
        progression.setProgressionId(progressionId);
        progression.setProgressionName(progressionName);
    }
    
    public BetInProgression(Bet bet, int progressionId, String progressionName, int progressionStatus)
    {
        super(bet.getBetId(), bet.getBetName(), bet.getDate(), bet.getOdd(),
                bet.getStake(), bet.getBukmacher(), bet.getNote(), bet.getType());
        progression.setProgressionId(progressionId);
        progression.setProgressionName(progressionName);
        progression.setProgressionStatus(progressionStatus);
    }
    
    @Override
    public String toString()
    {
        return betId + " " + betName + " | " + progression.getProgressionName() 
                + " " + progression.getProgressionId();
    }
}
