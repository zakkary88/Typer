/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coverbooker;

import javax.swing.JTextField;

/**
 *
 * @author Marcin
 */
public class CoverBookerCalc {
    
    private ThreeWayEvent event;
    private PercentageChance pChance;
    private BestOptionSorter bestOptionSorter;
    private CoverBookerMoney coverBookerMoney;
    
    public CoverBookerCalc()
    {
        this.event = new ThreeWayEvent();
        this.pChance = new PercentageChance();
        this.bestOptionSorter = new BestOptionSorter();
        this.coverBookerMoney = new CoverBookerMoney();
    }        
    
    public CoverBookerCalc(JTextField homeCourse, JTextField drawCourse,
            JTextField awayCourse)
    {
        this.event = setEvent(homeCourse, drawCourse, awayCourse);
        this.pChance = new PercentageChance();
        this.bestOptionSorter = new BestOptionSorter();
        this.coverBookerMoney = new CoverBookerMoney();
    }
    
        
    private ThreeWayEvent setEvent(JTextField homeCourse, JTextField drawCourse,
            JTextField awayCourse)
    {
        String homeCourseString = homeCourse.getText();     
        double homeCourseDouble = Double.parseDouble(homeCourseString);
        String drawCourseString = drawCourse.getText();
        double drawCourseDouble = Double.parseDouble(drawCourseString);
        String awayCourseString = awayCourse.getText();
        double awayCourseDouble = Double.parseDouble(awayCourseString);
        
        return new ThreeWayEvent(homeCourseDouble, drawCourseDouble, awayCourseDouble);       
    }
    
    public void calculatePercentageChance()
    {
        double homePC = calculate(event.getHomeCourse());
        double drawPC = calculate(event.getDrawCourse());
        double awayPC = calculate(event.getAwayCourse());
        
        pChance.setHomePercentageChance(homePC);
        pChance.setDrawPercentageChance(drawPC);
        pChance.setAwayPercentageChance(awayPC);
    }
    
    private double calculate(double course)
    {
        return 1.0 / course * 0.9 * 100;
    }
           
    public CoverTypeENUM getBestType()
    {     
        getBestOptionSorter().sort(getpChance().getHomePercentageChance(),
                getpChance().getDrawPercentageChance(), 
                getpChance().getAwayPercentageChance());      
        
        CoverTypeENUM type = CoverTypeENUM.Away_Draw;
        
        if(bestOptionSorter.getFirstPlace() == pChance.getHomePercentageChance() && 
                bestOptionSorter.getSecondPlace() == pChance.getDrawPercentageChance())
        {
            type = CoverTypeENUM.Home_Draw;
        }
        
        if(bestOptionSorter.getFirstPlace() == pChance.getHomePercentageChance() &&
               bestOptionSorter.getSecondPlace() == pChance.getAwayPercentageChance())
        {
            type = CoverTypeENUM.Home_Away;
        }
        
        if(bestOptionSorter.getFirstPlace() == pChance.getDrawPercentageChance() &&
                bestOptionSorter.getSecondPlace() == pChance.getHomePercentageChance())
        {
            type = CoverTypeENUM.Draw_Home;
        }
        
        if(bestOptionSorter.getFirstPlace() == pChance.getDrawPercentageChance() &&
                bestOptionSorter.getSecondPlace() == pChance.getAwayPercentageChance())
        {
            type = CoverTypeENUM.Draw_Away;
        }
        
        if(bestOptionSorter.getFirstPlace() == pChance.getAwayPercentageChance() &&
                bestOptionSorter.getSecondPlace() == pChance.getDrawPercentageChance())
        {
            type = CoverTypeENUM.Away_Draw;          
        }
        
        if(bestOptionSorter.getFirstPlace() == pChance.getAwayPercentageChance() &&
                bestOptionSorter.getSecondPlace() == pChance.getHomePercentageChance())
        {
            type = CoverTypeENUM.Away_Home;
        }        
        
        return type;
    }        
               
    public ThreeWayEvent getEvent() {
        return event;
    }   
    
    public PercentageChance getpChance() {
        return pChance;
    }
    
    public BestOptionSorter getBestOptionSorter() {
        return bestOptionSorter;
    }
    
    public CoverBookerMoney getCoverBookerMoney() {
        return coverBookerMoney;
    }
}
