package edu.isu.cs2263.CS2263_Acquire_Project;

public class Corporation {
    int stockPrice;
    int availableStocks;
    String corpName;
    //List<Tiles> corpTiles;
    int corpSize;
    boolean status;

    public int getStockPrice(){
        return stockPrice;
    }

    public int getAvailableStocks() {
        return availableStocks;
    }

    public String getCorpName(){
        return corpName;
    }

//    public List<Tiles> getCorpTiles(){
//        return corpTiles;
//    }

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

    public void setCorpName(String s){
        corpName = s;
    }

//    public void setCorpTiles(List<Tile> t){
//        corpTiles = t;
//    }

    public void setCorpSize(int i) {
        corpSize = i;
    }

    public void setCorpStatus(boolean b){
        status = b;
    }



}
