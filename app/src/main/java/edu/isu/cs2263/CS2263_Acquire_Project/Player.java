package edu.isu.cs2263.CS2263_Acquire_Project;

public class Player {
    //instance variables
    String pName;
    Hand pHand;
    Wallet pWallet;

    //constructor method
    public Player(String name, Hand hand, Wallet wallet){
        pName = name;
        pHand = hand;
        pWallet = wallet;
    }

    //other methods
    public void setPlayerName(String Name){pName = Name;}

    public String getPlayerName(){return pName;}

    private void playTile(Tile playedTile){
        //record tile

       /* if tileDoesNothing(){
            //send message that tile has been played
            return
        }
        if tileIsFounding(){
            //check for available corporations
            //prompt player to choose corp
            //if stock is available for founded corp,
            //add founder's stock to wallet
            //else tile does nothing
            return
        }
        if tileCanBeAddedtoCorp(){
            //
            return
        }
        if tileInitiatesMerge(){
            //system decides merging conditions
            //if properties are same size and considered unsafe
            //let player decide surviving corp
            //record surviving corp
            return
        } */
    }
    public void startTurn(){
        //allow player to select tile
    }
    public void endTurn(){
        //check if tile pool is not empty
        //system gives player one random tile from pool
        //if player has unplayable tiles
        //refresh the hand with as many tiles as they need
        //or as many tiles as are left
    }
    public void doTurn(){
        //allow player to play tile and
        //choose between buy, sell, trade, hold

    }
    public void merge(){
        //corporations are merged
    }

    private void orderBuy(Corporations corp, int amt){

    }
    private void orderSell(Corporations corp, int amt){

    }
    public int getWalletScore(){
        int cash = pWallet.cashValue();
        int stocks = pWallet.stockValue();
        int score = cash + stocks;
        return score;
    }
}
