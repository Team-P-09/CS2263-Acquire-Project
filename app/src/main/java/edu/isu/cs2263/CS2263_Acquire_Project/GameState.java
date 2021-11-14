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
        HashMap<String, List<Tile>> result = getGameboard().recordTile(handTile);
        String action = (new ArrayList<>(result.keySet())).get(0);
        List<Tile> tList = result.get(action);
        String cName = null;

        if(!action.equals("Nothing")){
            if(action.equals("Add to Corp")) {
                getScoreboard().initCorpTileAdd(tList);
            }else if(action.equals("Merge")){
                getScoreboard().initMerge(tList);
            }else if(action.equals("Founding Tile")){
                getScoreboard().initFounding(tList, playerName);
            }
            cName = getScoreboard().getCorporations().getTilesCorp(handTile);
            //The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        }
        getGameboard().getTile(handTile.getRow(), handTile.getCol()).setCorp(cName); //Sets the corporation for the tile on the gameboard
    }

    public void runGame(){
        boolean runGameBool = true;
        List<PlayerInfo> players = getScoreboard().getPlayers().getActivePlayers();
        String playerName;
        while(runGameBool){
            for(PlayerInfo player : players){
                playerName = player.getPName();
                runGameBool = runTurn(playerName);
                if(!runGameBool){break;} //exits the game if the player chooses to end the game
            }
        }
    }

    public boolean runTurn(String playerName){
        //OBSERVER FOR BUTTON : PLAYER TO PLACE ONE TILE
        //OBSERVER FOR BUTTON : ALLOW PLAYER TO BUY STOCK
        //OBSERVER FOR BUTTON : ALLOW PLAYER TO SELL STOCK
        //OBSERVER FOR BUTTON : ALLOW PLAYER TO END TURN
        //OBSERVER FOR BUTTON : ALLOW PLAYER TO SHOW RULES
        //OBSERVER FOR BUTTON : END GAME (RUN checkIfGameCanEnd())
        //Return true if end game is not pressed else return false
        return true;
    }

    public void endGame(){
        if(checkIfGameCanEnd()){
            getScoreboard().getWinners();
            //CODE TO END GAME
        }
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

        //create new Gamestate object with the saved parameters
        //ideally, should be able to pass the saved gameboard and scoreboard through here. ADD GAMEBOARD AND SCOREBOARD TO GAMESTATE CONSTRUCTOR PARAMS?
        //ALSO, ISN'T NUMBER OF PLAYERS SAVED IN SCOREBOARD? WHY WOULD THAT PARAM NEED TO PASS INTO GAMESTATE AS WELL?
        GameState previousGame = new GameState(0);

        return previousGame;
    }

}
