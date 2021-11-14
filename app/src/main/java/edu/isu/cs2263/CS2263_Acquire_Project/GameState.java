package edu.isu.cs2263.CS2263_Acquire_Project;

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

    public void endGame(){
        scoreboard.getWinners();
        //EXIT APP
    }

    public void saveGame() { System.out.println("Savegame");}

    public void loadGame() { System.out.println("Loadgame");}

    public void initTileStack(){
        System.out.println("InitTStack");
    }

    public void displayGameboard(){
        System.out.println("DisplayGameboard");
    }

    public void displayScoreboard(){
        System.out.println("displayScoreboard");
    }

    public void displayHand(){
        System.out.println("Displayhand");
    }

    public void placeTile(Tile handTile){
        HashMap<String, List<Tile>> result = getGameboard().recordTile(handTile);
        String action = (new ArrayList<>(result.keySet())).get(0);
        List<Tile> tList = result.get(action);
        String cName = null;

        if(action.equals("Add to Corp")){
            getScoreboard().initCorpTileAdd(tList);
            cName = getScoreboard().getCorpFromTile(handTile);
        }else if(action.equals("Merge")){
            //EXECUTE MERGE ACTION
            //Check merge status
            //scoreboard.initMerge(tArry); //Players isnt set up yet
            //UPDATE TILE WITH CORRECT CORP
        }else if(action.equals("Founding Tile")){
            //EXECUTE FOUNDING TILE FUNCTION
            //UPDATE TILE WITH CORRECT CORP
        }//The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        getGameboard().getTile(handTile.getRow(), handTile.getCol()).setCorp(cName); //Sets the corporation for the tile on the gameboard
    }

}
