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
    private LinkedList<Day> days = new LinkedList<Day>();
    
    private LinkedList<Double> wonBalance = new LinkedList<Double>();
    private LinkedList<Double> stakesSum = new LinkedList<Double>();
    private LinkedList<Double> yield = new LinkedList<Double>();
    
    private LinkedList<Double> wonBalanceInProg = new LinkedList<Double>();
    private LinkedList<Double> stakeSumInProg = new LinkedList<Double>();
    private LinkedList<Double> yieldInProg = new LinkedList<Double>();
    
    private LinkedList<Double> wonBalanceNotInProg = new LinkedList<Double>();
    private LinkedList<Double> stakeSumNotInProg = new LinkedList<Double>();
    private LinkedList<Double> yieldNotInProg = new LinkedList<Double>();
    
    public ChartsGenerator()
    {
        wonInProg = DataContainer.listModelWonBetsInProg.size();
        wonNotInProg = DataContainer.listModelWonBetsNotInProg.size();
        
        lostInProg = DataContainer.listModelLostBetsInProg.size();
        lostNotInProg = DataContainer.listModelLostBetsNotInProg.size();
        
        loadDataFromDB();
        fillDays();
        fillListYield();
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
         
         for(String date : getDates())
         {
             //wszystkie zaklady
             double stakesSumByDate = DataContainer.dataFromDB.getQueryManager().viewAllStakesByDate(date);
             double wonBalanceByDate = DataContainer.dataFromDB.getQueryManager().viewWonBalanceByDate(date);
             
             getStakesSum().add(stakesSumByDate);
             getWonBalance().add(wonBalanceByDate);
             
             //zaklady w progresjach
             double stakesSumByDateInProg = DataContainer.dataFromDB.getQueryManager().
                     viewAllStakesByDateInProg(date);
             double wonBalanceByDateInProg = DataContainer.dataFromDB.getQueryManager().
                     viewWonBalanceByDateInProg(date);
             
             getStakeSumInProg().add(stakesSumByDateInProg);
             getWonBalanceInProg().add(wonBalanceByDateInProg);
             
             //zaklady nie w progresjach
             double stakesSumByDateNotInProg = DataContainer.dataFromDB.getQueryManager().
                     viewAllStakesByDateNotInProg(date);
             double wonBalanceByDateNotInProg = DataContainer.dataFromDB.getQueryManager().
                     viewWonBalanceByDateNotInProg(date);
             
             stakeSumNotInProg.add(stakesSumByDateNotInProg);
             wonBalanceNotInProg.add(wonBalanceByDateNotInProg);           
         }
    }
    
    private void fillListYield()
    {
        double balance = 0;
        double stake = 0;     
        double balanceInProg = 0;
        double stakeInProg = 0;
        double balanceNotInProg = 0;
        double stakeNotInProg = 0;      
        
        for(int i=0; i<days.size(); ++i)
        {           
            balance += wonBalance.get(i);
            stake += stakesSum.get(i);
            
            balanceInProg += wonBalanceInProg.get(i);
            stakeInProg += stakeSumInProg.get(i);
            
            balanceNotInProg += wonBalanceNotInProg.get(i);
            stakeNotInProg += stakeSumNotInProg.get(i);
            
            //wszystkie zaklady
            double yieldChange = ((balance - stake) / stake) * 100;
            getYield().add(yieldChange);
            
            //zaklady w progresjach
            double yieldChangeInProg = ((balanceInProg - stakeInProg) / 
                    stakeInProg) * 100;
            yieldInProg.add(yieldChangeInProg);
            
            //zaklady nie w progresjach
            double yieldChangeNotInProg = ((balanceNotInProg - stakeNotInProg)
                    / stakeNotInProg) * 100;
            yieldNotInProg.add(yieldChangeNotInProg);
        }
    }
    
    private double countFinalYield()
    {
        double stake = 0;
        double balance = 0;
        
        for(int i=0; i<days.size(); i++)
        {
            stake += stakesSum.get(i);
            balance += wonBalance.get(i);
        }
        
        return ((balance - stake) / stake) * 100;
    }
    
    private double countFinalYieldNotInProg()
    {
        double stake = 0;
        double balance = 0;
        
        for(int i=0; i<days.size(); i++)
        {
            stake += stakeSumNotInProg.get(i);
            balance += wonBalanceNotInProg.get(i);
        }
        
        return ((balance - stake) / stake) * 100;
    }
    
    private double countFinalYieldInProg()
    {
        double stake = 0;
        double balance = 0;
        
        for(int i=0; i<days.size(); i++)
        {
            stake += stakeSumInProg.get(i);
            balance += wonBalanceInProg.get(i);
        }
        
        return ((balance - stake) / stake) * 100;
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
    
    public ChartPanel drawYieldByDatesInProgChart()
    {
         JFreeChart chart = setYieldInProgTimeSeries();
         ChartPanel cp = new ChartPanel(chart, false);
         cp.setPreferredSize(new Dimension(300, 300));

         return cp;
    }
    
    public ChartPanel drawYieldByDatesNotInProgChart()
    {
         JFreeChart chart = setYieldNotInProgTimeSeries();
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
        
        double yieldFinalValue = countFinalYield();
        
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
    
    private JFreeChart setYieldInProgTimeSeries()
    {
        TimeSeries series = new TimeSeries("Yield", Day.class);
        TimeSeries yieldFinal = new TimeSeries("Yield final", Day.class);
        
        double yieldFinalValue = countFinalYieldInProg();
        
        for(int i=0; i<days.size(); ++i)
        {
            series.add(days.get(i), getYieldInProg().get(i));
            yieldFinal.add(days.get(i), yieldFinalValue);
        }
      
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(yieldFinal);
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Yield in progressions by date",
            "Date",
            "Yield in %",
            dataset,
            true,
            true,
            false);

        return chart;
    }
    
    private JFreeChart setYieldNotInProgTimeSeries()
    {
        TimeSeries series = new TimeSeries("Yield", Day.class);
        TimeSeries yieldFinal = new TimeSeries("Yield final", Day.class);
        
        double yieldFinalValue = countFinalYieldNotInProg();
        
        for(int i=0; i<days.size(); ++i)
        {
            series.add(days.get(i), yieldNotInProg.get(i));
            yieldFinal.add(days.get(i), yieldFinalValue);
        }
      
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        dataset.addSeries(yieldFinal);
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Yield in progressions by date",
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
    
    public LinkedList<Day> getDays() 
    {
        return days;
    }

    public void setDays(LinkedList<Day> days) 
    {
        this.days = days;
    }

    public LinkedList<Double> getWonBalance() 
    {
        return wonBalance;
    }

    public void setWonBalance(LinkedList<Double> wonBalance) 
    {
        this.wonBalance = wonBalance;
    }

    public LinkedList<Double> getStakesSum() 
    {
        return stakesSum;
    }

    public void setStakesSum(LinkedList<Double> stakesSum) 
    {
        this.stakesSum = stakesSum;
    }

    public LinkedList<Double> getYield() 
    {
        return yield;
    }

    public void setYield(LinkedList<Double> yield) 
    {
        this.yield = yield;
    }

    public LinkedList<Double> getWonBalanceInProg() 
    {
        return wonBalanceInProg;
    }

    public void setWonBalanceInProg(LinkedList<Double> wonBalanceInProg) 
    {
        this.wonBalanceInProg = wonBalanceInProg;
    }

    public LinkedList<Double> getStakeSumInProg() 
    {
        return stakeSumInProg;
    }

    public void setStakeSumInProg(LinkedList<Double> stakeSumInProg) 
    {
        this.stakeSumInProg = stakeSumInProg;
    }

    public LinkedList<Double> getYieldInProg() 
    {
        return yieldInProg;
    }

    public void setYieldInProg(LinkedList<Double> yieldInProg) 
    {
        this.yieldInProg = yieldInProg;
    }
    
    public LinkedList<Double> getWonBalanceNotInProg() 
    {
        return wonBalanceNotInProg;
    }

    public void setWonBalanceNotInProg(LinkedList<Double> wonBalanceNotInProg) 
    {
        this.wonBalanceNotInProg = wonBalanceNotInProg;
    }

    public LinkedList<Double> getStakeSumNotInProg() 
    {
        return stakeSumNotInProg;
    }

    public void setStakeSumNotInProg(LinkedList<Double> stakeSumNotInProg) 
    {
        this.stakeSumNotInProg = stakeSumNotInProg;
    }

    public LinkedList<Double> getYieldNotInProg() 
    {
        return yieldNotInProg;
    }

    public void setYieldNotInProg(LinkedList<Double> yieldNotInProg) 
    {
        this.yieldNotInProg = yieldNotInProg;
    }
}
