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
import static org.junit.jupiter.api.Assertions.*;

public class TestGameboard {

    Gameboard testGameboard;
    Tile testTile;


    @BeforeEach
    public void setUp(){
        Gameboard testGameboard = new Gameboard();
        Tile testTile = new Tile(3, 3);
        testTile.setRow(3);
        testTile.setCol(3);
    }

    @AfterEach
    public void teardown(){
        Gameboard testGameboard = new Gameboard();
        Tile testTile = new Tile(3, 3);
        testTile.setRow(3);
        testTile.setCol(3);
    }

    @Test
    public void testRecordTileActivation(){
        Tile tile = testTile;
        int row = tile.getRow();
        int col = tile.getCol();
        testGameboard.recordTile(tile);
        assertNotNull(testGameboard.gameboard[testTile.getRow()][testTile.getCol()]);
    }

    @Test
    public void testRecordTileGetsMerge(){

    }

    @Test
    public void testRecordTileGetNothing(){

    }

    @Test
    public void testRecordTileGetFounding(){

    }

    @Test
    public void testRecordTileGetAddtoCorp(){

    }
}
