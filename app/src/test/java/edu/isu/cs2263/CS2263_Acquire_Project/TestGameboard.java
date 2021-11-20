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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameboard {

    Gameboard testGameboard;
    Tile testTile;


    @BeforeEach
    void setUp(){
        testGameboard = new Gameboard();
        testTile = new Tile(2, 3);
        testTile.activateTile();
        testGameboard.recordTile(testTile);
    }

    @Test
    void testRecordTileActivation(){
        int row = testTile.getRow();
        int col = testTile.getCol();
        testGameboard.recordTile(testTile);
        assertNotNull(testGameboard.getTile(row, col).isStatus());
    }

    @Test
    void testGetTile(){
        Integer row = 2;
        Integer col = 3;
        assertTrue(testGameboard.getTile(row, col).isStatus());
    }

    @Test
    void testGetActionAndTilesReturnsFounding(){
        Tile newT = new Tile(2,4);
        newT.activateTile();
        String action = new ArrayList<>(testGameboard.getActionAndTiles(newT).keySet()).get(0);
        assertEquals("Founding Tile", action);
    }

    @Test
    void testGetActionAndTilesReturnsMerge(){
        //tile set up for the first corp
        Tile corpATone = new Tile(5,5);
        Tile corpATtwo = new Tile(5,4);
        corpATone.activateTile();
        corpATtwo.activateTile();
        testGameboard.recordTile(corpATone);
        testGameboard.recordTile(corpATtwo);
        testGameboard.getTile(5,4).setCorp("Worldwide");
        testGameboard.getTile(5,5).setCorp("Worldwide");

        //tile set up for the second corp
        Tile corpBTone = new Tile(5,7);
        Tile corpBTtwo = new Tile(5,8);
        corpATone.activateTile();
        corpATtwo.activateTile();
        testGameboard.recordTile(corpBTone);
        testGameboard.recordTile(corpBTtwo);
        testGameboard.getTile(5,7).setCorp("Tower");
        testGameboard.getTile(5,8).setCorp("Tower");

        //tile for merge
        Tile mergeTile = new Tile(5,6);
        mergeTile.activateTile();
        String action = new ArrayList<>(testGameboard.getActionAndTiles(mergeTile).keySet()).get(0);
        assertEquals("Merge",action);
    }

    @Test
    void testGetActionAndTilesReturnsNothing(){
        Tile newT = new Tile(1,0);
        newT.activateTile();
        String action = new ArrayList<>(testGameboard.getActionAndTiles(newT).keySet()).get(0);
        assertEquals("Nothing", action);
    }

    @Test
    void testGetActionAndTilesReturnsAddToCorp(){
        Tile corpATone = new Tile(5,5);
        Tile corpATtwo = new Tile(5,4);
        corpATone.activateTile();
        corpATtwo.activateTile();
        testGameboard.recordTile(corpATone);
        testGameboard.recordTile(corpATtwo);
        testGameboard.getTile(5,4).setCorp("Worldwide");
        testGameboard.getTile(5,5).setCorp("Worldwide");

        Tile newT = new Tile(5,6);
        newT.activateTile();

        String action = new ArrayList<>(testGameboard.getActionAndTiles(newT).keySet()).get(0);
        assertEquals("Add to Corp", action);
    }

    @Test
    void testGetAdjacentTilesGetsOnlyGetsAdjacent(){
        Tile corpATone = new Tile(5,5);
        Tile corpATtwo = new Tile(5,7);
        corpATone.activateTile();
        corpATtwo.activateTile();
        testGameboard.recordTile(corpATone);
        testGameboard.recordTile(corpATtwo);

        Tile tileForAdj = new Tile(5,6);
        tileForAdj.activateTile();
        List<Tile> adjTiles = testGameboard.getAdjacentTiles(tileForAdj);
        assertEquals(2, adjTiles.size());
    }

    @Test
    void testAdjacentTilesDFSWorks(){
        Tile corpATone = new Tile(5,5);
        Tile corpATtwo = new Tile(5,7);
        Tile corpATthree = new Tile(5,8);
        corpATone.activateTile();
        corpATtwo.activateTile();
        corpATthree.activateTile();
        testGameboard.recordTile(corpATone);
        testGameboard.recordTile(corpATtwo);
        testGameboard.recordTile(corpATthree);

        Tile tileForAdj = new Tile(5,6);
        tileForAdj.activateTile();
        List<Tile> adjTiles = testGameboard.getAdjacentTiles(tileForAdj);
        assertEquals(3, adjTiles.size());
    }

    @Test
    void testAdjacentTilesStopsOnCorpTile(){
        Tile corpATone = new Tile(5,5);
        Tile corpATtwo = new Tile(5,7);
        Tile corpATthree = new Tile(5,8);
        corpATone.activateTile();
        corpATtwo.activateTile();
        corpATthree.activateTile();
        testGameboard.recordTile(corpATone);
        testGameboard.recordTile(corpATtwo);
        testGameboard.recordTile(corpATthree);
        testGameboard.getTile(5,7).setCorp("Worldwide");

        Tile tileForAdj = new Tile(5,6);
        tileForAdj.activateTile();
        List<Tile> adjTiles = testGameboard.getAdjacentTiles(tileForAdj);
        assertEquals(2, adjTiles.size());
    }

    @Test
    void getAdjCorpNames(){
        //tile set up for the first corp
        Tile corpATone = new Tile(5,5);
        Tile corpATtwo = new Tile(5,4);
        corpATone.activateTile();
        corpATtwo.activateTile();
        testGameboard.recordTile(corpATone);
        testGameboard.recordTile(corpATtwo);
        testGameboard.getTile(5,4).setCorp("Worldwide");
        testGameboard.getTile(5,5).setCorp("Worldwide");

        //tile set up for the second corp
        Tile corpBTone = new Tile(5,7);
        Tile corpBTtwo = new Tile(5,8);
        corpATone.activateTile();
        corpATtwo.activateTile();
        testGameboard.recordTile(corpBTone);
        testGameboard.recordTile(corpBTtwo);
        testGameboard.getTile(5,7).setCorp("Tower");
        testGameboard.getTile(5,8).setCorp("Tower");

        Tile tileForAdj = new Tile(5,6);
        tileForAdj.activateTile();
        List<Tile> adjTiles = testGameboard.getAdjacentTiles(tileForAdj);
        List<String> corpNames = testGameboard.getAdjTileCorpNames(adjTiles);
        assertAll("Names of adjacent corporations",
                () -> assertEquals("Worldwide" ,corpNames.get(1)),
                () -> assertEquals("Tower", corpNames.get(0))
                );
    }
}
