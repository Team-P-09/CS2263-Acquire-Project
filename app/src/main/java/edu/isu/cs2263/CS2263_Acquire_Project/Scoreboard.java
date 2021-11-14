package edu.isu.cs2263.CS2263_Acquire_Project;

import javafx.scene.control.ChoiceDialog;
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
public class Scoreboard {
    Players players;
    ArrayList<String> corpNames = new ArrayList<>(Arrays.asList("Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"));
    Corporations corporations;

    public Scoreboard(Integer numberOfPlayers) {
        corporations = new Corporations(getCorpNames());
        players = new Players(numberOfPlayers, getCorpNames());
    }

    /**
     * Merges the corporations on the tiles from the given tile array
     * tArray will be a size of 5, order of entry is unimportant, Array datatype is used for easy iteration
     * Calls mergeCorps from Corporations
     */
    public void initMerge(Tile[] tArray){
        ArrayList<String> mCorps = findCorps(tArray); //We will be used to identify the players who will need to take a merge action
        ArrayList<String> domCorp = findDomCorp(mCorps);
        String domCorpName;

        if(checkMergeStatus(domCorp)){
            domCorpName = getMergeDecision(domCorp);//domCorp.get(choiceIndex);
        }else{
            domCorpName = domCorp.get(0);
        }
        //FIND ALL PLAYERS THAT WILL BE AFFECTED BY THIS MERGE
        //LOOP THROUGH THEM GIVING THEM THE BUY/SELL/HOLD OPTIONS
        //

        mCorps.remove(domCorpName);
        getCorporations().mergeCorps(domCorpName, mCorps);
    }

    private List<String> findAffectedPlayers(ArrayList<String> mCorps){
        List<String> affectedPlayers = new ArrayList<>();
        for(String cName : mCorps){
            for(PlayerInfo player : getPlayers().getActivePlayers()){
                if(player.getPWallet().getStocks().containsKey(cName)){
                    affectedPlayers.add(player.getPName());
                }
            }
        }
        return affectedPlayers;
    }

    private String getMergeDecision(ArrayList<String> domCorp){
        ArrayList<String> choices = new ArrayList<>();
        for(String dCorpName : domCorp){
            choices.add(dCorpName);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(domCorp.get(0), choices);
        dialog.setTitle("Chose the dominate corporation");
        dialog.setHeaderText("Corporation names?");

        Optional<String> domChoice = dialog.showAndWait();
        while(!domChoice.isPresent()){
            domChoice = dialog.showAndWait();
        }
        return dialog.getSelectedItem();
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
            for(Map.Entry<String, CorpInfo> c : getCorporations().getCorps().entrySet()){
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

        for(Map.Entry<String, CorpInfo> c : getCorporations().getCorps().entrySet()){
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
    public void initCorpTileAdd(List<Tile> tList){ //String corpName, Tile t
        String corpName = "";
        Tile tile = new Tile(-1,-1);
        for(Tile t : tList){
            if(t.status && t.getCorp() != null){
                corpName = t.getCorp();
            }else if(t.status){tile = t;}
        }
        getCorporations().addTileToCorp(corpName, tile);
    }



    //reference for reading JSON files to java: https://attacomsian.com/blog/gson-read-json-file
    public static File saveScoreboard(String jsonFile, Scoreboard scoreboard_obj) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold data
        String jsonString = gson.toJson(scoreboard_obj);

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

    public static Scoreboard loadScoreboard(String jsonFile) {
        try {
            //create Gson instance
            Gson gson = new Gson();

            //create a reader
            Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

            //set type for scoreboard
            Type scoreboardType = new TypeToken<Scoreboard>(){}.getType();

            //convert JSON string to scoreboard obj
            Scoreboard scoreboard_obj = gson.fromJson(reader, scoreboardType);

            //close reader
            reader.close();

            return scoreboard_obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
