/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coverbooker;

/**
 *
 * @author Marcin
 */
public class PercentageChance {
  
    private double homePercentageChance;
    private double drawPercentageChance;
    private double awayPercentageChance;
    
    public PercentageChance()
    {
        this.homePercentageChance = 33.3;
        this.drawPercentageChance = 33.3;
        this.awayPercentageChance = 33.3;
    }
    
    public PercentageChance(double homePercentageChance, 
            double drawPercentageChance, double awayPercentageChance)
    {
        this.homePercentageChance = homePercentageChance;
        this.drawPercentageChance = drawPercentageChance;
        this.awayPercentageChance = awayPercentageChance;
    }
    
    public double getHomeAndDrawPChance()
    {
        return homePercentageChance + drawPercentageChance;
    }
    
    public double getHomeAndAwayPChance()
    {
        return homePercentageChance + awayPercentageChance;
    }
    
    public double getDrawAndAwayPChance()
    {
        return drawPercentageChance + awayPercentageChance;
    }
    
    public double getAwayPercentageChance() {
        return awayPercentageChance;
    }

    public double getDrawPercentageChance() {
        return drawPercentageChance;
    }

    public double getHomePercentageChance() {
        return homePercentageChance;
    }

    public void setAwayPercentageChance(double awayPercentageChance) {
        this.awayPercentageChance = awayPercentageChance;
    }

    public void setDrawPercentageChance(double drawPercentageChance) {
        this.drawPercentageChance = drawPercentageChance;
    }

    public void setHomePercentageChance(double homePercentageChance) {
        this.homePercentageChance = homePercentageChance;
    }
}
