package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.*;

@Getter @Setter
public class Scoreboard {
    //List<Player> players;
    ArrayList<String> corpNames = new ArrayList<>(Arrays.asList("Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"));
    Corporations corporations;

    public Scoreboard() {
        corporations = new Corporations(corpNames);
        //Initialize Players
    }

    /**
     * Merges the corporations on the tiles from the given tile array
     * tArray will be a size of 5, order of entry is unimportant, Array datatype is used for easy iteration
     * Calls mergeCorps from Corporations
     */
    public void initMerge(Tile[] tArray, ArrayList<String> mPlayers){
        ArrayList<String> mCorps = findCorps(tArray); //We will be used to identify the players who will need to take a merge action
        ArrayList<String> domCorp = findDomCorp(mCorps);
        String domCorpName;
        if(checkMergeStatus(domCorp)){
            //NOTIFY PLAYER THAT THEY NEED TO SELECT
            //RUN CORP MERGE ON SELECTED CORP
            int choiceIndex = 0; //THIS WILL BE DECIDED BY PLAYER INPUT
            domCorpName = domCorp.get(choiceIndex);
        }else{
            domCorpName = domCorp.get(0);
        }
        mCorps.remove(domCorpName);
        getCorporations().mergeCorps(domCorpName, mCorps);
    }

    /**
     * Initiates and controls the sell operation updating the available stocks for a corp and updating the sock and cash of a player
     * @param playerName
     * @param corpName
     * @param Qty
     */
    public void initSell(String playerName, String corpName, Integer Qty){
        //METHOD FOR VALIDATING SALE QTY
        int stockVal = corporations.getCorp(corpName).getStockPrice();
        //METHOD FOR EXECUTING THE SALE IN THE PLAYERS WALLET
        corporations.getCorp(corpName).addCorpStock(Qty);
    }

    /**
     * Removes available stock from specific CorpInfo
     * Removes cash form specific Player
     * Adds stock value to specific player
     * @param playerName
     * @param corpName
     * @param Qty
     */
    public void initBuy(String playerName, String corpName, Integer Qty){
        boolean validQty = getCorporations().getCorp(corpName).getAvailableStocks() >= Qty;
        if(validQty){
            int stockVal = getCorporations().getCorp(corpName).getStockPrice();
            //METHOD FOR BUYING FROM PLAYER (VALIDATES SALE AMT AND EXECUTES IF ACCEPTABLE)
            getCorporations().getCorp(corpName).removeCorpStock(Qty);
        }
    }

    /**
     * Returns true if there are two or more corps tied for largest size
     * Returns false if there is only one dominate corp
     * @param domList
     * @return
     */
    public boolean checkMergeStatus(ArrayList<String> domList){
        if(domList.size() > 1){
            return true;
        }
        return false;
    }

    /**
     * Returns a corp name as a string
     * used in initMerge
     * iterates through the Array of Tiles returning the name
     * @param tArray
     * @return
     */
    public ArrayList<String> findCorps(Tile[] tArray){
        ArrayList<String> cNames = new ArrayList<>();
        int cSize;
        for(Tile t : tArray){
            for(Map.Entry<String, CorpInfo> c : corporations.corps.entrySet()){
                if(c.getValue().getCorpTiles().containsKey(t)){
                    cNames.add(c.getKey());
                    break; //each tile can only appear once therefore we stop looking
                }
            }
        }
        return cNames;
    }

    /**
     * Returns an ArrayList of Strings
     * Usually will be a single entry
     * @param mCorps
     * @return      Returns the dominate corp name(s)
     */
    public ArrayList<String> findDomCorp(ArrayList<String> mCorps){
        int leadingCorpSize = 0;
        ArrayList<String> domCorpList = new ArrayList<>();
        int cSize;
        for(String s : mCorps){
            cSize = getCorporations().getCorp(s).getCorpSize();
            if(leadingCorpSize < cSize){
                domCorpList = new ArrayList<>();
                domCorpList.add(s);

            } else if(leadingCorpSize == cSize){
                domCorpList.add(s);
            }
        }
        return domCorpList;
    }



    /**
     * Returns a HashMap with a corpName as a key and an Array of Integer[3]
     * The Array has positions [CorpInfo size, CorpInfo Stock Price, CorpInfo Available Stocks]
     * @return      HashMap String, Integer[] of CorpInfo for display
     */
    public HashMap<String, Integer[]> displayCorpInfo(){
        HashMap<String, Integer[]> displayInfo = new HashMap<>();
        int cSize;
        int cPrice;
        int cStocks;

        for(Map.Entry<String, CorpInfo> c : this.corporations.corps.entrySet()){
            Integer[] infoArray = new Integer[3];
            String cName = c.getKey();
            cSize = c.getValue().getCorpSize();
            cPrice = c.getValue().getStockPrice();
            cStocks = c.getValue().getAvailableStocks();

            infoArray[0] = cSize;
            infoArray[1] = cPrice;
            infoArray[2] = cStocks;

            //ArrayList<Tile> cTiles = c.getValue().getCorpTiles(); //not addding in the tiles as this information is readily available to the player
            displayInfo.put(cName, infoArray);
        }
        return displayInfo;
    }

    public void displayPlayers(){
        System.out.println("displayPlayers");
    }

    /**
     * Initializes a players list object
     * @param playerNames
     */
    public void initPlayers(List<String> playerNames){
        //create a new player instance
        //WAITING FOR PLAYER CLASS TO BE FINISHED BEFORE IMPLEMENTATION
        //PLAYERS CLASS MAY BE IMPLEMENTED AND PLAYER WILL BE RENAMED TO PLAYERINFO
        //THIS WILL METHOD WILL BE MOVED TO THE PLAYERS CLASS IF CREATED
        for(String s : playerNames){
            System.out.println(s); //this will
        }
    }

    public void getWinners(){
        System.out.println("Get winners");
        //for players in players object
        //get the score of each player
        //return a sorted list of HashMaps(PlayerName : Score) highest to lowest
    }

    /**
     * Sorts through the array and returns the corp name and the tile to be added
     * Step 2/4
     * Method Order:
     *      1 - GameState : placeTile
     *      2 - Scoreboard : initCorpTileAdd
     *      3 - Corporations : addTileToCorp
     *      4 - CorpInfo : addCorpTile
     */
    public void initCorpTileAdd(Tile[] tArry){ //String corpName, Tile t
        String corpName = "";
        Tile tile = new Tile(-1,-1);
        for(Tile t : tArry){
            if(t.status && t.getCorp() != null){
                corpName = t.getCorp();
            }else if(t.status){tile = t;}
        }
        getCorporations().addTileToCorp(corpName, tile);
    }

    public String getCorpFromTile(Tile t){
        return getCorporations().getTilesCorp(t);
    }

}
