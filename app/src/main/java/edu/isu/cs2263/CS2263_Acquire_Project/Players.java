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
import java.util.stream.Collectors;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Getter @Setter
public class Players {
    private ArrayList<PlayerInfo> activePlayers; //ORDER OF PLAYERS AND PLAYER NAMES
    private TileStack tStack;
    private Integer num;


    public Players(Integer number, ArrayList<String> corpNames){
        num = number;
        tStack = new TileStack();
        activePlayers = initPlayers(number, corpNames);
    }

    /**
     *
     * @param number
     * @param corpNames
     * @return
     */
    private ArrayList<PlayerInfo> initPlayers(Integer number, ArrayList<String> corpNames){
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
    private ArrayList<String> namePlayers(Integer number){
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
    private ArrayList<String> sortPlayers(ArrayList<String> pSet){
        HashMap<String, Integer> playerOrderTM = new HashMap<>();
        ArrayList<Tile> tilesForPosition = new ArrayList<>();
        Tile t;
        for(String s : pSet){
            t = getTStack().popTile();
            tilesForPosition.add(t);
            playerOrderTM.put(s, t.getCol() + t.getRow());
        }
        getTStack().getTileStack().addAll(tilesForPosition);

        //THIS CODE WAS FOUND ON https://stackabuse.com/how-to-sort-a-hashmap-by-value-in-java/
        Map<String, Integer> sortedPlayers = playerOrderTM.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));

        return new ArrayList<>(sortedPlayers.keySet());
    }

    public PlayerInfo getPlayerByName(String name){
        for(PlayerInfo pinfo : getActivePlayers()){
            if(pinfo.getPName().equals(name)){
                return pinfo;
            }
        }
        return null;
    }

    public void buyStock(String pName, String cName, Integer qty, Integer stockVal){
        getPlayerByName(pName).getPWallet().addStock(cName, qty);
        getPlayerByName(pName).getPWallet().removeCash(qty * stockVal);
    }

    public void sellStock(String pName, String cName, Integer qty, Integer stockVal){
        getPlayerByName(pName).getPWallet().removeStock(cName, qty);
        getPlayerByName(pName).getPWallet().addCash(qty * stockVal);
    }

/*
    public static File savePlayers(String jsonFile, Players players_obj) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold data
        String jsonString = gson.toJson(players_obj);

        try {
            //create the jsonFile
            File playersFile = new File(jsonFile);
            // file.createNewFile();

            //write the json string into the json file
            FileWriter fileWriter = new FileWriter(playersFile);
            fileWriter.write(jsonString);

            //close the file
            fileWriter.flush();
            fileWriter.close();

            return playersFile;

        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }
*/
    /**
     * @param jsonFile (jsonFile string that was created in savePlayers)
     * @return returns a Players object that was previously saved
     */
    /*
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
    }*/
}
