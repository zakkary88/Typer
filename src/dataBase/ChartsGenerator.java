/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

import java.awt.Dimension;
import java.util.LinkedList;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartPanel;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYSeries;

/**
 *
 * @author Marcin
 */
public class ChartsGenerator {
    
    private int wonInProg = 0;
    private int wonNotInProg = 0;
    private int lostInProg = 0;
    private int lostNotInProg = 0;
    
    private LinkedList<String> yearsMonths = new LinkedList<String>();
    private LinkedList<Double> yieldMonth = new LinkedList<Double>();
    
    public ChartsGenerator()
    {
        wonInProg = DataContainer.listModelWonBetsInProg.size();
        wonNotInProg = DataContainer.listModelWonBetsNotInProg.size();
        
        lostInProg = DataContainer.listModelLostBetsInProg.size();
        lostNotInProg = DataContainer.listModelLostBetsNotInProg.size();
        
        loadDataFromDB();
    }
    
    private void loadDataFromDB()
    {
         DataContainer.dataFromDB.getQueryManager().viewYearsWithMoths(yearsMonths);
         double yield = 0.0;
         
         for(String yearMonth : yearsMonths)
         {
             yield = DataContainer.dataFromDB.getQueryManager().viewYield(yearMonth);
             yieldMonth.add(yield);
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
        TimeSeries series = new TimeSeries("Population", Day.class);
        
        for(int i=0; i<yearsMonths.size(); ++i)
            series.add(new Day(i, i, i), yieldMonth.get(i));
        
        JFreeChart chart = null;
        
        return chart;
    }

/*
 * series.add(1, 1);
series.add(1, 2);
series.add(2, 1);
series.add(3, 9);
series.add(4, 10);
// Add the series to your data set
XYSeriesCollection dataset = new XYSeriesCollection();
dataset.addSeries(series);
// Generate the graph
JFreeChart chart = ChartFactory.createXYLineChart(
"XY Chart", // Title
"x-axis", // x-axis Label
"y-axis", // y-axis Label
dataset, // Dataset
PlotOrientation.VERTICAL, // Plot Orientation
true, // Show Legend
true, // Use tooltips
false
* 
* /
 */

/*
 public class TarcieWykres extends JFrame{
    
    public TarcieWykres(String nazwa, LinkedList<DaneWykresu> dane, 
            LinkedList<DaneWykresu> daneVT, LinkedList<String> daneNawierzchnia)
    {
         JFreeChart wykres = StworzWykres(nazwa, dane, daneVT, daneNawierzchnia);
         
         ChartPanel wykresPanel = new ChartPanel(wykres,false);
         wykresPanel.setPreferredSize(new Dimension(500,500));

         setContentPane(wykresPanel);
         setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
    }

    public JFreeChart StworzWykres(String nazwa, LinkedList<DaneWykresu> dane,
            LinkedList<DaneWykresu> daneVT, LinkedList<String> daneNawierzchnia)
    {
        DefaultCategoryDataset wykres_dane = new DefaultCategoryDataset();
        
        for(int i=0; i<dane.size(); ++i)
        {         
            String opis_reakcja = "PrÄ™dkoĹ›Ä‡ poczÄ…tkowa: " + daneVT.get(i).dana1 + " m/s"
                    + " | " + "Czas reakcji: " + daneVT.get(i).dana2 + " s"
                    + " | " + "Stan nawierzchni: " + daneNawierzchnia.get(i);
            
            wykres_dane.setValue(dane.get(i).dana1, "bez reakcji", opis_reakcja);
            wykres_dane.setValue(dane.get(i).dana2, "z reakcja",  opis_reakcja);
        }
               
        JFreeChart wykres = ChartFactory.createBarChart3D(
                "Wplyw predkosci na droge hamowania",
                "Hamowanie bez uwzglÄ™dnienia reakcji i z uwzglÄ™dnieniem",
                "Droga - S[m]", 
                wykres_dane, 
                PlotOrientation.HORIZONTAL,
                true, 
                true, 
                false);
        
        return wykres;
    }  
}

 */

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

    public LinkedList<String> getYearsMonths() 
    {
        return yearsMonths;
    }

    public void setYearsMonths(LinkedList<String> yearsMonths) 
    {
        this.yearsMonths = yearsMonths;
    }
}
