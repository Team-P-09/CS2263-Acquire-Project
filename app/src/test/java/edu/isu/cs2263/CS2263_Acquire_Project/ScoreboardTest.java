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

import javafx.application.Application;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {

    private HashMap<String, Integer[]> testDisplayInfo;
    Scoreboard s;
    String p1Name;
    String p2Name;
    Tile tileA;
    Tile tileB;
    Tile tileC;
    Tile tileD;

    @BeforeEach
    void setUp(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};

        s = new Scoreboard(2);

        //Corporations tcorps = new Corporations(corpNames);

        tileA = new Tile(1, 1);
        tileB = new Tile(1, 2);
        tileC = new Tile(5, 3);
        tileD = new Tile(5, 2);

        tileA.activateTile();
        tileB.activateTile();
        tileC.activateTile();
        tileD.activateTile();

        tileA.setCorp(corpNames[0]);
        tileB.setCorp(corpNames[0]);
        tileC.setCorp(corpNames[1]);
        tileD.setCorp(corpNames[1]);

        s.getCorporations().addTileToCorp(corpNames[0], tileA);
        s.getCorporations().addTileToCorp(corpNames[0], tileB);
        s.getCorporations().addTileToCorp(corpNames[1], tileC);
        s.getCorporations().addTileToCorp(corpNames[1], tileD);


        //System.out.println(s.corporations.getCorp(corpNames[0]).getCorpSize());

//        testDisplayInfo =  s.displayCorpInfo();

        p1Name = "Player 1";
        p2Name = "Player 2";
    }

//    @Test void testDisplayDataSize(){
//        assertTrue(testDisplayInfo.size() == 7);
//    }
//
//    @Test void testDisplayDataFirstVal(){
//        assertTrue(testDisplayInfo.get("Worldwide")[0] == 2);
//    }

//    @Test void testinitBuyAddsStock(){
//        s.initBuy(p1Name, "Worldwide");
//        assertTrue(s.getCorporations().getCorp("Worldwide").getAvailableStocks() == 12);
//    }
//
//    @Test void testinitBuyRemovesCash(){
//        s.initBuy(p1Name,"Worldwide");
//        Integer pcash = s.getPlayers().getPlayerByName(p1Name).getPWallet().getCash();
//        System.out.println(pcash);
//        assertTrue(pcash == 5700);
//    }

    @Test
    void testGetAvailableCorps(){
        assertTrue(s.getAvailableCorps().size() == 7);
    }

    @Test void testinitPlayers(){
        assertTrue(s.getPlayers().getActivePlayers().size() == 2);
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
        Tile newTtwo = new Tile(1,4);
        Tile newTthree = new Tile(0,3);
        Tile newTfour = new Tile(2,3);
        newTone.activateTile();
        List<Tile> tList = new ArrayList<>();
        tList.add(newTone);
//        tList.add(newTtwo);
//        tList.add(newTthree);
//        tList.add(newTfour);
        tList.add(tileB);

        for(Tile t : tList){
            System.out.println(t.isStatus());
            System.out.println(t.getCorp());
        }

        s.initCorpTileAdd(tList);
        assertTrue(s.getCorporations().getCorp("Worldwide").getCorpSize() == 3);
    }

    @Test
    void testGetPlayerScore(){
        s.getPlayers().getPlayerByName(p1Name).getPWallet().addStock("Worldwide", 5);
        Integer wwStockValue = s.getCorporations().getCorp("Worldwide").getStockPrice();
        assertTrue(s.getPlayerScore(p1Name) == 6000 + 5*wwStockValue);
    }
}
