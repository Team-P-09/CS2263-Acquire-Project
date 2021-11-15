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
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile testTile = new Tile(1, 1);

    @BeforeEach
    void setUp(){
        testTile.setRow(1);
        testTile.setCol(1);
        testTile.setCorp(null);
    }

    @Test
    void testGetLocation(){
        assertEquals("1, 1", testTile.getLocation(), "Should return string of coordinates");
    }

    @Test
    void testSetRow(){
        testTile.setRow(3);
        assertTrue(testTile.getRow() == 3);

        testTile.setRow(-1);
        assertTrue(testTile.getRow() != -1);

        testTile.setRow(10);
        assertTrue(testTile.getRow() != 10);
    }

    @Test
    void testSetCol(){
        testTile.setCol(10);
        assertTrue(testTile.getCol() == 10);

        testTile.setCol(-1);
        assertTrue(testTile.getCol() != -1);

        testTile.setCol(13);
        assertTrue(testTile.getCol() != 13);
    }

    @Test
    void testSetCorp(){
        testTile.setCorp("American");
        assertEquals( "American", testTile.getCorp());
        testTile.setCorp("not a corp");
        assertEquals( "not a corp", testTile.getCorp());
    }

}
