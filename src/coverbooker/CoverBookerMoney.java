/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coverbooker;

/**
 *
 * @author Marcin
 */
public class CoverBookerMoney {
    
    private double mainBetMoney;
    private double secondaryBetMoney;   
    
    public CoverBookerMoney()
    {
        mainBetMoney = 0.0;
        secondaryBetMoney = 0.0;
    }
        
    // TODO MOZNA POPRAWIC - KAZDE POJAWIA SIE 2 RAZY !!!
    public void calcuteMoneyRates(double homeCourse, double drawCourse, 
            double awayCourse, double moneyBudget, CoverTypeENUM coverType)
    {
        //main bet - HOME
        if(coverType.equals(CoverTypeENUM.Home_Draw))
            secondaryBetMoney = moneyBudget / drawCourse;
      
        if(coverType.equals(CoverTypeENUM.Home_Away))
            secondaryBetMoney = moneyBudget / awayCourse;
        
        //main bet - DRAW
        if(coverType.equals(CoverTypeENUM.Draw_Home))
            secondaryBetMoney = moneyBudget / homeCourse;
        
        if(coverType.equals(CoverTypeENUM.Draw_Away))
            secondaryBetMoney = moneyBudget / awayCourse;
        
        //main bet - AWAY
        if(coverType.equals(CoverTypeENUM.Away_Home))
            secondaryBetMoney = moneyBudget / homeCourse;
        
        if(coverType.equals(CoverTypeENUM.Away_Draw))
            secondaryBetMoney = moneyBudget / drawCourse;
           
        mainBetMoney = moneyBudget - secondaryBetMoney;
    }
    
    public double calculatePossibleWin(double homeCourse, double drawCourse,
            double awayCourse, double moneyOnBet, CoverTypeENUM selectedType)
    {
        double win = 0.0;
        
        if(selectedType.equals(CoverTypeENUM.Home_Away) ||
                selectedType.equals(CoverTypeENUM.Home_Draw))
            win = homeCourse * moneyOnBet;
        
        if(selectedType.equals(CoverTypeENUM.Draw_Away) ||
                selectedType.equals(CoverTypeENUM.Draw_Home))
            win = drawCourse * moneyOnBet;
        
        if(selectedType.equals(CoverTypeENUM.Away_Draw) ||
                selectedType.equals(CoverTypeENUM.Away_Home))
            win = awayCourse * moneyOnBet;
        
        return win;
    }
    
    public double calculateCleanWin(double possibleWin, double moneyBudget)
    {
        return possibleWin - moneyBudget;
    }
    
    public void setMainBetMoney(double mainBetMoney) {
        this.mainBetMoney = mainBetMoney;
    }

    public void setSecondaryBetMoney(double secondaryBetMoney) {
        this.secondaryBetMoney = secondaryBetMoney;
    }

    public double getMainBetMoney() {
        return mainBetMoney;
    }

    public double getSecondaryBetMoney() {
        return secondaryBetMoney;
    }
    
}
