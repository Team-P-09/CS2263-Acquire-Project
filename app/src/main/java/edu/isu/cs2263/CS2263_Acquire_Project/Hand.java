package edu.isu.cs2263.CS2263_Acquire_Project;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Random;

@Getter @Setter
public class Hand {
    //instance variables
    ArrayList<Tile> playersTiles;

    //constructor method
    public Hand(ArrayList<Tile> pTiles){
        playersTiles = pTiles;
    }

    public void addTile(Tile t){
        getPlayersTiles().add(t);
    }

    public void removeTile(Tile t){
        getPlayersTiles().remove(t);
    }

}
