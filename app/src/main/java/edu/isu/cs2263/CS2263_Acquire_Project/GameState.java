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
    Gameboard gameboard;
    Scoreboard scoreboard;
    int currentPlayerTracker = 0;
    Boolean hasPlayed = false;

    private static GameState instance = null;
    private GameState(Integer numberOfPlayers){
        gameboard = new Gameboard();
        scoreboard = new Scoreboard(numberOfPlayers);
    }
    public static GameState getInstance(Integer numberOfPlayers){
        if (instance==null){
            instance=new GameState(numberOfPlayers);
        }
        return instance;
    }


    public void placeTile(Tile handTile, String playerName){
        getGameboard().recordTile(handTile);
        handTile.activateTile();
        HashMap<String, List<Tile>> result = getGameboard().getActionAndTiles(handTile);
        String action = (new ArrayList<>(result.keySet())).get(0);
        List<Tile> tList = result.get(action);
        String cName = null;

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
            cName = getScoreboard().getCorporations().getTilesCorp(handTile);
            //The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        }
        //tList.add(handTile);
        //getGameboard().getTile(handTile.getRow(), handTile.getCol()).setCorp(cName); //Sets the corporation for the tile on the gameboard
        updateAffectedTiles(tList);
        removeTileFromPlayer(playerName, handTile);
    }

    private void updateAffectedTiles(List<Tile> affectedTiles){
        String cName;
        for(Tile t : affectedTiles){
            if(t != null){
                cName = getScoreboard().getCorporations().getTilesCorp(t);
                getGameboard().getTile(t.getRow(), t.getCol()).setCorp(cName);
            }
        }
    }

    public HashMap<String, Integer> endGame(){
        if(checkIfGameCanEnd()){
            return getScoreboard().getWinners();
            //CODE TO END GAME
        }
        return new HashMap<String, Integer>();
    }

    /**
     * Iterates through corporations checking if there are any active and unsafe corporations OR if any corporation is equal or greater 41 tiles in size
     * @return returns a boolean if true the game can end if false the game cannot end
     */
    public boolean checkIfGameCanEnd(){
        boolean canEndBool = true;
        boolean isSafe;
        boolean isActive;
        for(String corpName : getScoreboard().getCorporations().getCorps().keySet()){
            isSafe = getScoreboard().getCorporations().getCorp(corpName).isSafe();
            isActive = getScoreboard().getCorporations().getCorp(corpName).isStatus();
            if(!isSafe && isActive){ //doesnt allow the game to end if there is an active corporation that is not safe
                canEndBool = false;
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

    public void removeTileFromPlayer(String playerName, Tile t){
        getScoreboard().getPlayers().getPlayerByName(playerName).getPHand().removeTile(t);
    }

    public void checkPlayerHandForRefresh(String playerName){
        List<Tile> playerHand = getScoreboard().getPlayers().getPlayerByName(playerName).getPHand().getPlayersTiles();
        List<Tile> tilesToRemove = getCorpsForRefreshTiles(playerHand);
        Integer tilesToRefresh = tilesToRemove.size();
        Tile newTile;
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
     *
     * @param playerHand
     * @return
     */
    public List<Tile> getCorpsForRefreshTiles(List<Tile> playerHand){
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
    public boolean checkTileRefresh(List<String> corpNames){
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
}
