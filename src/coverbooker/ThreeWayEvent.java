/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package coverbooker;

/**
 *
 * @author Marcin
 */
public class ThreeWayEvent extends TwoWayEvent{
    
    private double drawCourse;
       
    public ThreeWayEvent()
    {
        super();
        this.drawCourse = 1.0;
    }
    
    public ThreeWayEvent(double homeCourse, double drawCourse, double awayCourse)
    {
        super(homeCourse, awayCourse);
        this.drawCourse = drawCourse;
    }
    
    public ThreeWayEvent(TwoWayEvent twe ,double drawCourse)
    {
        super(twe.getHomeCourse(), twe.getAwayCourse());
        this.drawCourse = drawCourse;
    }

    public double getDrawCourse() {
        return drawCourse;
    }

    public void setDrawCourse(double drawCourse) {
        this.drawCourse = drawCourse;
    }

}
