package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;

@Getter @Setter
public class PlayerInfo {
    //instance variables
    String pName;
    Hand pHand;
    Wallet pWallet;

    //constructor method
    public PlayerInfo(String name, ArrayList<String> corpNames, ArrayList<Tile> tArry){
        pName = name;
        pHand = new Hand(tArry);
        pWallet = new Wallet(corpNames);
    }

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
