package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Gameboard {
    //public Tile tile;
    public String infoCard;
    Tile[][] gameboard = new Tile[9][12];

    public Gameboard(){
        gameboard = initGameboard();
    }

    /**
     * Activates the placed tile
     * Returns a HashMap for the action that will be taken after the tile is played
     * @return
     */
    public HashMap<String, List<Tile>> recordTile(Tile t){
        activateTile(t.getRow(), t.getCol());
//        Tile t = getTile(r, c);
        //Logic for getting adjacent tiles
        List<Tile> tList = getAdjacentTiles(t);

        String action = decideAction(t);


//        Tile[] tMap = {this.getTile(r, c-1), this.getTile(r, c+1), this.getTile(r-1, c), this.getTile(r+1, c)};
        HashMap<String, List<Tile>> actionMap = new HashMap<>();
        actionMap.put(action, tList);
        return actionMap;
    }

    public Tile[][] initGameboard(){
        for(int r = 0 ; r < gameboard.length ; r++){
            for(int c = 0 ; c < gameboard[r].length ; c ++){
                gameboard[r][c] = new Tile(r, c);
            }
        }
        return gameboard;
    }

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

    public String decideAction(Tile tile){

        //WRITE CODE TO VERIFY BOUNDS OF ARRAY LIST
        String tileUp = gameboard[tile.getCol()][tile.getRow()+1].getCorp();
        String tileDown = gameboard[tile.getCol()][tile.getRow()-1].getCorp();
        String tileLeft = gameboard[tile.getCol()-1][tile.getRow()].getCorp();
        String tileRight = gameboard[tile.getCol()+1][tile.getRow()].getCorp();

        String[] adjCNames = new String[]{tileUp, tileDown, tileLeft, tileRight};

        String action;
        String aStat;

        //Get data for all of the adjacent tiles
        HashMap<String, Integer> adjCorps = new HashMap<>();
        for(String cName : adjCNames){
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
        return gameboard[r][c];
    }

    private void updateTileCorp(int r, int c, String cName){
        gameboard[r][c].setCorp(cName);
    }

    private void activateTile(int r, int c){
        gameboard[r][c].activateTile();
    }


}
