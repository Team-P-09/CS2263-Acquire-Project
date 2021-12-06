/*
 * MIT License
 *
 * Copyright (c) 2021 Thomas Evans, David Lindeman, and Natalia Castaneda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
    private boolean hasBeenFounded;

    public CorpInfo(){ //int stPrice, int avStocks, ArrayList<Tile> coTiles, int coSize, boolean stat
        stockPrice = 100;
        availableStocks = 25;
        corpTiles = new HashMap<>();
        status = false;
        isSafe = false;
        hasBeenFounded = false;
    }

    public void foundCorp(){
        hasBeenFounded = true;
    }

    /**
     * Retrieves the corpTiles and empties the corpTiles
     * @return an ArrayList of the Tile class
     */
    public HashMap<String, Tile> popAllTiles(){
        HashMap<String, Tile> outTiles = getCorpTiles();
        setCorpTiles(new HashMap<>());
        return outTiles;
    }

    private void checkIfSafe(){
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
