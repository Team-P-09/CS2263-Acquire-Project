package edu.isu.cs2263.CS2263_Acquire_Project;


import java.util.ArrayList;
import java.util.Random;

public class Hand {
    //instance variables
    //******TileStack class not yet created
    TileStack tilePool;
    ArrayList<Tile> playersTiles;

    //get an arraylist of tiles in each hand
    Random rand = new Random();

    //constructor method
    public Hand(ArrayList<Tile> pTiles){
        for (int i = 0, i < 6, i++){
            int tile = rand.nextInt(tilePool);
            pTiles.add(tile);
        }
        playersTiles = pTiles;
        //six random tiles from tilestack stored in an arraylist for each player
    }

    //other methods
    public void drawTile(){
        //random tile from tilestack is added to player's hand
    }
    public void drawHand(){
        //get six random tiles from tile pool and add to hand
    }
    public void refreshHand(){
        //remove unplayable tiles
        //add new random tiles from TileStack
    }
}
