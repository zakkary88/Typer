package progressivebooker;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcin
 */
public class ProgressionType {
    
    /*
    private ProgressionTypeENUM progressionType;
    private double startBetAmount;
    private double lostMoney;
     * 
     */
    private List<Double> moneyRates;
    
    public ProgressionType()
    {
        moneyRates = new ArrayList<Double>();
    }
  
    
    //Argumenty: aktualna stawka
    //Zwraca aktualną stawkę przemożona razy 2.
    public double doublingUpCurrentBetAmount(double currentBetAmount)            
    {
        return currentBetAmount * 2;
    }
    
    //Agrumenty: założony zysk, przegrane w progresji pięniądze, kurs na zdarzenie.
    //Zwraca stawkę na podstawie założonego zysku.
    public double steadyProfitCurrentBetAmount(double moneyToWin, double lostMoney, double odd)
    {
        return (moneyToWin + lostMoney) / (odd-1.0);
    }
    
    //Argumenty: lista kursów, aktualny poziom progresji.
    //Zwraca stawkę wyznaczoną na podstawie ciągu Fibonacci'ego.
    public double fibonnaciCurrentBetAmount(double startBetAmount, int progressionLevel)
    {
        double currentBetAmount = 0.0;
        
        if(progressionLevel == 0 || progressionLevel == 1)
        {
            currentBetAmount = startBetAmount;
        }
        else
        {       
            double lastBetAmount = moneyRates.get(progressionLevel-1);
            double oneBeforeLastBetAmount = moneyRates.get(progressionLevel-2);
            currentBetAmount = lastBetAmount + oneBeforeLastBetAmount;         
        }
        
        moneyRates.add(currentBetAmount);
        
        /*
        switch(progressionLevel)
        {
            case 0:
                currentBetAmount = startBetAmount;
                break;
            case 1:
                currentBetAmount = startBetAmount;
                moneyRates.add(currentBetAmount);
                break;
            default:
                double lastBetAmount = moneyRates.get(progressionLevel);
                double oneBeforeLastBetAmount = moneyRates.get(progressionLevel-1);
                currentBetAmount = lastBetAmount + oneBeforeLastBetAmount;
                moneyRates.add(currentBetAmount);
                break;
        }
        */
        
        return currentBetAmount;
    }
    
    public List<Double> getMoneyRates() 
    {
        return moneyRates;
    }
}
