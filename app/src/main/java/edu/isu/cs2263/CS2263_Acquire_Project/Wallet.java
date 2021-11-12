package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.HashMap;

public class Wallet {
    //instance variables
    int Cash;
    HashMap<String, Integer> Stocks;
    //contains corp name and quantity of stocks in that corp

    //Constructor method
    public Wallet(int the_cash, HashMap<String, Integer> the_stocks){
        Cash = the_cash;
        Stocks = the_stocks;
    }
    //other methods
    public int cashValue(){return Cash;}

    public int stockValue(){
        int stockVal = 0;
        System.out.println(Stocks.keySet());
        return stockVal;
    }

    private void trade(String corpName, int quant){
        //trade defunct stock for surviving corp if they are available
        //process repeats for other corps affected
    }
    private void sell(String corpName, int quant, int stockVal){
        int valueStock = Stocks.get(corpName);
        Cash += (quant * valueStock);
        System.out.println("$" + valueStock + " has been added to your wallet!");
    }

    private void hold(String corpName, int quant){
        //record stock to be held
        //repeat for other corporations affected

    }
    private void buy(String corpName, int quant){
        //up to 3 stock sold to player if chosen stock is available
    }
    private void getBonusStock(){

    }
}
