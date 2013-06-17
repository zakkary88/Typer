/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.awt.Dimension;
import java.util.LinkedList;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;


/**
 *
 * @author Marcin
 */
public class ChartsGenerator {
    
    private int wonInProg = 0;
    private int wonNotInProg = 0;
    private int lostInProg = 0;
    private int lostNotInProg = 0;
    
    private LinkedList<String> dates = new LinkedList<String>();
    private LinkedList<Double> yield = new LinkedList<Double>();
    private LinkedList<Day> days = new LinkedList<Day>();
    
    public ChartsGenerator()
    {
        wonInProg = DataContainer.listModelWonBetsInProg.size();
        wonNotInProg = DataContainer.listModelWonBetsNotInProg.size();
        
        lostInProg = DataContainer.listModelLostBetsInProg.size();
        lostNotInProg = DataContainer.listModelLostBetsNotInProg.size();
        
        loadDataFromDB();
        fillDays();      
    }
    
    private Day getDate(String dateFromBD)
    {
        String monthString = "";
        String yearString = "";
        String dayString = "";
        
        String [] split = dateFromBD.split("-");
        String [] split2 = split[2].split(" ");
        
        yearString = split[0];
        monthString = split[1];     
        dayString = split2[0];
        
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);
        int day = Integer.parseInt(dayString);       
        Day date = new Day(day, month, year);
        
        //System.out.println(day + " " + month + " " + yearString);
        return date;                
    }
    
    private void fillDays()
    {
        for(String date : dates)
            days.add(getDate(date));        
    }
    
    private void loadDataFromDB()
    {
         DataContainer.dataFromDB.getQueryManager().viewDates(getDates());
         double yieldValue = 0.0;
         
         for(String date : getDates())
         {
             yieldValue = DataContainer.dataFromDB.getQueryManager().viewYield(date);
             yield.add(yieldValue);
         }
    }
       
    public ChartPanel drawEfficiencyChart()
    {
         JFreeChart chart = setEfficiencyChart();
         ChartPanel cp = new ChartPanel(chart, false);
         cp.setPreferredSize(new Dimension(300, 300));

         return cp;         
    }
    
    public ChartPanel drawEfficiencyInProgressionChart()
    {
         JFreeChart chart = setEfficiencyInProgressionsChart();
         ChartPanel cp = new ChartPanel(chart, false);
         cp.setPreferredSize(new Dimension(300, 300));

         return cp;         
    }
    
    public ChartPanel drawEfficiencyNotInProgChart()
    {
         JFreeChart chart = setEfficiencyNotInProgChart();
         ChartPanel cp = new ChartPanel(chart, false);
         cp.setPreferredSize(new Dimension(300, 300));

         return cp;         
    }
    
    public ChartPanel drawYieldByDatesChart()
    {
         JFreeChart chart = setYieldTimeSeries();
         ChartPanel cp = new ChartPanel(chart, false);
         cp.setPreferredSize(new Dimension(300, 300));

         return cp;         
    }
    
    private JFreeChart setEfficiencyChart()
    {           
        int won = getWonInProg() + getWonNotInProg();
        int lost = getLostInProg() + getLostNotInProg();
        
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Won " + won , won);
        pieDataset.setValue("Lost " + lost, lost);
        
        JFreeChart chart = ChartFactory.createPieChart3D
        ("Efficiency", // Title
        pieDataset, // Dataset
        true, // Show legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
        );
        
        return chart;
    }
    
    private JFreeChart setEfficiencyInProgressionsChart()
    {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Won " + getWonInProg() , getWonInProg());
        pieDataset.setValue("Lost " + getLostInProg(), getLostInProg());
        
        JFreeChart chart = ChartFactory.createPieChart3D
        ("Efficiency in progressions", // Title
        pieDataset, // Dataset
        true, // Show legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
        );
        
        return chart;
    }
    
    private JFreeChart setEfficiencyNotInProgChart()
    {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Won " + getWonNotInProg() , getWonNotInProg());
        pieDataset.setValue("Lost " + getLostNotInProg(), getLostNotInProg());
        
        JFreeChart chart = ChartFactory.createPieChart3D
        ("Efficiency not in progressions", // Title
        pieDataset, // Dataset
        true, // Show legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
        );
        
        return chart;
    }
    
    private JFreeChart setYieldTimeSeries()
    {
        TimeSeries series = new TimeSeries("Yield", Day.class);
        TimeSeries yieldFinal = new TimeSeries("Yield final", Day.class);
        double yieldFinalValue = DataContainer.dataFromDB.getQueryManager().viewFinalYield();
        
        for(int i=0; i<days.size(); ++i)
        {
            series.add(days.get(i), getYield().get(i));    
            yieldFinal.add(days.get(i), yieldFinalValue);
        }
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(yieldFinal);
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Yield by date",
            "Date",
            "Yield in %",
            dataset,
            true,
            true,
            false);

        return chart;
    }
    
    public int getWonInProg() 
    {
        return wonInProg;
    }

    public void setWonInProg(int wonInProg) 
    {
        this.wonInProg = wonInProg;
    }

    public int getWonNotInProg() 
    {
        return wonNotInProg;
    }

    public void setWonNotInProg(int wonNotInProg) 
    {
        this.wonNotInProg = wonNotInProg;
    }

    public int getLostInProg() 
    {
        return lostInProg;
    }

    public void setLostInProg(int lostInProg) 
    {
        this.lostInProg = lostInProg;
    }

    public int getLostNotInProg() 
    {
        return lostNotInProg;
    }

    public void setLostNotInProg(int lostNotInProg) 
    {
        this.lostNotInProg = lostNotInProg;
    }

    public LinkedList<String> getDates() 
    {
        return dates;
    }
    
    public void setDates(LinkedList<String> dates) 
    {
        this.dates = dates;
    }

    public LinkedList<Double> getYield() 
    {
        return yield;
    }

    public void setYield(LinkedList<Double> yield) 
    {
        this.yield = yield;
    }

    public LinkedList<Day> getDays() 
    {
        return days;
    }

    public void setDays(LinkedList<Day> days) 
    {
        this.days = days;
    }
}
