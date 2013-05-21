/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progressivebooker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Marcin
 */
public class ProgressiveBookerMoney {
    
    private List<Double> odds;
    private Random rand;
    private ProgressionType progressionType;
    
    private double currentBetAmount;
    private double lostMoney;
    private double wonMoney;
    
    public ProgressiveBookerMoney()
    {
        this.progressionType = new ProgressionType();
        this.odds = new ArrayList<Double>();
        this.rand = new Random();
        
        currentBetAmount = 0.0;
        lostMoney = 0.0;
        wonMoney = 0.0;
    }
    
    // Zaokrągla liczbę do 2 miejsc po przecinku.
    // Zapobiega różnicom w wyniku, w przypadku zaokrąglenia jedynie końcowego wyniku.   
    private double formatNumber(double number)
    {
        double formatedNumber = number * 100;
        Math.round(formatedNumber);
        
        // WYJASNIĆ !!!
        formatedNumber = formatedNumber  - formatedNumber % 1;
        formatedNumber /= 100;
        return formatedNumber;
        
    }
    
    // mozliwosc ustawienia czestotliwosc remisow dla ligi od 20 do 35%
    // funkcja powinnzwracac VOID - narazie test
    // sprawdzic, czy lista nie jest pusta!!!!
    
    
    /*
     * drawFrequency potrzebne jest zawsze
     * startBetAmount dla steadyProfit mozna zamienic na moneyToWin, co
     * eliminuje niepotrzbne argumenty funkcji
     */
    public String simulate(int drawFrequency, double toWinORstartBet, ProgressionTypeENUM progType)
    {
        String result = "";
        
        if(progType.equals(ProgressionTypeENUM.Doubling_Up))
            result = simulateDoublingUp(drawFrequency, toWinORstartBet);
        
        if(progType.equals(ProgressionTypeENUM.Steady_Profit))
            result = simulateSteadyProfit(drawFrequency, toWinORstartBet);
        
        if(progType.equals(ProgressionTypeENUM.Fibonacci))
            result = simulateFibonnaci(drawFrequency, toWinORstartBet);
        
        return result;
    }
    
    // dopracowac!!!  prawdopodobnie błąd formatowania
    private String simulateSteadyProfit(int drawFrequency, double moneyToWin)
    {
        int randomInt = 0;      
        String info = "";
        currentBetAmount = progressionType.steadyProfitCurrentBetAmount(moneyToWin, 0.0, odds.get(0));
        currentBetAmount = formatNumber(currentBetAmount);

        for(int i=0; i<odds.size(); ++i)
        {
            randomInt = rand.nextInt(99) + 1;   //od 1 do 100            
     
            if(randomInt < drawFrequency)
            {               //mozna w nawias - (odds.get(i) - 1)
                wonMoney = currentBetAmount * odds.get(i) - lostMoney - currentBetAmount;
                wonMoney = formatNumber(wonMoney);
                lostMoney = 0.0;
                
                info += (i+1) + ": " + randomInt + " - kurs: " 
                        + odds.get(i).toString() + " wygrana:  " + wonMoney + 
                        " || stawka: " + currentBetAmount + "\n";               
                
                currentBetAmount = moneyToWin;
                wonMoney = 0.0;
            }
            else
            {
                lostMoney += currentBetAmount;
                lostMoney = formatNumber(lostMoney);

                info += (i+1) + ": " + randomInt + " - kurs: " 
                        + odds.get(i).toString() + " przegrana:  " + lostMoney + 
                        " || stawka: " + currentBetAmount + "\n";
                
                currentBetAmount = progressionType.steadyProfitCurrentBetAmount(moneyToWin, lostMoney, odds.get(i));                         
                currentBetAmount = formatNumber(currentBetAmount);
            }
            
            if(i == odds.size()-1)
            {
                currentBetAmount = 0.0;
                wonMoney = 0.0;
                lostMoney = 0.0;
            }
        }
        
        return info;
    }
    
    private String simulateDoublingUp(int drawFrequency, double startBetAmount)
    {
        int randomInt = 0;      
        String info = "";
        currentBetAmount = startBetAmount;
                      
        for(int i=0; i<odds.size(); ++i)
        {
            randomInt = rand.nextInt(99) + 1;   //od 1 do 100            
     
            if(randomInt < drawFrequency)
            {               //mozna w nawias - (odds.get(i) - 1)
                wonMoney = currentBetAmount * odds.get(i) - lostMoney - currentBetAmount;
                wonMoney = formatNumber(wonMoney);
                lostMoney = 0.0;
                
                info += (i+1) + ": " + randomInt + " - kurs: " 
                        + odds.get(i).toString() + " wygrana:  " + wonMoney + 
                        " || stawka: " + currentBetAmount + "\n";
                
                currentBetAmount = startBetAmount;
                wonMoney = 0.0;
            }
            else
            {
                lostMoney += currentBetAmount;
              
                info += (i+1) + ": " + randomInt + " - kurs: " 
                        + odds.get(i).toString() + " przegrana:  " + lostMoney + 
                        " || stawka: " + currentBetAmount + "\n";
                
                currentBetAmount = progressionType.doublingUpCurrentBetAmount(currentBetAmount);
                currentBetAmount = formatNumber(currentBetAmount);
            }
            
            if(i == odds.size()-1)
            {
                currentBetAmount = 0.0;
                wonMoney = 0.0;
                lostMoney = 0.0;               
            }
        }
        
        return info;
    }
    
    public String simulateFibonnaci(int drawFrequency, double startBetAmount)
    {
        int randomInt = 0;      
        String info = "";   
  
        currentBetAmount = progressionType.fibonnaciCurrentBetAmount(startBetAmount, 0);  
        int progressionLevel = 0;
        
        for(int i=0; i<odds.size(); ++i)
        {
            randomInt = rand.nextInt(99) + 1;   //od 1 do 100            
     
            if(randomInt < drawFrequency)
            {               
                //nie wiadomo, czy potrzebne
               // currentBetAmount = progressionType.fibonnaciCurrentBetAmount(startBetAmount, 0);
                wonMoney = currentBetAmount * odds.get(i) - lostMoney - currentBetAmount;
                wonMoney = formatNumber(wonMoney);
                lostMoney = 0.0;
                
                info += (i+1) + ": " + randomInt + " - kurs: " 
                        + odds.get(i).toString() + " wygrana:  " + wonMoney + 
                        " || stawka: " + currentBetAmount + "\n";
                
                progressionType.getMoneyRates().clear();
                
                currentBetAmount = progressionType.fibonnaciCurrentBetAmount(startBetAmount, 0);  
                wonMoney = 0.0;
                progressionLevel = 0;
            }
            else
            {       
                progressionLevel++;
                lostMoney += currentBetAmount;
                lostMoney = formatNumber(lostMoney);               
                
                info += (i+1) + ": " + randomInt + " - kurs: " 
                        + odds.get(i).toString() + " przegrana:  " + lostMoney + 
                        " || stawka: " + currentBetAmount + "\n";
                                         
                
                currentBetAmount = progressionType.fibonnaciCurrentBetAmount(startBetAmount, progressionLevel);
                // problemem jest to, ze dodaje do listy 2 razy startBetAmount (w sumie sa 3, zamiast 2)
                currentBetAmount = formatNumber(currentBetAmount);               
            }
            
            if(i == odds.size()-1)
            {
                currentBetAmount = 0.0;
                wonMoney = 0.0;
                lostMoney = 0.0;
                
                progressionType.getMoneyRates().clear();
            }
        }
        
        return info;
    }
    
        
    public List<Double> getOdds() 
    {
        return odds;
    }
}
