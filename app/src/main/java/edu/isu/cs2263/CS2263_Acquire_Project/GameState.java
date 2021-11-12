package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.*;

public class GameState {
    static Gameboard gameboard;
    static Scoreboard scoreboard;

    public static void main(String[] args) {
        startGame();
    }


    public static void startGame(){
        //PROMPT FOR PLAYER NAMES
        gameboard = new Gameboard();
        scoreboard = new Scoreboard();
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




    public void placeTile(int row, int col){
//        Tile t = gameboard.getTile(row, col);
        HashMap<String, Tile[]> result = gameboard.recordTile(row, col);
        String action = (new ArrayList<String>(result.keySet())).get(0);

        if(action.equals("Add to Corp")){
            //ADD THE TILE TO THE CORRECT CORP

            //UPDATE TILE WITH CORRECT CORP
        }else if(action.equals("Merge")){
            //EXECUTE MERGE ACTION
            //UPDATE TILE WITH CORRECT CORP
        }else if(action.equals("Founding Tile")){
            //EXECUTE FOUNDING TILE FUNCTION
            //UPDATE TILE WITH CORRECT CORP
        }//The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
        //UPDATE TILE ON BOARD WITH t


    }
}
