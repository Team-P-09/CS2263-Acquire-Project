/*
 * MIT License
 *
 * Copyright (c) 2021 Thomas Evans, David Lindeman, and Natalia Castaneda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class TestGameState {
    private GameState tGameState = GameState.getInstance(4);
    private GameState gameStateCopy;
    private String p1Name;
    private String p2Name;
    private String p3Name;
    private String p4Name;

//    org.junit.jupiter.execution.parallel.enabled = true;
//    org.junit.jupiter.execution.parallel.mode.default = concurrent;
//
//    @Extensions()

//    @Rule
//    public RunInThreadRule runInThread = new RunInThreadRule();
//    @TestInstance()


    /**
     * Because the game state will be an instance we cant declare it in the setup
     */
    @Execution(ExecutionMode.SAME_THREAD)
    @BeforeEach
    void setUp(){
        //set up gameboard and players
        p1Name = tGameState.getScoreboard().getPlayers().getActivePlayers().get(0).getPName();
        p2Name = tGameState.getScoreboard().getPlayers().getActivePlayers().get(1).getPName();
        p3Name = tGameState.getScoreboard().getPlayers().getActivePlayers().get(2).getPName();
        p4Name = tGameState.getScoreboard().getPlayers().getActivePlayers().get(3).getPName();
        //we create a separate variable for GameState that will be accessed by each class
//        tGameState = initGameState;
        gameStateCopy = tGameState;
    }

    @AfterEach
    void tearDown(){
        //resets the gamestate to an initial state
        tGameState.resetInstance();
    }



    /**
     * Creates a copy of a GameState instance and executes the operations that placeTile will execute
     * The methods used for verifying placeTile have been tested in their respective class tests
     */
    @Test
    void testPlayTileNothing(){
        Tile t = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().get(0);
        t.activateTile();
        //Action to be tested
        tGameState.placeTile(t,p1Name);
        //Action to be tested against
        gameStateCopy.getGameboard().recordTile(t);
        gameStateCopy.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().removeTile(t);

        assertEquals(gameStateCopy, tGameState);
    }

    @Test
    void testPlayTileNothingWithSevenCorps(){
        //set all corps as safe
        for(String corpName : tGameState.getScoreboard().getCorporations().getCorps().keySet()){
            tGameState.getScoreboard().getCorporations().getCorp(corpName).setStatus(true);
            gameStateCopy.getScoreboard().getCorporations().getCorp(corpName).setStatus(true);
        }

        //get tile and set up adjacent tile that should cause a founding
        Tile t = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().get(0);
        t.activateTile();

        //we have to set up the adjacent tile to be placed
        int adjTrow = 0;
        int adjTcol = t.getCol();
        if(t.getRow() + 1 <= 9){
             adjTrow = t.getRow();
        }else{
            adjTrow = t.getRow()-1;
        }
        Tile adjT = new Tile(adjTrow,adjTcol);
        //records the adjacent tile on the board
        tGameState.getGameboard().recordTile(adjT);
        gameStateCopy.getGameboard().recordTile(adjT);

        //Action to be tested
        tGameState.placeTile(t,p1Name);
        //Action to be tested against
        gameStateCopy.getGameboard().recordTile(t);
        gameStateCopy.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().removeTile(t);

        assertEquals(gameStateCopy, tGameState);
    }

    @Test
    void testPlayTileMerge(){

    }

    @Test void testPlayTileFounding(){

    }

    @Test void testPlayTileAddTo(){

    }

    @Test
    void gameCanEndWithSize(){
        //code to add 41 tiles to a corporation
        //size cannot be incremented outside the add/remove methods
        int count = 0;
        int row = 0;
        int col = 0;
        Tile t;
        while(count < 41){
            t = new Tile(row, col);
            tGameState.getScoreboard().getCorporations().getCorp("Worldwide").addCorpTile(t);
            if(row < 9){
                row++;
            }else{
                row = 0;
                if(col < 12){
                    col++;
                }else{
                    col = 0;
                }
            }
            count++;
        }
        assertTrue(tGameState.checkIfGameCanEnd());
    }

    @Test
    void gameCanEndWithSafeCorps(){
        //activates and sets all corporations to safe
        for(String corpName : tGameState.getScoreboard().getCorporations().getCorps().keySet()){
            tGameState.getScoreboard().getCorporations().getCorp(corpName).setSafe(true);
            tGameState.getScoreboard().getCorporations().getCorp(corpName).setStatus(true);
        }
        assertTrue(tGameState.checkIfGameCanEnd());
    }

    @Test
    void gameCanEndWithSomeSafeSomeInnactive(){
        List<String> corpsToSetSafe = new ArrayList<>(tGameState.getScoreboard().getCorporations().getCorps().keySet());
        int counter = 0;
        String tCName;
        while(counter < 4){
            tCName = corpsToSetSafe.get(counter);
            tGameState.getScoreboard().getCorporations().getCorp(tCName).setStatus(true);
            tGameState.getScoreboard().getCorporations().getCorp(tCName).setSafe(true);
            counter++;
        }
        assertTrue(tGameState.checkIfGameCanEnd());
    }

//    @Test
//    void endGameReturnsWinnersWhenNoTie(){
//        //set up state where game can end
//        for(String corpName : tGameState.getScoreboard().getCorporations().getCorps().keySet()){
//            tGameState.getScoreboard().getCorporations().getCorp(corpName).setSafe(true);
//            tGameState.getScoreboard().getCorporations().getCorp(corpName).setStatus(true);
//        }
//
//        //will return p1 in 1st and p2 in second... etc
//        tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPWallet().addCash(5000);
//        tGameState.getScoreboard().getPlayers().getPlayerByName(p2Name).getPWallet().addCash(4000);
//        tGameState.getScoreboard().getPlayers().getPlayerByName(p3Name).getPWallet().addCash(3000);
//        tGameState.getScoreboard().getPlayers().getPlayerByName(p4Name).getPWallet().addCash(2000);
//
//        HashMap<String, Integer> places = tGameState.endGame();
//        assertAll("Player places",
//                () -> assertEquals(1,places.get(p1Name)),
//                () -> assertEquals(2, places.get(p2Name)),
//                () -> assertEquals(3, places.get(p3Name)),
//                () -> assertEquals(4, places.get(p4Name))
//                );
//    }

    @Test
    void gameCannotEndIfConditionIsNotMet(){
        assertFalse(tGameState.checkIfGameCanEnd());
    }

    @Test
    void canDrawTileToPlayer(){
        //THE PREVENTION FOR A PLAYER HAVING OVER 6 TILES IS IN UIController AND IS NOT TESTED HERE
        int initalPHandSize = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().size();
        tGameState.drawTileToPlayer(p1Name);
        int newPHandSize = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().size();
        assertEquals(initalPHandSize + 1, newPHandSize);
    }

    @Test
    void canDrawTileToPlayerAtAnyHandSize(){
        int initalPHandSize = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().size();
        //remove all tiles that were initialized to the players hand
        for(int i = initalPHandSize - 1 ; i >= 0 ; i--){
            tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().remove(i);
        }
        //add tiles until the initial hand size is achieved
        for(int i = 0 ; i < initalPHandSize ; i++){
            tGameState.drawTileToPlayer(p1Name);
        }
        int newPHandSize = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().size();
        assertEquals(newPHandSize, initalPHandSize);
    }

    @Test
    void canRemoveTilesFromPlayerHand(){
        Tile t = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().get(0);
        tGameState.removeTileFromPlayer(p1Name,t);
        ArrayList<Tile> pHand = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles();

        assertFalse(pHand.contains(t));
    }

    @Test
    void cannotRemoveTileThatIsNotInPlayersHand(){
        Tile t = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().get(0);
        tGameState.removeTileFromPlayer(p2Name,t);

        ArrayList<Tile> pOneHand = tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles();
        ArrayList<Tile> pTwoHand = tGameState.getScoreboard().getPlayers().getPlayerByName(p2Name).getPHand().getPlayersTiles();

        assertAll("Tile not removed from either player",
                () -> assertTrue(pOneHand.contains(t)),
                () -> assertFalse(pTwoHand.contains(t))
        );
    }

    @Test
    void playersHandNeedsRefresh(){
        //Activate tiles on the board
        Tile tone = new Tile(0,1);
        Tile ttwo = new Tile(1,0);
        Tile tthree = new Tile(0,2);
        Tile tfour = new Tile(2,0);
        tGameState.getGameboard().recordTile(tone);
        tGameState.getGameboard().recordTile(ttwo);
        tGameState.getGameboard().getTile(0,1).setCorp("Worldwide");
        tGameState.getGameboard().getTile(0,2).setCorp("Worldwide");
        tGameState.getGameboard().getTile(1,0).setCorp("Sackson");
        tGameState.getGameboard().getTile(2,0).setCorp("Sackson");
        tGameState.getScoreboard().getCorporations().getCorp("Worldwide").setSafe(true);
        tGameState.getScoreboard().getCorporations().getCorp("Worldwide").setStatus(true);
        tGameState.getScoreboard().getCorporations().getCorp("Sackson").setSafe(true);
        tGameState.getScoreboard().getCorporations().getCorp("Sackson").setStatus(true);
        //remove tiles from players hand
        tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().remove(0);
        tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().remove(0);
        //add bad tiles (2) to players hand

        Tile removeTone = new Tile(0,0);
        Tile removeTtwo = new Tile(1,1);

        tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().add(removeTone);
        tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().add(removeTtwo);
        //test method
        tGameState.checkPlayerHandForRefresh(p1Name);
        assertAll("Both bad tiles should be removed from player hand",
                ()-> assertFalse(tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().contains(removeTone)),
                ()-> assertFalse(tGameState.getScoreboard().getPlayers().getPlayerByName(p1Name).getPHand().getPlayersTiles().contains(removeTtwo))
        );
    }

    @Test
    void testSaveGamestate(){
        Gameboard testGboard = new Gameboard();
        Scoreboard testSboard = new Scoreboard(2);
        tGameState.setGameboard(testGboard);
        tGameState.setScoreboard(testSboard);
        tGameState.saveGameState();
        assertNotNull("savedGameB");
        assertNotNull("savedScoreB");
    }
}
