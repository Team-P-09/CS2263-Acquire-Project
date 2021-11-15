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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Getter
public class Gameboard {
    public String infoCard;
    Tile[][] gameboard;

    public Gameboard(){
        gameboard = initGameboard();
    }

    /**
     * Activates the placed tile
     * Returns a HashMap for the action that will be taken after the tile is played
     * @return
     */
    public HashMap<String, List<Tile>> recordTile(Tile t){
        activateTile(t);
        List<Tile> tList = getAdjacentTiles(t);
        tList.add(t); //add the initial tile to the tile List
        String action = decideAction(tList);

        HashMap<String, List<Tile>> actionMap = new HashMap<>();
        actionMap.put(action, tList);
        return actionMap;
    }

    public Tile[][] initGameboard(){
        Tile[][] gboard = new Tile[9][12];
        for(int r = 0 ; r < gboard.length ; r++){
            for(int c = 0 ; c < gboard[r].length ; c ++){
                gboard[r][c] = new Tile(r, c);
            }
        }
        return gboard;
    }

    //todo:handle outOfBounds response for tiles on the edge of the gameboard
    public List<Tile> getAdjacentTiles(Tile t){
        int row = t.getRow();
        int col = t.getCol();
        List<Tile> adjTiles = new ArrayList<>();
        adjTiles = checkAdj(row, col, adjTiles, true);
        adjTiles = checkAdj(col, row, adjTiles, false);
        return adjTiles;
    }

    /**
     * returns a List of Strings for the active and not null corporations
     * @param tiles
     * @return
     */
    public List<String> getAdjTileCorpNames(List<Tile> tiles){
        List<String> adjCorpNames = new ArrayList<>();
        String corpName;
        for(Tile t : tiles){
            corpName = t.getCorp();
            if(t.isStatus() && corpName != null){
                adjCorpNames.add(corpName);
            }
        }
        return adjCorpNames;
    }

    /**
     * Validates that the dimensions will not exceed the max gameboard size
     * @param dimA
     * @param dimB
     * @param adjTList
     * @param isRow
     * @return
     */
    private List<Tile> checkAdj(Integer dimA, Integer dimB, List<Tile> adjTList, Boolean isRow){
        int dimAMax;
        if(isRow){
            dimAMax = 8;
        }else{
            dimAMax = 11;
        }
        for(int i = -1 ; i < 2 ; i+=2){
            dimA = dimA + i;
            if(dimA <= dimAMax || dimA >=0){
                if(isRow){adjTList.add(getTile(dimA, dimB));}
                else{adjTList.add(getTile(dimB, dimA));}
            }
        }
        return adjTList;
    }

    /**
     * Decides the necissary action based on a list of adjacent tiles
     * @param adjTileList
     * @return
     */
    public String decideAction(List<Tile> adjTileList){
        String action;
        String actionStat;
        String corpNameForAdding;
        String cName;

        //Get data for all of the adjacent tiles
        HashMap<String, Integer> adjCorps = new HashMap<>(); //This will be a hashmap with <CorpName, Number Of Tiles>
        for(Tile t : adjTileList){
            cName = t.getCorp();
            if(t.isStatus()){
                if(adjCorps.containsKey(cName)){
                    adjCorps.put(cName, adjCorps.get(cName) + 1);
                }
                else{
                    adjCorps.put(cName, 1);
                }
            }
        }

        //decide which action to return
        if(adjCorps.size() > 1){
            action = "Merge";
        }else if(adjCorps.size() == 1){
            //ADD TO CORP OR FOUNDING
            corpNameForAdding = (new ArrayList<>(adjCorps.keySet())).get(0); //Name of the corporation for the adjacent tile(s)
            if(corpNameForAdding != null){
                action = "Add to Corp";
            }else{action = "Founding Tile";}
        }else{ action = "Nothing";}

        return action;
    }

    public Tile getTile(int r, int c){
        return getGameboard()[r][c];
    }

    private void updateTileCorp(Tile t, String cName){
        int row = t.getRow();
        int col = t.getCol();
        getGameboard()[row][col].setCorp(cName);
    }

    private void activateTile(Tile t){
        int row = t.getRow();
        int col = t.getCol();
        getGameboard()[row][col].activateTile();
    }


    /**
     * @param jsonFile (string to become json file)
     * @param gameboard_obj (scoreboard obj to save)
     * @return File (jsonFile to later be deserialized)
     * @throws IOException
     */
    //reference for reading JSON files to java: https://attacomsian.com/blog/gson-read-json-file
    public static File saveGameboard(String jsonFile, Gameboard gameboard_obj) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold scoreboard data
        String jsonString = gson.toJson(gameboard_obj);

        try {
            //create the jsonFile
            File file = new File(jsonFile);

            //write the json string into the json file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);

            //close the file
            fileWriter.flush();
            fileWriter.close();

        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonFile (jsonFile string that was created in saveGameboard)
     * @return returns a Gameboard object that was previously saved
     */
    public Gameboard loadGameboard(String jsonFile) {
        try {
            //create Gson instance
            Gson gson = new Gson();

            //create a reader
            Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

            //set type for gameboard
            Type gameboardType = new TypeToken<Gameboard>(){}.getType();

            //convert JSON string to gameboard object
            Gameboard gameboard_obj = gson.fromJson(reader, gameboardType);

            //close reader
            reader.close();

            return gameboard_obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
