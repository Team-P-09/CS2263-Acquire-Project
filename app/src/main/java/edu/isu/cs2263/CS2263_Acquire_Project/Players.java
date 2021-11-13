package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.*;

@Getter @Setter
public class Players {
    ArrayList<String> pNameOrder; //ORDER OF PLAYERS AND PLAYER NAMES
    HashMap<String, PlayerInfo> activePlayers; //THIS WILL BE THE HASHMAP FOR PLAYERS AND THEIR INFO
    TileStack tStack;


    public Players(Integer number, ArrayList<String> corpNames){
        pNameOrder = orderPlayers(number);
        activePlayers = initPlayers(pNameOrder, corpNames);
        tStack = new TileStack();
    }

    /**
     * Initializes the HashMap for players
     * Requires orderPlayers to be ran first
     * @param pNames
     * @param corpNames
     * @return
     */
    public HashMap<String, PlayerInfo> initPlayers(ArrayList<String> pNames, ArrayList<String> corpNames){
        HashMap<String, PlayerInfo> newPlayers = new HashMap<>();

        for(int i = 0 ; i < pNames.size() ; i++){
            ArrayList<Tile> tArry = getTStack().pullTiles(6);
            PlayerInfo newP = new PlayerInfo(pNames.get(i), corpNames, tArry);
            newPlayers.put(pNames.get(i), newP);
        }
        return newPlayers;
    }

    /**
     * names and orders Players
     * Must be ran before initPlayers
     * @param number
     * @return
     */
    public ArrayList<String> orderPlayers(Integer number){
        ArrayList<String> playerList = namePlayers(number);
        return sortPlayers(playerList);
    }

    /**
     * Takes an interger and names players based on their position
     * Returns an ArrayList of player names as strings
     * @param number
     * @return
     */
    public ArrayList<String> namePlayers(Integer number){
        ArrayList<String> pSet = new ArrayList<>();
        String p = "Player ";
        for(int i = 1 ; i < number +1 ; i++){
            pSet.add(p + i);
        }
        return pSet;
    }

    /**
     * Sorts and names the players
     * @param pSet
     * @return
     */
    public ArrayList<String> sortPlayers(ArrayList<String> pSet){
        SortedMap<String, Integer> playerOrderTM = new TreeMap<>();
        ArrayList<Tile> tilesForPosition = new ArrayList<>();
        Tile t;
        for(String s : pSet){
            t = tStack.popTile();
            tilesForPosition.add(t);
            playerOrderTM.put(s, t.col + t.row);
        }
        tStack.getTileStack().addAll(tilesForPosition);

        return new ArrayList<String>(playerOrderTM.keySet());
    }

    public void startTurn(){
        //allow player to select tile
    }
    public void endTurn(){
        //check if tile pool is not empty
        //system gives player one random tile from pool
        //if player has unplayable tiles
        //refresh the hand with as many tiles as they need
        //or as many tiles as are left
    }
    public void doTurn(){
        //allow player to play tile and
        //choose between buy, sell, trade, hold

    }
    public void merge(){
        //corporations are merged
    }

    private void orderBuy(Corporations corp, int amt){

    }
    private void orderSell(Corporations corp, int amt){

    }
//    public int getWalletInfo(String pName){
//        //int cash = getActivePlayers().
//        //HashMap<String, Integer> stocks = getPWallet().getStocks();
//        //return score;
//    }
}
