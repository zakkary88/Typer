/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progressivebooker;


/**
 *
 * @author Marcin
 */
public class ProgressiveSimulator {
    
    private OddsGenerator og;
    private ProgressiveBookerMoney pbm;
    
    public ProgressiveSimulator()
    {
        og = new OddsGenerator();
        pbm = new ProgressiveBookerMoney();
    }
    
    public void generateOddsList(int numberOfOdds)
    {
        pbm.getOdds().clear();
        for(int i=0; i<numberOfOdds; ++i)
        {
            double odd = og.generateOdd();      
            pbm.getOdds().add(odd);
        }
    }
    
    public String viewOdds()
    {
        String oddsString = "";
        
        for(int i=0; i<pbm.getOdds().size(); ++i)
        {
            oddsString += (i+1) + ": " + pbm.getOdds().get(i).toString() + "\n";
        }
        
        return oddsString;
    }
    
    public ProgressiveBookerMoney getPbm() 
    {
        return pbm;
    }
}
