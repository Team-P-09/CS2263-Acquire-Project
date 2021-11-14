package edu.isu.cs2263.CS2263_Acquire_Project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class GameState {
    static Gameboard gameboard;
    static Scoreboard scoreboard;

    public static void main(String[] args) {
        startGame();
    }

    public static void startGame() {
        //PROMPT FOR PLAYER NAMES
        gameboard = new Gameboard();
        scoreboard = new Scoreboard();
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

    public void placeTile(int row, int col) {
        HashMap<String, Tile[]> result = gameboard.recordTile(row, col);
        String action = (new ArrayList<String>(result.keySet())).get(0);
        Tile[] tArry = result.get(action);
        String cName = null;
        Tile t = gameboard.getTile(row, col);

        if (action.equals("Add to Corp")) {
            scoreboard.initCorpTileAdd(tArry);
            cName = scoreboard.getCorpFromTile(t);
        } else if (action.equals("Merge")) {
            //EXECUTE MERGE ACTION
            //Check merge status
            //scoreboard.initMerge(tArry); //Players isnt set up yet
            //UPDATE TILE WITH CORRECT CORP
        } else if (action.equals("Founding Tile")) {
            //EXECUTE FOUNDING TILE FUNCTION
            //UPDATE TILE WITH CORRECT CORP
        }//The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        gameboard.getTile(row, col).setCorp(cName);

    }

    public List<File> saveGameState(Players gPlayers, Corporations gCorps) {
        try {
            String jsonFileTileStack = null;
            String jsonFilePlayers = null;
            String jsonFileCorporations = null;
            String jsonFileGameboard = null;
            String jsonFileScoreboard = null;
            List<File> savedGame = null;

            //File savedT = gTileStack.saveTileStack(jsonFileTilesStack, scoreboard.getTileStack());
            File savedPlay = gPlayers.savePlayers(jsonFilePlayers, scoreboard.getPlayers());
            File savedCorp = gCorps.saveCorporations(jsonFileCorporations, scoreboard.getCorporations());
            File savedScoreB = Scoreboard.saveScoreboard(jsonFileScoreboard, scoreboard);
            File savedGameB = Gameboard.saveGameboard(jsonFileGameboard, gameboard);

            //savedGame.add(savedT);
            savedGame.add(savedPlay);
            savedGame.add(savedCorp);
            savedGame.add(savedScoreB);
            savedGame.add(savedGameB);

            return savedGame;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GameState loadGameState(List<File> gameFiles) {
        TileStack tileS = null;
        tileS.loadTileStack(gameFiles.get(0).toString());

        Players play = null;
        play.loadPlayers(gameFiles.get(1).toString());

        Corporations corp = null;
        corp.loadCorporations(gameFiles.get(2).toString());

        Scoreboard scoreb = null;
        scoreb.loadScoreboard(gameFiles.get(3).toString());

        Gameboard gameb = null;
        gameb.loadGameboard(gameFiles.get(4).toString());

        return null;
    }
}