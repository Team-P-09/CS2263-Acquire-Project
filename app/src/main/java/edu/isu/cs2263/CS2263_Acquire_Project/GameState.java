package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class GameState {
    Gameboard gameboard;
    Scoreboard scoreboard;

    public GameState(Integer numberOfPlayers){
        gameboard = new Gameboard();
        scoreboard = new Scoreboard(numberOfPlayers);
    }

    public void saveGame() { System.out.println("Savegame");}

    public void loadGame() { System.out.println("Loadgame");}

    public void placeTile(Tile handTile, String playerName){
        HashMap<String, List<Tile>> result = getGameboard().recordTile(handTile);
        String action = (new ArrayList<>(result.keySet())).get(0);
        List<Tile> tList = result.get(action);
        String cName = null;

        if(action.equals("Add to Corp")){
            getScoreboard().initCorpTileAdd(tList);
            cName = getScoreboard().getCorpFromTile(handTile);
        }else if(action.equals("Merge")){
            getScoreboard().initMerge(tList);
            cName = getScoreboard().getCorpFromTile(handTile);
        }else if(action.equals("Founding Tile")){
            getScoreboard().initFounding(tList, playerName);
            cName = getScoreboard().getCorpFromTile(handTile);
        }//The tag "Nothing" is not accounted for as nothing would change from the initialized tile object
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
}
