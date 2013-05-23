/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dataBase;

/**
 *
 * @author Marcin
 * 
 * Obiekt klasy jest tworzony dla nowego zakładu, który nie jest częścią
 * progresji (opcja part of progression jest odznaczona)
 */
public class Bet {
    
    protected int betId;
    protected String betName;
    protected String date;
    protected double odd;
    protected double stake;
    protected int betStatus;     // /-1 blad / 1 nierozstrzygniety / 2 wygrany / 3 przegrany / 4 odwołany
    protected String bukmacher;
    protected String note;
    protected String type;

    protected double balance;
    
    //int partOfProgression - istnieje w bazaie danych / 0 - nie jest czescia progresji / inna licza - progressionId     
    //w tabeli znajduje sie jako klucz obcy laczacy zaklad z progresja
    
    // ROZWAZYC DODANIE PART OF PROGRESSION jako pola !!!!!!!!!!!!
   
    
    public Bet(int betId, String betName, String date, double odd, double stake, 
            String bukmacher, String note, String type)
    {
        this.betId = betId;
        this.betName = betName;
        this.date = date;
        this.odd = odd;
        this.stake = stake;
        this.betStatus = 1;     //jakos nierozstrzygniety
        this.bukmacher = bukmacher;
        this.note = note;
        this.type = type;
        this.balance = 0;
    }
    
    public Bet(int betId, String betName, String date, double odd, double stake, 
            int betStatus, String bukmacher, String note, double balance, String type)
    {
        this.betId = betId;
        this.betName = betName;
        this.date = date;
        this.odd = odd;
        this.stake = stake;
        this.betStatus = betStatus;     
        this.bukmacher = bukmacher;
        this.note = note;
        this.balance = balance;
        this.type = type;
    }
    
    @Override
    public String toString()
    {
        return betId + " " + betName;
    }
    
    public void setLostBet()
    {
        betStatus= 2;
    }
    
    public void setWonBet()
    {
        betStatus = 1;
    }

    public int getBetId() 
    {
        return betId;
    }

    public String getBetName() 
    {
        return betName;
    }

    public String getDate() 
    {
        return date;
    }

    public double getOdd() 
    {
        return odd;
    }

    public double getStake() 
    {
        return stake;
    }

    public int getBetStatus() 
    {
        return betStatus;
    }
    
    public double getBalance() 
    {
        return balance;
    }

    public void setWon(double balance) 
    {
        this.balance = balance;
    }

    public String getNote() 
    {
        return note;
    }

    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getBukmacher() 
    {
        return bukmacher;
    }

    public void setBukmacher(String bukmacher) 
    {
        this.bukmacher = bukmacher;
    }
    
    public String getType() 
    {
        return type;
    }

    public void setType(String type) 
    {
        this.type = type;
    }
}
