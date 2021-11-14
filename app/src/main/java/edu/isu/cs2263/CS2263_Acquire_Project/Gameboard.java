package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class Gameboard {
    public String infoCard;
    Tile[][] gameboard;

    public Gameboard(){
        gameboard = initGameboard();
    }

    /**
     * Activates the placed tile
     * Returns a HashMap for the action that will be taken after the tile is played
     * @return
     */
    //todo:actually take the actions and apply the changes to the tiles
    public HashMap<String, List<Tile>> recordTile(Tile t){
        activateTile(t);
        List<Tile> tList = getAdjacentTiles(t);
        tList.add(t); //add the initial tile to the tile List
        String action = decideAction(tList);

        HashMap<String, List<Tile>> actionMap = new HashMap<>();
        actionMap.put(action, tList);
        return actionMap;
    }

    public Tile[][] initGameboard(){
        Tile[][] gboard = new Tile[9][12];
        for(int r = 0 ; r < gboard.length ; r++){
            for(int c = 0 ; c < gboard[r].length ; c ++){
                gboard[r][c] = new Tile(r, c);
            }
        }
        return gboard;
    }

    //todo:handle outOfBounds response for tiles on the edge of the gameboard
    public List<Tile> getAdjacentTiles(Tile t){
        int row = t.getRow();
        int col = t.getCol();
        List<Tile> adjTiles = new ArrayList<>();
        adjTiles = checkAdj(row, col, adjTiles, true);
        adjTiles = checkAdj(col, row, adjTiles, false);
        return adjTiles;
    }

    /**
     * Validates that the dimensions will not exceed the max gameboard size
     * @param dimA
     * @param dimB
     * @param adjTList
     * @param isRow
     * @return
     */
    private List<Tile> checkAdj(Integer dimA, Integer dimB, List<Tile> adjTList, Boolean isRow){
        int dimAMax;
        if(isRow){
            dimAMax = 9;
        }else{
            dimAMax = 12;
        }
        for(int i = -1 ; i < 2 ; i+=2){
            dimA = dimA + i;
            if(dimA <= dimAMax || dimA >=0){
                if(isRow){adjTList.add(getTile(dimA, dimB));}
                else{adjTList.add(getTile(dimB, dimA));}
            }
        }
        return adjTList;
    }

    /**
     * Decides the necissary action based on a list of adjacent tiles
     * @param tList
     * @return
     */
    public String decideAction(List<Tile> tList){
        String action;
        String aStat;
        String cName;

        //Get data for all of the adjacent tiles
        HashMap<String, Integer> adjCorps = new HashMap<>();
        for(Tile t : tList){
            cName = t.getCorp();
            if(adjCorps.containsKey(cName)){
                adjCorps.put(cName, adjCorps.get(cName) + 1);
            }
            else{
                adjCorps.put(cName, 1);
            }
        }

        //decide which action to return
        if(adjCorps.size() > 1){
            action = "Merge";
        }else if(adjCorps.size() == 1){
            //ADD TO CORP OR FOUNDING
            aStat = (new ArrayList<String>(adjCorps.keySet())).get(0);
            if(aStat != null){
                action = "Add to Corp";
            }else{action = "Founding Tile";}
        }else{ action = "Nothing";}

        return action;
    }

    public Tile getTile(int r, int c){
        return getGameboard()[r][c];
    }

    private void updateTileCorp(Tile t, String cName){
        int row = t.getRow();
        int col = t.getCol();
        getGameboard()[row][col].setCorp(cName);
    }

    private void activateTile(Tile t){
        int row = t.getRow();
        int col = t.getCol();
        getGameboard()[row][col].activateTile();
    }


}
