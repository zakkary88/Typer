package coverbooker;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marcin
 */
public class TwoWayEvent {
    
    private double homeCourse;
    private double awayCourse;
    
    public TwoWayEvent()
    {
        this.homeCourse = 1.0;
        this.awayCourse = 1.0;
    }
    
    public TwoWayEvent(double homeCourse, double awayCourse)
    {
        this.homeCourse = homeCourse;
        this.awayCourse = awayCourse;
    }
     
    public double getAwayCourse() {
        return awayCourse;
    }

    public double getHomeCourse() {
        return homeCourse;
    }
    
    public void setAwayCourse(double awayCourse) {
        this.awayCourse = awayCourse;
    }

    public void setHomeCourse(double homeCourse) {
        this.homeCourse = homeCourse;
    }
}
