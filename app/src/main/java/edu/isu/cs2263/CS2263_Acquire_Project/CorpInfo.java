package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.ArrayList;
import java.util.List;

public class CorpInfo {
    int stockPrice;
    int availableStocks;
    ArrayList<Tile> corpTiles;
    int corpSize;
    boolean status;

    public CorpInfo(int stPrice, int avStocks, ArrayList<Tile> coTiles, int coSize, boolean stat){
        stockPrice = stPrice;
        availableStocks = avStocks;
        corpTiles = coTiles;
        corpSize = coSize;
        status = stat;
    }

    public int getStockPrice(){
        return stockPrice;
    }

    public int getAvailableStocks() {
        return availableStocks;
    }

    public int getCorpSize(){
        return corpSize;
    }

    public boolean getCorpStatus(){
        return status;
    }

    public void setStockPrice(int i){
        stockPrice = i;
    }

    public void setAvailableStocks(int i){
        availableStocks = i;
    }

    public void setCorpTiles(ArrayList<Tile> t){
        corpTiles = t;
    }

    public void setCorpSize(int i) {
        corpSize = i;
    }

    public void setCorpStatus(boolean b){
        status = b;
    }



}
