package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

    public static File savePlayers(String jsonFile, Players players) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold data
        String jsonString = gson.toJson(players);

        try {
            //create the jsonFile
            File file = new File(jsonFile);
            // file.createNewFile();

            //write the json string into the json file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);

            //close the file
            fileWriter.flush();
            fileWriter.close();

            return file;

        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public Players loadPlayers(String jsonFile){
        try {
            //create Gson instance
            Gson gson = new Gson();

            //create a reader
            Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

            //set type for players
            Type playersType = new TypeToken<Players>(){}.getType();

            //convert JSON string to players obj
            Players players_obj = gson.fromJson(reader, playersType);

            //close reader
            reader.close();

            return players_obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
