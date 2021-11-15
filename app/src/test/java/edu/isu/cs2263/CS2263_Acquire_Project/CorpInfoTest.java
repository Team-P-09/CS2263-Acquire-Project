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

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CorpInfoTest {
    CorpInfo tcorp;
    HashMap<String, Tile> tHM;

    @BeforeEach
    void setUp(){
        tcorp = new CorpInfo();
        Tile t = new Tile(1, 1);

        HashMap<String, Tile> nT = new HashMap<>();
        nT.put(t.getLocation(), t);

        tcorp.setCorpTiles(nT);


        tHM = tcorp.retrieveTiles();

        //        tcorp.corpTiles.add(t);
        //        ArrayList<Tile> tl = tcorp.retrieveTiles();
    }

    @AfterEach
    void teardown(){
        tcorp = new CorpInfo();
        tHM = new HashMap<>();
    }


    @Test void retrieveTilesTestRemoves(){
        assertTrue(tcorp.getCorpSize() == 0);
    }

    @Test void retrieveTilesTestExtracts(){
        assertTrue(tHM.size() == 1);
    }
}
