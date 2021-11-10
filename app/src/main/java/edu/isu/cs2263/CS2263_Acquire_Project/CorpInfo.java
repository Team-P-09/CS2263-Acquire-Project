package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Data;
import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;

@Getter @Setter
public class CorpInfo {
    int stockPrice;
    int availableStocks;
    ArrayList<Tile> corpTiles;
    boolean status;

    public CorpInfo(){ //int stPrice, int avStocks, ArrayList<Tile> coTiles, int coSize, boolean stat
        stockPrice = 100;
        availableStocks = 25;
        corpTiles = new ArrayList<>();
        status = false;;
    }

    /**
     * Retrieves the corpTiles and empties the corpTiles
     * @return an ArrayList of the Tile class
     */
    public ArrayList<Tile> retrieveTiles(){
        ArrayList<Tile> outTiles = this.corpTiles;
        this.corpTiles = new ArrayList<Tile>();
        return outTiles;
    }

    /**
     * adds a single tile to the corpTiles variable
     */
    public void addCorpTile(Tile t){
        this.corpTiles.add(t);
    }

    /**
     * returns the size of the corpTiles ArrayList
     */
    public int getCorpSize(){
        return this.corpTiles.size();
    }
}
