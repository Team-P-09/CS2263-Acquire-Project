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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ScoreboardTest {

    private HashMap<String, Integer[]> testDisplayInfo;
    Scoreboard s;

    @BeforeEach
    void setUp(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};

        s = new Scoreboard(2);

        //Corporations tcorps = new Corporations(corpNames);

        Tile tileA = new Tile(1, 1);
        Tile tileB = new Tile(1, 2);
        Tile tileC = new Tile(2, 3);
        Tile tileD = new Tile(2, 2);

        s.corporations.addTileToCorp(corpNames[0], tileA);
        s.corporations.addTileToCorp(corpNames[0], tileB);
        s.corporations.addTileToCorp(corpNames[1], tileC);
        s.corporations.addTileToCorp(corpNames[2], tileD);

        //System.out.println(s.corporations.getCorp(corpNames[0]).getCorpSize());

        testDisplayInfo =  s.displayCorpInfo();
    }

    @AfterEach
    void tearDown(){
        testDisplayInfo = new HashMap<>();
    }

    @Test void testDisplayDataSize(){
        assertTrue(testDisplayInfo.size() == 7);
    }

    @Test void testDisplayDataFirstVal(){
        assertTrue(testDisplayInfo.get("Worldwide")[0] == 2);
    }

    @Test void testinitSell(){

    }

    @Test void testinitBuyTakesStock(){
        s.initBuy("Player1", "Worldwide");
        assertTrue(s.getCorporations().getCorp("Worldwide").getAvailableStocks() == 12);
    }

    @Test void testinitBuyAddsCash(){

    }



    @Test void testinitMergeSingleDom(){

    }

//    @Test void testFindDomCorp(){
//
//    }

    @Test void testinitPlayers(){
        //THIS WILL BE MOVED TO PLAYERS OBJECT
    }

    @Test void testGetWinners(){

    }
}
