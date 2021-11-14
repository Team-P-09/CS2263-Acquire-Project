package edu.isu.cs2263.CS2263_Acquire_Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Gameboard {
    //public Tile tile;
    public String infoCard;
    Tile[][] gameboard = new Tile[9][12];

    public Gameboard(){
        gameboard = initGameboard();
    }

//    public void recordTile(Tile tile){
//        int x = tile.getCol();
//        int y = tile.getRow();
//        String corp = tile.getCorp();
//
//        gameboard[x][y] = tile;
//        assignTileCorp(tile);
//    }

    /**
     * Activates the placed tile
     * Returns a HashMap for the action that will be taken after the tile is played
     * @param r the row cords of the new tile
     * @param c the col cords of the new tile
     * @return
     */
    public HashMap<String, Tile[]> recordTile(int r, int c){
        activateTile(r,c);
        Tile t = getTile(r, c);
        String action = decideAction(t);
        Tile[] tMap = {this.getTile(r, c-1), this.getTile(r, c+1), this.getTile(r-1, c), this.getTile(r+1, c)};
        HashMap<String, Tile[]> actionMap = new HashMap<>();
        actionMap.put(action, tMap);
        return actionMap;
    }

    public Tile[][] initGameboard(){
        for(int r = 0 ; r < gameboard.length ; r++){
            for(int c = 0 ; c < gameboard[r].length ; c ++){
                gameboard[r][c] = new Tile(r, c);
            }
        }
        return gameboard;
    }


    public String decideAction(Tile tile){
        String tileUp = gameboard[tile.getCol()][tile.getRow()+1].getCorp();
        String tileDown = gameboard[tile.getCol()][tile.getRow()-1].getCorp();
        String tileLeft = gameboard[tile.getCol()-1][tile.getRow()].getCorp();
        String tileRight = gameboard[tile.getCol()+1][tile.getRow()].getCorp();

        String[] adjCNames = new String[]{tileUp, tileDown, tileLeft, tileRight};

        String action;
        String aStat;

        //Get data for all of the adjacent tiles
        HashMap<String, Integer> adjCorps = new HashMap<>();
        for(String cName : adjCNames){
            if(adjCorps.containsKey(cName)){
                adjCorps.put(cName, adjCorps.get(cName) + 1);
            }
            else{
                adjCorps.put(cName, 1);
            }
        }

        //decide which action to return
        if(adjCorps.size() > 1){
            action = "Merge";
        }else if(adjCorps.size() == 1){
            //ADD TO CORP OR FOUNDING
            aStat = (new ArrayList<String>(adjCorps.keySet())).get(0);
            if(aStat != null){
                action = "Add to Corp";
            }else{action = "Founding Tile";}
        }else{ action = "Nothing";}

        return action;
    }

    public Tile getTile(int r, int c){
        return gameboard[r][c];
    }

    private void updateTileCorp(int r, int c, String cName){
        gameboard[r][c].setCorp(cName);
    }

    private void activateTile(int r, int c){
        gameboard[r][c].activateTile();
    }


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
