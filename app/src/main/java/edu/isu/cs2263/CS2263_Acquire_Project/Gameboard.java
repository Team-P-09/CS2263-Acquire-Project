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
    }

    public void assignTileCorp(CorpInfo corp, Tile t){

    }

}
