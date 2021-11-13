package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.*;

@Getter @Setter
public class Players {
    ArrayList<PlayerInfo> activePlayers;
    TileStack tStack;


    public Players(Integer number, ArrayList<String> corpNames){
        activePlayers = initPlayers(number, corpNames);
        tStack = new TileStack();
    }

    /**
     *
     * @param number
     * @param corpNames
     * @return
     */
    public ArrayList<PlayerInfo> initPlayers(Integer number, ArrayList<String> corpNames){
        //create a new player instance
        //WAITING FOR PLAYER CLASS TO BE FINISHED BEFORE IMPLEMENTATION
        //PLAYERS CLASS MAY BE IMPLEMENTED AND PLAYER WILL BE RENAMED TO PLAYERINFO
        //THIS WILL METHOD WILL BE MOVED TO THE PLAYERS CLASS IF CREATED
        ArrayList<PlayerInfo> newPlayers = new ArrayList<>();

        ArrayList<String> pNames;
        pNames = setPlayers(number);
        pNames = sortPlayers(pNames);


        for(int i = 0 ; i < pNames.size() ; i++){
            ArrayList<Tile> tArry = getTStack().pullTiles(6);
            PlayerInfo newP = new PlayerInfo(pNames.get(i), corpNames, tArry);
            newPlayers.add(newP);
        }

        return newPlayers;

    }

    public ArrayList<String> setPlayers(Integer number){
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
//    public int getWalletInfo(){
//        int cash = getPWallet().getCash();
//        HashMap<String, Integer> stocks = getPWallet().getStocks();
//        return score;
//    }
}
