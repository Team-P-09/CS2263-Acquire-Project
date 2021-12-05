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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestScoreboard extends ApplicationTest{

    private HashMap<String, Integer[]> testDisplayInfo;
    private Scoreboard s;
    private String p1Name;
    private String p2Name;
    private String p3Name;
    private Tile tileA;
    private Tile tileB;
    private Tile tileC;
    private Tile tileD;
    private Tile tileE;
    private Tile tileF;

    @BeforeEach
    void setUp(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};

        s = new Scoreboard(3);

        tileA = new Tile(1, 1);
        tileB = new Tile(1, 2);
        tileC = new Tile(5, 3);
        tileD = new Tile(5, 2);
        tileE = new Tile(1, 4);
        tileF = new Tile(1, 5);

        tileA.activateTile();
        tileB.activateTile();
        tileC.activateTile();
        tileD.activateTile();
        tileE.activateTile();
        tileF.activateTile();

        tileA.setCorp(corpNames[0]);
        tileB.setCorp(corpNames[0]);
        tileC.setCorp(corpNames[1]);
        tileD.setCorp(corpNames[1]);
        tileE.setCorp(corpNames[2]);
        tileF.setCorp(corpNames[2]);

        s.getCorporations().addTileToCorp(corpNames[0], tileA);
        s.getCorporations().addTileToCorp(corpNames[0], tileB);
        s.getCorporations().addTileToCorp(corpNames[1], tileC);
        s.getCorporations().addTileToCorp(corpNames[1], tileD);
        s.getCorporations().addTileToCorp(corpNames[2], tileE);
        s.getCorporations().addTileToCorp(corpNames[2], tileF);

        s.getCorporations().getCorp(corpNames[0]).setStatus(true);
        s.getCorporations().getCorp(corpNames[1]).setStatus(true);
        s.getCorporations().getCorp(corpNames[2]).setStatus(true);

        p1Name = "Player 1";
        p2Name = "Player 2";
        p3Name = "Player 3";
    }

    @Test
    void testGetAvailableCorps(){
        assertTrue(s.getNonActiveCorps().size() == 4);
    }

    @Test void testinitPlayers(){
        assertTrue(s.getPlayers().getActivePlayers().size() == 3);
    }

    @Test void testGetWinners(){
        s.getPlayers().getPlayerByName(p1Name).getPWallet().addStock("Worldwide", 5);
        s.getPlayers().getPlayerByName(p2Name).getPWallet().addStock("Sackson", 2);
        HashMap<String, Integer> pStandings = s.getWinners();
        assertTrue(pStandings.get(p1Name) == 1);
    }

    @Test
    void testInitCorpTileAdd(){
        Tile newTone = new Tile(1,3);
        newTone.activateTile();
        List<Tile> tList = new ArrayList<>();
        tList.add(newTone);
        tList.add(tileB);

        //only adds one tile, the corporation is initialized with 2 tiles
        s.initCorpTileAdd(tList);
        assertTrue(s.getCorporations().getCorp("Worldwide").getCorpSize() == 3);
    }

    @Test
    void testGetPlayerScore(){
        s.getPlayers().getPlayerByName(p1Name).getPWallet().addStock("Worldwide", 5);
        Integer wwStockValue = s.getCorporations().getCorp("Worldwide").getStockPrice();
        assertTrue(s.getPlayerScore(p1Name) == 6000 + 5*wwStockValue);
    }

    @Test
    void testInitFoundingWorksOnStandardFounding(){
        //List<Tile> tiles, String playerName, String corpName
        Tile tileActive = new Tile(9,0);
        tileActive.activateTile();
        Tile tilePlayed = new Tile(8,0);
        tilePlayed.activateTile();
        //other adj tiles
        Tile tileDown = new Tile(8,1);
        Tile tileLeft = new Tile(7,0);

        List<Tile> tList = new ArrayList<>();
        tList.add(tileActive);
        tList.add(tilePlayed);
        tList.add(tileDown);
        tList.add(tileLeft);

        s.initFounding(tList, p1Name, "Imperial");

        assertAll("Corporation has been founded",
                () -> assertTrue(s.getCorporations().getCorp("Imperial").isStatus()),
                () -> assertEquals(2, s.getCorporations().getCorp("Imperial").getCorpSize()),
                () -> assertEquals(1, s.getPlayers().getPlayerByName(p1Name).getPWallet().getStocks().get("Imperial")),
                () -> assertTrue(s.getCorporations().getCorp("Imperial").isHasBeenFounded()),
                () -> assertEquals(24, s.getCorporations().getCorp("Imperial").getAvailableStocks())
                );
    }

    @Test
    void testInitCorpFoundsWhenGreaterThanOneStepAway(){
        //List<Tile> tiles, String playerName, String corpName
        Tile tileActive = new Tile(9,0);
        tileActive.activateTile();
        Tile tilePlayed = new Tile(8,0);
        tilePlayed.activateTile();
        //other adj tiles
        Tile tileDown = new Tile(8,1);
        Tile tileLeft = new Tile(7,0);

        Tile otherTileActive = new Tile(10,0);
        otherTileActive.activateTile();

        List<Tile> tList = new ArrayList<>();
        tList.add(tileActive);
        tList.add(tilePlayed);
        tList.add(tileDown);
        tList.add(tileLeft);
        tList.add(otherTileActive);

        s.getCorporations().getCorp("Imperial").setHasBeenFounded(true);

        s.initFounding(tList, p1Name, "Imperial");

        assertAll("Corporation has been founded",
                () -> assertTrue(s.getCorporations().getCorp("Imperial").isStatus()),
                () -> assertEquals(3, s.getCorporations().getCorp("Imperial").getCorpSize()),
                () -> assertEquals(0, s.getPlayers().getPlayerByName(p1Name).getPWallet().getStocks().get("Imperial")),
                () -> assertEquals(25, s.getCorporations().getCorp("Imperial").getAvailableStocks())
        );
    }

    @Test
    void testGetNonActiveCorps(){
        List<String> nonActiveCorps = s.getNonActiveCorps();
        assertAll("Both founded corps are not available",
                () -> assertFalse(nonActiveCorps.contains("Worldwide")),
                () -> assertFalse(nonActiveCorps.contains("Sackson"))
        );
    }

    @Test
    void testGetBuyableCorps(){
        List<String> buyableCorps = s.getBuyableCorps();
        assertAll("Both founded corps are not available",
                () -> assertTrue(buyableCorps.contains("Worldwide")),
                () -> assertTrue(buyableCorps.contains("Sackson"))
        );
    }

    @Test
    void testAddTilesToCorp(){
        //List<Tile> tList, String corpName
        //List<Tile> tiles, String playerName, String corpName
        Tile tileActive = new Tile(9,0);
        tileActive.activateTile();
        Tile tilePlayed = new Tile(8,0);
        tilePlayed.activateTile();
        //other adj tiles
        Tile tileDown = new Tile(8,1);
        Tile tileLeft = new Tile(7,0);

        Tile otherTileActive = new Tile(10,0);
        otherTileActive.activateTile();

        List<Tile> tList = new ArrayList<>();
        tList.add(tileActive);
        tList.add(tilePlayed);
        tList.add(tileDown);
        tList.add(tileLeft);
        tList.add(otherTileActive);

        Integer newSize = s.getCorporations().getCorp("Worldwide").getCorpSize() + 3;

        s.addUnassignedTilesToCorp(tList, "Worldwide");

        assertEquals(newSize, s.getCorporations().getCorp("Worldwide").getCorpSize());
    }

    @Test
    void testRetrieveTiles(){
        List<Tile> retrievedTiles = s.retrieveTiles(s.getCorporations().getCorp("Worldwide").getCorpTiles());

        assertAll("Correct tiles have been retrieved",
                () -> assertTrue(retrievedTiles.contains(tileA)),
                () -> assertTrue(retrievedTiles.contains(tileB))
                );
    }

    @Test
    void testInitMerge(){
        //List<String> mCorps, String domCorpName, List<String> affectedPlayers
        //Set up played tiles
        Tile tPlayed = new Tile(1,3);
        Tile tUp = new Tile(0,3);
        Tile tDown = new Tile(2,3);
        Tile tLeft = new Tile(1,2);
        Tile tRight = new Tile(1,4);

        tPlayed.activateTile();

        List<Tile> tList = new ArrayList<>();
        tList.add(tPlayed);
        tList.add(tUp);
        tList.add(tDown);
        tList.add(tLeft);
        tList.add(tRight);

        //AddCorps to Players
        s.getPlayers().getPlayerByName(p1Name).getPWallet().addStock("Worldwide", 3);
        s.getPlayers().getPlayerByName(p2Name).getPWallet().addStock("Worldwide", 2);

        List<String> mCorps = s.findCorps(tList);
        String domCorpName = "Festival";
        List<String> affectedPlayers = s.findAffectedPlayers(mCorps);
        mCorps.remove("Festival");

        s.initMerge(mCorps, domCorpName, affectedPlayers);

        //NOTE IN MERGECONTROLLER THE UNASSIGNED TILE(S) WILL ALSO BE ADDED TO THE DOMINATE CORPORATION
        assertAll("Verifies merge correctly distributes bonuses and tiles on non tie",
                //bonuses
                () -> assertEquals(9000, s.getPlayers().getPlayerByName(p1Name).getPWallet().getCash()),
                () -> assertEquals(7500,s.getPlayers().getPlayerByName(p2Name).getPWallet().getCash()),
                //corp sizes
                () -> assertEquals(4, s.getCorporations().getCorp("Festival").getCorpSize()),
                () -> assertEquals(0, s.getCorporations().getCorp("Worldwide").getCorpSize())
                );
    }

    @Test
    void testInitMergeDoesNotGiveBonusesToNonMajorityOrMinorityStockHolders(){
        //List<String> mCorps, String domCorpName, List<String> affectedPlayers
        //Set up played tiles
        Tile tPlayed = new Tile(1,3);
        Tile tUp = new Tile(0,3);
        Tile tDown = new Tile(2,3);
        Tile tLeft = new Tile(1,2);
        Tile tRight = new Tile(1,4);

        tPlayed.activateTile();

        List<Tile> tList = new ArrayList<>();
        tList.add(tPlayed);
        tList.add(tUp);
        tList.add(tDown);
        tList.add(tLeft);
        tList.add(tRight);

        //AddCorps to Players
        s.getPlayers().getPlayerByName(p1Name).getPWallet().addStock("Worldwide", 3);
        s.getPlayers().getPlayerByName(p2Name).getPWallet().addStock("Worldwide", 2);
        s.getPlayers().getPlayerByName(p3Name).getPWallet().addStock("Worldwide", 1);

        List<String> mCorps = s.findCorps(tList);
        String domCorpName = "Festival";
        List<String> affectedPlayers = s.findAffectedPlayers(mCorps);
        mCorps.remove("Festival");

        s.initMerge(mCorps, domCorpName, affectedPlayers);

        assertEquals(6000, s.getPlayers().getPlayerByName(p3Name).getPWallet().getCash());
    }

    @Test
    void testInitMergeDoesNotGiveBonusesWhenThereIsNoMinority(){
        //List<String> mCorps, String domCorpName, List<String> affectedPlayers
        //Set up played tiles
        Tile tPlayed = new Tile(1,3);
        Tile tUp = new Tile(0,3);
        Tile tDown = new Tile(2,3);
        Tile tLeft = new Tile(1,2);
        Tile tRight = new Tile(1,4);

        tPlayed.activateTile();

        List<Tile> tList = new ArrayList<>();
        tList.add(tPlayed);
        tList.add(tUp);
        tList.add(tDown);
        tList.add(tLeft);
        tList.add(tRight);

        //AddCorps to Players
        s.getPlayers().getPlayerByName(p1Name).getPWallet().addStock("Worldwide", 3);
        s.getPlayers().getPlayerByName(p2Name).getPWallet().addStock("Worldwide", 0);
        s.getPlayers().getPlayerByName(p3Name).getPWallet().addStock("Worldwide", 0);

        List<String> mCorps = s.findCorps(tList);
        String domCorpName = "Festival";
        List<String> affectedPlayers = s.findAffectedPlayers(mCorps);
        mCorps.remove("Festival");

        s.initMerge(mCorps, domCorpName, affectedPlayers);

        assertAll("Neither p2 or p3 got bonuses",
                () -> assertEquals(6000, s.getPlayers().getPlayerByName(p2Name).getPWallet().getCash()),
                () -> assertEquals(6000, s.getPlayers().getPlayerByName(p3Name).getPWallet().getCash())
                );
    }

    @Test
    void testInitBuyCorrectlyRemovesCashAddsStock(){
        Integer buyQty = 3;
        Integer stockValue = s.getCorporations().getCorp("Imperial").getStockPrice();
        Integer expectedWalletValue = 6000 - stockValue*buyQty;
        s.initBuy(p1Name, "Imperial", buyQty);
        assertAll("Cash removed, stocks added",
                () -> assertEquals(3, s.getPlayers().getPlayerByName(p1Name).getPWallet().getStocks().get("Imperial")),
                () -> assertEquals(expectedWalletValue, s.getPlayers().getPlayerByName(p1Name).getPWallet().getCash())
                );
    }

    @Test
    void testBuyLimitStockCashLimitsPurchase(){
        s.getPlayers().getPlayerByName(p1Name).getPWallet().removeCash(5800);
        Integer expectedBuyQty = s.getPlayers().getPlayerByName(p1Name).getPWallet().getCash()/s.getCorporations().getCorp("Worldwide").getStockPrice();
        Integer actualBuyQty = s.maxBuy(p1Name,"Worldwide",3);
        assertEquals(expectedBuyQty, actualBuyQty);
    }

    @Test
    void testBuyLimitReturnsLimitWhenBoundingConstraint(){
        Integer buyLimit = 3;
        s.getPlayers().getPlayerByName(p1Name).getPWallet().removeCash(5500);
        Integer expectedBuyQty = buyLimit;
        Integer actualBuyQty = s.maxBuy(p1Name,"Worldwide",buyLimit);
        assertEquals(expectedBuyQty, actualBuyQty);
    }

    @Test
    void testBuyLimitWontOverSellStock(){
        Integer buyLimit = 3;
        s.getCorporations().getCorp("Worldwide").removeCorpStock(23);
        Integer expectedBuyQty = s.getCorporations().getCorp("Worldwide").getAvailableStocks();
        Integer actualBuyQty = s.maxBuy(p1Name,"Worldwide",buyLimit);
        assertEquals(expectedBuyQty, actualBuyQty);
    }

    @Test
    void testSafeCorpsAreRemoved(){
        s.getCorporations().getCorp("Worldwide").setSafe(true);
        List<String> corps = new ArrayList<>();
        corps.add("Worldwide");
        corps.add("Festival");
        corps.add("Sackson");
        List<String> unsafecorps = s.removeSafeCorps(corps);
        assertFalse(unsafecorps.contains("Worldwide"));
    }
}
