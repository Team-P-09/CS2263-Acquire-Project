package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter @Setter
public class CorpInfo {
    private int stockPrice;
    private int availableStocks;
    //ArrayList<Tile> corpTiles;
    private HashMap<String, Tile> corpTiles; //HashMap gives us a faster implementation for merging corporations
    private boolean status;
    private boolean isSafe;

    public CorpInfo(){ //int stPrice, int avStocks, ArrayList<Tile> coTiles, int coSize, boolean stat
        stockPrice = 100;
        availableStocks = 25;
        corpTiles = new HashMap<>();//new ArrayList<>();
        status = false;
        isSafe = false;
    }

    /**
     * Retrieves the corpTiles and empties the corpTiles
     * @return an ArrayList of the Tile class
     */
    public HashMap<String, Tile> retrieveTiles(){
        //ArrayList<Tile> outTiles = this.corpTiles;
        HashMap<String, Tile> outTiles = getCorpTiles();
        setCorpTiles(new HashMap<>());//ArrayList<Tile>();
        return outTiles;
    }

    public void checkIfSafe(){
        if(getCorpSize() >= 11){
            setSafe(true);
        }
    }

    /**
     * adds a single tile to the corpTiles variable
     * Step 4/4
     *Method Order:
     *      1 - GameState : placeTile
     *      2 - Scoreboard : initCorpTileAdd
     *      3 - Corporations : addTileToCorp
     *      4 - CorpInfo : addCorpTile
     */
    public void addCorpTile(Tile t){
        getCorpTiles().put(t.getLocation(), t);
        checkIfSafe();
        //this.corpTiles.add(t);
    }

    public void addAllTiles(){

    }

    /**
     * returns the size of the corpTiles ArrayList
     */
    public int getCorpSize(){
        return getCorpTiles().size();
    }

    public void addCorpStock(Integer qty){
        //THE RESTRICTION OF NOT EXCEEDING 25 WILL BE HELD IN THE getQty METHOD IN SCOREBOARD
        setAvailableStocks(getAvailableStocks() + qty);
    }

    public void removeCorpStock(Integer qty){
        //THE RESTRICTION OF NOT GOING BELOW 0 WILL BE HELD AT THE UI LEVEL
        setAvailableStocks(getAvailableStocks() - qty);
    }
}
