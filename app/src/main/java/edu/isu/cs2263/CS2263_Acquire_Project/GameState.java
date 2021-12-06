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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class GameState {
    private Gameboard gameboard;
    private Scoreboard scoreboard;

    private Integer numOfPlayers;

    private int currentPlayerTracker = 0;
    public Boolean hasPlayed = false;
    public Boolean endGame = false;
    private static GameState instance = null; //making this static was causing tons of problems in testing
    private Integer currentBoughtStock = 0;

  //  public File savedScoreB;
    //public File savedGameB;
   // public File savedNumOfPlayers;

    private GameState(Integer numberOfPlayers){
        gameboard = new Gameboard();
        scoreboard = new Scoreboard(numberOfPlayers);
    }

    public static GameState getInstance(Integer numberOfPlayers){
        if (instance == null){
            instance = new GameState(numberOfPlayers);
        }
        return instance;
    }

    /**
     * This is only used for tests
     * Because we are using a singleton we need this method to properly test our gamestate
     */
    void resetInstance(){
        Integer pNumb = scoreboard.getPlayers().getActivePlayers().size();
        gameboard = new Gameboard();
        scoreboard = new Scoreboard(pNumb);
        currentPlayerTracker = 0;
        hasPlayed = false;
        endGame = false;
        instance = null; //making this static was causing tons of problems in testing
        currentBoughtStock = 0;
    }


    /**
     * Places a tile on the board and determines which action needs to be executed
     * @param handTile the tile from the players input in the UI
     * @param playerName the string name of the player
     */
    public void placeTile(Tile handTile, String playerName){
        getGameboard().recordTile(handTile);
        handTile.activateTile();
        HashMap<String, List<Tile>> result = getGameboard().getActionAndTiles(handTile);
        String action = (new ArrayList<>(result.keySet())).get(0);
        List<Tile> tList = result.get(action);

        if(!action.equals("Nothing")){
            if(action.equals("Add to Corp")) {
                tList =  getScoreboard().initCorpTileAdd(tList);
            }else if(action.equals("Merge")){
                tList = mergeController(tList);
            }else if(action.equals("Founding Tile")){
                if(getScoreboard().getNonActiveCorps().size() > 0) {
                    foundingController(tList, playerName);
                }
            }
            //The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        }
        updateAffectedTiles(tList);
        removeTileFromPlayer(playerName, handTile);
    }

    public void resetBuyCounter(){
        currentBoughtStock = 0;
    }

    /**
     * updates a list of tiles to correctly represent which corporation they belong to in the Gameboard object
     * @param affectedTiles
     */
    private void updateAffectedTiles(List<Tile> affectedTiles){
        String cName;
        for(Tile t : affectedTiles){
            if(t != null){
                cName = getScoreboard().getCorporations().getTilesCorp(t);
                getGameboard().getTile(t.getRow(), t.getCol()).setCorp(cName);
            }
        }
    }

    /**
     * Checks if game can end then returns winner information
     * If the game cannot end returns null
     * @return
     */
    public void endGame(){
        if(checkIfGameCanEnd()){
            HashMap<String, Integer> winners = new HashMap<>(getScoreboard().getWinners());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Winner!");
            alert.setHeaderText("Winner!");
            alert.setContentText(winners.toString());

            alert.showAndWait();
        }
    }

    /**
     * Iterates through corporations checking if there are any active and unsafe corporations OR if any corporation is equal or greater 41 tiles in size
     * @return automatically exits if there is an unsafe active corporation or if any corporation has a size greater than 41, else checks if at least one corporation is safe and active
     */
    public boolean checkIfGameCanEnd(){
        boolean canEndBool = false;
        boolean isSafe;
        boolean isActive;
        Set<String> corpNames = getScoreboard().getCorporations().getCorps().keySet();
        //check if a corp has been founded and is safe, exits and returns if conditions are broken
        for(String corpName : corpNames){
            isSafe = getScoreboard().getCorporations().getCorp(corpName).isSafe();
            isActive = getScoreboard().getCorporations().getCorp(corpName).isStatus();
            if(isSafe && isActive){
                canEndBool = true;
            }

            //Exit conditions
            //if any corporation is not safe and is active returns false and exits
            if(!isSafe && isActive){
                return false;
            }
            //exits and ends if any corp is over size 41
            if(getScoreboard().getCorporations().getCorp(corpName).getCorpSize() >= 41){
                return true;
            }
        }
        return canEndBool;
    }

    public void drawTileToPlayer(String playerName){
        if(getScoreboard().getPlayers().getTStack().getTileStack().size() > 0){
            Tile t = getScoreboard().getPlayers().getTStack().popTile();
            getScoreboard().getPlayers().getPlayerByName(playerName).getPHand().addTile(t);
        }
    }

    /**
     * removes tiles from players hand
     * @param playerName
     * @param t
     */
    public void removeTileFromPlayer(String playerName, Tile t){
        getScoreboard().getPlayers().getPlayerByName(playerName).getPHand().removeTile(t);
    }

    /**
     * Checks if players hand needs to be refreshed
     * Then removes all unplayable tiles from the players hand
     * @param playerName
     */
    //todo invalid tiles are not removed
    public void checkPlayerHandForRefresh(String playerName){
        List<Tile> playerHand = getScoreboard().getPlayers().getPlayerByName(playerName).getPHand().getPlayersTiles();
        List<Tile> tilesToRemove = getCorpsForRefreshTiles(playerHand);
        Integer tilesToRefresh = tilesToRemove.size();
        if(tilesToRefresh > 0){
            for(Tile t : tilesToRemove){
                removeTileFromPlayer(playerName, t);
            }
            for(int i = 0 ; i < tilesToRefresh ; i++){
                drawTileToPlayer(playerName);
            }
        }
    }

    /**
     * Removes all tiles from a players hand that cannot be played
     * @param playerHand
     * @return
     */
    private List<Tile> getCorpsForRefreshTiles(List<Tile> playerHand){
        List<Tile> tilesToRemove = new ArrayList<>();
        for(Tile t : playerHand){
            List<Tile> adjtiles = getGameboard().getAdjacentTiles(t);
            List<String> adjCorpNames = getGameboard().getAdjTileCorpNames(adjtiles);
            if(checkTileRefresh(adjCorpNames)){
                tilesToRemove.add(t);
            }
        }
        return tilesToRemove;
    }

    /**
     * Checks if two or more of the adjacent corporation names are safe
     * @param corpNames
     * @return  boolean, true if must be refreshed
     */
    private boolean checkTileRefresh(List<String> corpNames){
        //RETURNS true IF THE TILE IS ONLY ADJACENT TO SAFE CORPORATIONS
        boolean refreshBool = false;
        Integer safeCounter = 0;
        for(String corpName : corpNames){
            if(getScoreboard().getCorporations().getCorp(corpName).isSafe()){
                safeCounter++;
                if(safeCounter >= 2){
                    refreshBool = true;
                    break; //if the true condition has been met exit
                }
            }
        }
        return refreshBool;
    }

    /**
<<<<<<< HEAD
     * Gets an ArrayList of available corp names then runs initializes the founding of a corporation
     * @param tList
     * @param playerName
     */
    public void foundingController(List<Tile> tList, String playerName){
        ArrayList<String> availableCorps = scoreboard.getNonActiveCorps();
        String title = "Chose a corporation to start";
        String header = "Unfounded Corporations:";
        String corpName = getDecision(availableCorps, title, header);
        scoreboard.initFounding(tList, playerName, corpName);
    }

    public Integer buyController(String playerName, String corpName, Integer buyLimit){
        Integer maxQty = scoreboard.maxBuy(playerName, corpName, buyLimit);
        Integer qty = scoreboard.getQty(corpName, maxQty, "Buy");
        return scoreboard.initBuy(playerName, corpName, qty);
    }

    public List<Tile> mergeController(List<Tile> tList){
        ArrayList<String> mCorps = scoreboard.findCorps(tList); //We will be used to identify the players who will need to take a merge action
        String domCorpName = scoreboard.getDomCorpName(mCorps);
        mCorps.remove(domCorpName);
        mCorps = scoreboard.removeSafeCorps(mCorps);
        //only runs the merge turn if there are sub corps to merge into the dom corp
        if(mCorps.size() > 0){
            List<String> affectedPlayers = scoreboard.findAffectedPlayers(mCorps);
            scoreboard.runMergeTurn(mCorps, domCorpName, affectedPlayers);
            scoreboard.initMerge(mCorps, domCorpName, affectedPlayers);
        }
        scoreboard.addUnassignedTilesToCorp(tList, domCorpName);
        return scoreboard.retrieveTiles(scoreboard.getCorporations().getCorp(domCorpName).getCorpTiles());
    }

    /**
     * Grabs a user input using a choice dialogue box
     * @param choiceList
     * @param title
     * @param header
     * @param <T>
     * @return
     */
    private <T> T getDecision(List<T> choiceList, String title, String header){
        ArrayList<T> choices = new ArrayList<>();
        for(T choice : choiceList){
            choices.add(choice);
        }

        ChoiceDialog<T> dialog = new ChoiceDialog<>(choiceList.get(0), choices);
        dialog.setTitle(title);
        dialog.setHeaderText(header);

        Optional<T> pChoice = dialog.showAndWait();
        while(!pChoice.isPresent()){
            pChoice = dialog.showAndWait();
        }
        return dialog.getSelectedItem();
    }

    /**
     * @return returns a list of files that will need to be reloaded to get gamestate back
     */
    public void saveGameState(){ //NEED TO BE ABLE TO SAVE NUMBER OF PLAYERS
      //  File savedScoreB;
      //  File savedGameB;
      //  File savedNumOfPlayers;

        try {
            numOfPlayers = scoreboard.getNumofPlayers();
            //empty files that will be filled with saved data
            String jsonFileGameboard = "savedGameB";
            String jsonFileScoreboard = "savedScoreB";

            //writing number of players to a file
            Writer wr = new FileWriter("savedNumOfPlayers");
            wr.write(Integer.toString(numOfPlayers));
            wr.close();
            //writing current player tracker
            Writer wr2 = new FileWriter("savedCurrentPlayerTracker");
            wr2.write(Integer.toString(currentPlayerTracker));
            wr2.close();
            //writing hasPlayed status
            Writer wr3 = new FileWriter("saveHasPlayed");
            if (hasPlayed == true){
                wr3.write(Integer.toString(1));
            }
            if (hasPlayed == false){
                wr3.write(Integer.toString(0));
            }
            wr3.close();
            //writing end game status
            Writer wr4 = new FileWriter("saveEndGame");
            if (endGame == true){
                wr4.write(Integer.toString(1));
            }
            if (endGame == false){
                wr4.write(Integer.toString(0));
            }
            wr4.close();
            //writing current bought stock status
            Writer wr5 = new FileWriter("savedCurrentBoughtStock");
            wr5.write(Integer.toString(currentBoughtStock));
            wr5.close();

            //getting data from scoreboard and gameboard to be saved
            Scoreboard.saveScoreboard(jsonFileScoreboard, scoreboard);
            Gameboard.saveGameboard(jsonFileGameboard, gameboard);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * //@param gameFiles list of files that need to be reloaded
     * @return returns gamestate object that will reload previous games
     */
    public void loadGameState(GameState savedGame) throws FileNotFoundException {
        int savedCPTracker = 0;
        Boolean savedHasPlayed = null;
        Boolean savedEndGame = null;
        int savedCBS = 0;

     //   File savedPlayerCount = new File("savedNumOfPlayers");
     //   File savedCurrentPlayerTracker = new File("savedCurrentPlayerTracker");
      //  File saveHasPlayed = new File("saveHasPlayed");
      //  File saveEndGame = new File("saveEndGame");
      //  File savedCurrentBoughtStock = new File("savedCurrentBoughtStock");

        int numPlayers = 0;

        //create empty objects to hold saved data
        int status = 0;

        Scanner sc = new Scanner(new File("savedNumOfPlayers"));
        while(sc.hasNextLine()) {
            numPlayers = Integer.parseInt(sc.nextLine());
        }

        Scanner sc2 = new Scanner(new File("savedCurrentPlayerTracker"));
        while(sc2.hasNextLine()) {
             savedCPTracker = Integer.parseInt(sc2.nextLine());
        }

        Scanner sc3 = new Scanner(new File("saveHasPlayed"));
        while(sc3.hasNextLine()) {
            status = Integer.parseInt(sc3.nextLine());
            if (status ==0){
                savedHasPlayed = false;
            }
            if (status==1){
                savedHasPlayed = true;
            }
        }

        Scanner sc4 = new Scanner(new File("saveEndGame"));
        while(sc4.hasNextLine()) {
            status = Integer.parseInt(sc4.nextLine());
            if (status ==0){
                savedEndGame = false;
            }
            if (status ==1){
                savedEndGame = true;
            }
        }

        Scanner sc5 = new Scanner(new File("savedCurrentBoughtStock"));
        while(sc5.hasNextLine()) {
            savedCBS = Integer.parseInt(sc5.nextLine());
        }

        //retrieving data from json files using load methods from each class
        savedGame.numOfPlayers = numPlayers;
        savedGame.scoreboard = Scoreboard.loadScoreboard("savedScoreboard");
        savedGame.gameboard = Gameboard.loadGameboard("savedGameboard");
        savedGame.hasPlayed = savedHasPlayed;
        savedGame.currentPlayerTracker = savedCPTracker;
        savedGame.endGame = savedEndGame;
        savedGame.currentBoughtStock = savedCBS;
    }

    public PlayerInfo getCurrentPlayer(){
        return scoreboard.getPlayers().getPlayerByName("Player " + (currentPlayerTracker+1));
    }

    public void nextPlayer(){
        if(currentPlayerTracker == scoreboard.getPlayers().getActivePlayers().size()-1){
            currentPlayerTracker = 0;
        }
        else {
            currentPlayerTracker++;
        }
        hasPlayed = false;
    }

    public void hasPlayed(){
        if (hasPlayed == false){
            hasPlayed = true;
        }
    }

    public void setEndGame(){
        endGame = true;
    }
}
