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

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class GameState {
    private Gameboard gameboard;
    private Scoreboard scoreboard;
    private int currentPlayerTracker = 0;
    public Boolean hasPlayed = false;
    public Boolean endGame = false;
    private static GameState instance = null; //making this static was causing tons of problems in testing
    //todo create variable of tiles played per turn
    //todo prevent player from being able to initiate a buy when the cannot buy any more tiles

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
                tList = getScoreboard().initMerge(tList);
            }else if(action.equals("Founding Tile")){
                if(getScoreboard().getAvailableCorps().size() > 0) {
                    getScoreboard().initFounding(tList, playerName);
                }
            }
            //The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        }
        updateAffectedTiles(tList);
        removeTileFromPlayer(playerName, handTile);
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
    public HashMap<String, Integer> endGame(){
        if(checkIfGameCanEnd()){
            return getScoreboard().getWinners();
        }
        return null;
    }

    /**
     * Iterates through corporations checking if there are any active and unsafe corporations OR if any corporation is equal or greater 41 tiles in size
     * @return automatically exits if there is an unsafe active corporation or if any corporation has a size greater than 41, else checks if at least one corporation is safe and active
     */
    //todo merge in Corporations is correctly deactivating corps so the error probably lies in the UI or initMerge in Scoreboard
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
//
//        //if there is a corp
//        if(canEndBool){
//            for(String corpName : corpNames){
//                isSafe = getScoreboard().getCorporations().getCorp(corpName).isSafe();
//                isActive = getScoreboard().getCorporations().getCorp(corpName).isStatus();
//                System.out.println(corpName + "\n" + isSafe + "\n" + isActive);
//                if(!isSafe && isActive){ //doesnt allow the game to end if there is an active corporation that is not safe
//                    canEndBool = false;
//                }
//            }
//        }
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
     * @param currentScoreboard current scoreboard state to save
     * @param currentGameboard current gameboard state to save
     * @return returns a list of files that will need to be reloaded to get gamestate back
     */
    public List<File> saveGameState(Scoreboard currentScoreboard, Gameboard currentGameboard) {
        try {
            //empty files that will be filled with saved data
            String jsonFileTileStack = null;
            String jsonFilePlayers = null;
            String jsonFileCorporations = null;
            String jsonFileGameboard = null;
            String jsonFileScoreboard = null;
            List<File> savedGameFiles = null;

            //getting data from scoreboard and gameboard to be saved
            //tilestack, players, and corporations data come from scoreboard
            File savedTileS = TileStack.saveTileStack(jsonFileTileStack, currentScoreboard.getPlayers().getTStack());
            File savedPlay = Players.savePlayers(jsonFilePlayers, currentScoreboard.getPlayers());
            File savedCorp = Corporations.saveCorporations(jsonFileCorporations, currentScoreboard.getCorporations());

            File savedScoreB = Scoreboard.saveScoreboard(jsonFileScoreboard, currentScoreboard);
            File savedGameB = Gameboard.saveGameboard(jsonFileGameboard, currentGameboard);

            //add saved json files to list to be deserialized later
            savedGameFiles.add(savedTileS);
            savedGameFiles.add(savedPlay);
            savedGameFiles.add(savedCorp);
            savedGameFiles.add(savedScoreB);
            savedGameFiles.add(savedGameB);

            //return the list of saved game files
            return savedGameFiles;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param gameFiles list of files that need to be reloaded
     * @return returns gamestate object that will reload previous games
     */
    public GameState loadGameState(List<File> gameFiles) {
        //create empty objects to hold saved data
        TileStack tileS = null;
        Players play = null;
        Corporations corp = null;
        Scoreboard scoreb = null;
        Gameboard gameb = null;
        GameState savedGame = null;

        //retrieving data from json files using load methods from each class
        tileS.loadTileStack(gameFiles.get(0).toString());
        play.loadPlayers(gameFiles.get(1).toString());
        corp.loadCorporations(gameFiles.get(2).toString());
        scoreb.loadScoreboard(gameFiles.get(3).toString());
        gameb.loadGameboard(gameFiles.get(4).toString());

        //save tilestack, players, and corps back into scoreboard
        //MAYBE ADD TILESTACK TO SCOREBOARD PARAMS SO IT CAN BE RECALLED?
        scoreb.setPlayers(play);
        scoreb.setCorporations(corp);

        //load up params into gamestate object
        savedGame.scoreboard = scoreb;
        savedGame.gameboard = gameb;

        //return the loaded GameState object
        return savedGame;
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
