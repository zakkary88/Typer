/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package progressivebooker;

import java.util.Random;


/**
 *
 * @author Marcin
 */
public class OddsGenerator {
      
    Random rand;
    
    public OddsGenerator()
    {
        rand = new Random();
    }
    
    // narazie tylko przykladowe losowanie kursu dla remisu
    // TODO ulepszyć losowanie kursu
    public double generateOdd()
    {
        int randomInt = rand.nextInt(6) + 1;    //od 1 do 7
        double odd = 0.0;
        
        if(randomInt == 5)
        {
            odd = (rand.nextDouble() + 5) * 100;
        }
        else
        {
            odd = (rand.nextDouble() + 3.2) * 100;
        }
        
        odd = odd + (5 - odd % 5);

        odd = Math.round(odd);
        odd /= 100;
        
        return odd;
    }   
   
}
