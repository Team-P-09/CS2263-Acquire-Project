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
    public static GameState getInstance(Integer nuberOfPlayers){
        if (instance==null){
            instance=new GameState(nuberOfPlayers);
        }
        return instance;
    }

//    public GameState(Integer numberOfPlayers){
//        gameboard = new Gameboard();
//        scoreboard = new Scoreboard(numberOfPlayers);
//    }

    public static void startGame(){
        //PROMPT FOR PLAYER NAMES
    }

    public void endGame() {
        scoreboard.getWinners();
        //EXIT APP
    }

    public void saveGame() {
        System.out.println("Savegame");
    }

    public void loadGame() {
        System.out.println("Loadgame");
    }

    public void initTileStack() {
        System.out.println("InitTStack");
    }

    public void displayGameboard() {
        System.out.println("DisplayGameboard");
    }

    public void displayScoreboard() {
        System.out.println("displayScoreboard");
    }

    public void displayHand() {
        System.out.println("Displayhand");
    }

    public void placeTile(Tile handTile){
        HashMap<String, List<Tile>> result = getGameboard().recordTile(handTile);
        String action = (new ArrayList<>(result.keySet())).get(0);
        List<Tile> tList = result.get(action);
        String cName = null;

        if (action.equals("Add to Corp")) {
            getScoreboard().initCorpTileAdd(tList);
            cName = getScoreboard().getCorpFromTile(handTile);
        }else if(action.equals("Merge")){
            //EXECUTE MERGE ACTION
            //Check merge status
            //scoreboard.initMerge(tArry); //Players isnt set up yet
            //UPDATE TILE WITH CORRECT CORP
        } else if (action.equals("Founding Tile")) {
            //EXECUTE FOUNDING TILE FUNCTION
            //UPDATE TILE WITH CORRECT CORP
        }//The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        getGameboard().getTile(handTile.getRow(), handTile.getCol()).setCorp(cName); //Sets the corporation for the tile on the gameboard
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

}