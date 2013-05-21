/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

/**
 *
 * @author Marcin
 */
public class Progression {
    
    private int progressionId;
    private String progressionName;
    private int progressionStatus;    // np. 1 - trwa / 2 - zakonczona
    
    public Progression(int progressionId, String progressionName)
    {
        this.progressionId = progressionId;
        this.progressionName = progressionName;
        this.progressionStatus = 1;
    }
    
    public Progression(int progressionId, String progressionName, int progressionStatus)
    {
        this.progressionId = progressionId;
        this.progressionName = progressionName;
        this.progressionStatus = progressionStatus;
    }
    
    @Override
    public String toString()
    {
        return progressionId + " " + progressionName + " " + progressionStatus;
    }
    
    public void endProgression()
    {
        progressionStatus = 2;
    }
    
    public int getProgressionStatus()
    {
        return progressionStatus;
    }

    public int getProgressionId()
    {
        return progressionId;
    }

    public void setProgressionId(int progressionId) 
    {
        this.progressionId = progressionId;
    }

    public String getProgressionName() 
    {
        return progressionName;
    }

    public void setProgressionName(String progressionName) 
    {        
        this.progressionName = progressionName;
    }
    
    public void setProgressionStatus(int progressionStatus)
    {
        this.progressionStatus = progressionStatus;
    }
}
