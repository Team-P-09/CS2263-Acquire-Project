package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter @Setter
public class Wallet {
    //instance variables
    int cash;
    HashMap<String, Integer> stocks;
    //contains corp name and quantity of stocks in that corp

    //Constructor method
    public Wallet(ArrayList<String> corpNames){
        cash = 6000;
        stocks = initWallet(corpNames);
    }

    public HashMap<String, Integer> initWallet(ArrayList<String> corpNames){
        HashMap<String, Integer> newWallet = new HashMap<>();
        for(String cName : corpNames){
            newWallet.put(cName, 0);
        }
        return newWallet;
    }

    public void addCash(Integer value){
        setCash(getCash() + value);
    }

    public void removeCash(Integer value){
        setCash(getCash() - value);
    }

    public void addStock(String cName, Integer qty){
        Integer newQty = getStocks().get(cName) + qty;
        getStocks().put(cName, newQty);
    }

    public void removeStock(String cName, Integer qty){
        Integer newQty = getStocks().get(cName) - qty;
        getStocks().put(cName, newQty);
    }

//    private void trade(String corpName, int quant){
//        //trade defunct stock for surviving corp if they are available
//        //process repeats for other corps affected
//    }
//    private void sell(String corpName, int quant, int stockVal){
//        int valueStock = stocks.get(corpName);
//        cash += (quant * valueStock);
//        System.out.println("$" + valueStock + " has been added to your wallet!");
//    }

//    private void hold(String corpName, int quant){
//        //record stock to be held
//        //repeat for other corporations affected
//
//    }
//    private void buy(String corpName, int quant){
//        //up to 3 stock sold to player if chosen stock is available
//    }
//    private void getBonusStock(){
//
//    }



}
