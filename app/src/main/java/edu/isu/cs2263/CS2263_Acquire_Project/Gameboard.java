package edu.isu.cs2263.CS2263_Acquire_Project;

public class Gameboard {
    //public Tile tile;
    public String infoCard;
    Tile[][] gameboard;

    public Gameboard(){
        gameboard = new Tile[9][12];
    }

    public void recordTile(Tile tile){
        int x = tile.getCol();
        int y = tile.getRow();
        String corp = tile.getCorp();

        gameboard[x][y] = tile;
        assignTileCorp(tile);
    }

    public void assignTileCorp(Tile tile){
        String tileUp = gameboard[tile.getCol()][tile.getRow()+1].getCorp();
        String tileDown = gameboard[tile.getCol()][tile.getRow()-1].getCorp();
        String tileLeft = gameboard[tile.getCol()-1][tile.getRow()].getCorp();
        String tileRight = gameboard[tile.getCol()+1][tile.getRow()].getCorp();

        //need to determine whether we need to do nothing, found, add to corp, or initiate merge
        //need to compare all combinations
        //up vs right
        //up vs down
        //up vs left
        //right vs down
        //right vs left
        //down vs left
        if (tileUp == null || tileRight == null || tileDown == null || tileLeft == null){
            //do nothing
        }
        //logic for

    }




}
