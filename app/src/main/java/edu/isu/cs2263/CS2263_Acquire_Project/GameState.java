package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.*;

public class GameState {
    Gameboard gameboard;

    public static void main(String[] args) {

    }


    public void startGame(){
        System.out.println("Start Game");
    }

    public void endGame(){
        System.out.println("End Game");
    }

    public void saveGame() { System.out.println("Savegame");}

    public void loadGame() { System.out.println("Loadgame");}

    public void initGame(int NumbPlayers){
        System.out.println("initGame");
    }

    public void initScoreboard(){
        System.out.println("initScoreboard");
    }

    public void initPlayers(){
        System.out.println("initPlayers");
    }

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




    public void placeTile(char row, char col){
        //RECORD TILE WILL RETURN A TILE OBJECT
        gameboard.recordTile();
        Tile t = new Tile(row, col, null);
        HashMap<String, Tile[]> result = new HashMap<>();
        String action = (new ArrayList<String>(result.keySet())).get(0);

        if(action.equals("Add to corp")){
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
