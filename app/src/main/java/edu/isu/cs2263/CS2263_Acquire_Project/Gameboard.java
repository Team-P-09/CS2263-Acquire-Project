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

import java.util.*;

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
    public HashMap<String, List<Tile>> getActionAndTiles(Tile t){
        List<Tile> tList = getAdjacentTiles(t);
        String action = decideAction(tList);
        tList.add(t); //add the initial tile to the tile List

        HashMap<String, List<Tile>> actionMap = new HashMap<>();
        actionMap.put(action, tList);
        return actionMap;
    }

    /**
     * Initialzies a new gameboard
     * @return
     */
    private Tile[][] initGameboard(){
        Tile[][] gboard = new Tile[9][12];
        for(int r = 0 ; r < gboard.length ; r++){
            for(int c = 0 ; c < gboard[r].length ; c ++){
                gboard[r][c] = new Tile(r, c);
            }
        }
        return gboard;
    }

    /**
     *Returns a list of adjacent tiles
     * Wont return all corporations in a corporation tile chain
     * Ran by getCorpsForRefreshTiles in GameState, getActionAndTiles in Gameboard
     * @param t
     * @return
     */
    public List<Tile> getAdjacentTiles(Tile t){
        //Variables for DFS algorithm
        List<Tile> adjTiles;
        Queue<Tile> tileQueue = new ArrayDeque<>();
        HashMap<String, Tile> visitedTiles = new HashMap<>();
        tileQueue.add(t);
        Tile curTile;
        String curTLoc;
        //DFS algo to add all connected adjacent tiles
        //does not add unactivated tiles
        //does not search adjacent tiles for tiles with a corporation(they will all have the same corporation)
        while(!tileQueue.isEmpty()){
            curTile = tileQueue.remove();
            adjTiles = checkAdj(curTile); //This wont retrieve tiles if the tile has a corporation
            for(Tile adjT : adjTiles){
                curTLoc = adjT.getLocation();
                if(!visitedTiles.containsKey(curTLoc)){
                    visitedTiles.put(curTLoc, adjT);
                    tileQueue.add(adjT);
                }
            }
        }

        //Translate visited tile hashmap to list for further processing
        List<Tile> outTiles = new ArrayList<>();
        for(String loc : visitedTiles.keySet()){
            outTiles.add(visitedTiles.get(loc));
        }


        //Remove null entries
        outTiles.remove(null);
        //above code does below code, testing to verify
//        for(Tile nt : outTiles){
//            if(nt == null){
//                outTiles.remove(nt);
//            }
//        }
        return outTiles;
    }

    /**
     * returns a List of Strings for the active and not null corporations
     * @param tiles
     * @return
     */
    public List<String> getAdjTileCorpNames(List<Tile> tiles){
        HashSet<String> uniqueCorpNames = new HashSet<>();
        String corpName;
        for(Tile t : tiles){
            corpName = t.getCorp();
            if(t.isStatus() && corpName != null && !uniqueCorpNames.contains(corpName)){
                uniqueCorpNames.add(corpName);
            }
        }
        return new ArrayList<>(uniqueCorpNames);
    }

    /**
     * Checks if the tile has a corporation, if not then adds adjacent tiles to a List of tiles to return
     * Ran by getAdjacentTiles
     * @param t input tile to check and find adjacents
     * @return list of adjacent tiles max size 4
     */
    private List<Tile> checkAdj(Tile t){
        Integer row = t.getRow();
        Integer col = t.getCol();

        List<Tile> adjTiles = new ArrayList<>();
        Boolean isCorp = getTile(row, col).getCorp() != null;
        if(!isCorp){
            adjTiles = checkSides(row, col, adjTiles, true);
            adjTiles = checkSides(col, row, adjTiles, false);
        }

        return adjTiles;
    }

    /**
     * Validates that the dimensions will not exceed the max gameboard size
     * Ran by checkAdj
     * @param dimA
     * @param dimB
     * @param adjTList
     * @param isRow
     * @return
     */
    private List<Tile> checkSides(Integer dimA, Integer dimB, List<Tile> adjTList, Boolean isRow){
        int dimAMax;
        Integer newDim;
        List<Tile> outTiles = new ArrayList<>();
        if(isRow){
            dimAMax = 8;
        }else{
            dimAMax = 11;
        }
        for(Integer i = -1 ; i < 2 ; i+=2){
            newDim = dimA + i;
            //System.out.println(newDim);
            if(newDim <= dimAMax && newDim >=0){
                if(isRow){

                    adjTList.add(getTile(newDim, dimB));
                }
                else{
                    adjTList.add(getTile(dimB, newDim));
                }
            }
        }
        //only adds active tiles
        for(Tile t : adjTList){
            if(t.isStatus()){
                outTiles.add(t);
            }
        }
        return outTiles;
    }

    /**
     * Decides the necissary action based on a list of adjacent tiles
     * ran by getActionAndTiles
     * @param adjTileList
     * @return
     */
    private String decideAction(List<Tile> adjTileList){
        String action;
        String corpNameForAdding;
        String cName;
        Integer numberOfNonNullCorps;

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

        numberOfNonNullCorps = findNumberOfNonNullCorps(adjCorps);
        //decide which action to return
        if(adjCorps.size() > 1){
            if(numberOfNonNullCorps > 1){
                action = "Merge";
            }else{
                action = "Add to Corp";
            }
        }else if(adjCorps.size() == 1){
            //ADD TO CORP OR FOUNDING
            corpNameForAdding = (new ArrayList<>(adjCorps.keySet())).get(0); //Name of the corporation for the adjacent tile(s)
            //System.out.println(corpNameForAdding);
            if(corpNameForAdding != null){
                action = "Add to Corp";
            }else{action = "Founding Tile";}
        } else{ action = "Nothing";}

//        if(adjCorps.size() > 1){
//            action = "Merge";
//        }else if(adjCorps.size() == 1){
//            //ADD TO CORP OR FOUNDING
//            corpNameForAdding = (new ArrayList<>(adjCorps.keySet())).get(0); //Name of the corporation for the adjacent tile(s)
//            //System.out.println(corpNameForAdding);
//            if(corpNameForAdding != null){
//                action = "Add to Corp";
//            }else{action = "Founding Tile";}
//        } else{ action = "Nothing";}

        return action;
    }

    public Tile getTile(int r, int c){
        return getGameboard()[r][c];
    }

    private Integer findNumberOfNonNullCorps(HashMap<String, Integer> corps){
        List<String> adjCorps = new ArrayList<>(corps.keySet());
        Integer corpCounter = 0;
        for(String corpName : adjCorps){
            if(corpName != null){
                corpCounter++;
            }
        }
        return corpCounter;
    }

    /**
     * Activates the tile on the gameboard
     * @param t
     */
    public void recordTile(Tile t){
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
