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
    ArrayList<PlayerInfo> activePlayers; //ORDER OF PLAYERS AND PLAYER NAMES
    TileStack tStack;


    public Players(Integer number, ArrayList<String> corpNames){
        tStack = new TileStack();
        activePlayers = initPlayers(number, corpNames);
    }

    /**
     *
     * @param number
     * @param corpNames
     * @return
     */
    public ArrayList<PlayerInfo> initPlayers(Integer number, ArrayList<String> corpNames){
        ArrayList<PlayerInfo> newPlayers = new ArrayList<>();
        ArrayList<String> playerList = namePlayers(number);
        ArrayList<String> pNames = sortPlayers(playerList);

        for(int i = 0 ; i < pNames.size() ; i++){
            ArrayList<Tile> tArry = getTStack().pullTiles(6);
            PlayerInfo newP = new PlayerInfo(pNames.get(i), corpNames, tArry);
            newPlayers.add(newP);
        }
        return newPlayers;
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
            t = getTStack().popTile();
            tilesForPosition.add(t);
            playerOrderTM.put(s, t.col + t.row);
        }
        getTStack().getTileStack().addAll(tilesForPosition);

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

    /**
     * @param jsonFile (string to become json file)
     * @param players_obj (Players obj to save)
     * @return File (jsonFile to later be deserialized)
     * @throws IOException
     */
    public static File savePlayers(String jsonFile, Players players_obj) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold data
        String jsonString = gson.toJson(players_obj);

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

    /**
     * @param jsonFile (jsonFile string that was created in savePlayers)
     * @return returns a Players object that was previously saved
     */
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

    public PlayerInfo getCurrentPlayer(){
        //todo:add logic to get the real current player
        return activePlayers.get(0);
    }
}
