/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coverbooker;

/**
 *
 * @author Marcin
 */
public class BestOptionSorter {
    
    private double firstPlace; 
    private double secondPlace;
    
    public BestOptionSorter()
    {
        firstPlace = 0.0;
        secondPlace = 0.0;
    }
    
    /*
     * Konstruktor potrzeby tylko, gdy funkcja sort jest prywatna!
    public BestOptionSorter(double homePChance, double drawPChance, double awayPChance)
    {
        sort(drawPChance, drawPChance, awayPChance);
    }
     */
    
    public void sort(double homePChance, double drawPChance, double awayPChance)
    {
        if(homePChance > drawPChance)
        {
            if(homePChance > awayPChance)
            {
                firstPlace = homePChance;
                
                if(drawPChance > awayPChance)
                    secondPlace = drawPChance;
                else
                    secondPlace = awayPChance;
            }
            else
            {
                firstPlace = awayPChance;
                secondPlace = homePChance;
            }
        }
        else
        {
            if(drawPChance > awayPChance)
            {
                firstPlace = drawPChance;
                
                if(homePChance > awayPChance)
                    secondPlace = homePChance;
                else
                    secondPlace = awayPChance;
            }
            else
            {
                firstPlace = awayPChance;
                secondPlace = drawPChance;
            }      
        }
    }
    
    public double getBestOption()
    {
        return firstPlace + secondPlace;
    }
    
    public double getFirstPlace() {
        return firstPlace;
    }

    public double getSecondPlace() {
        return secondPlace;
    }
}
