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

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CorporationsTest {
    ArrayList<String> corpNames;
    String domCorpName;
    ArrayList<String> subCorpNames;
    Corporations tcorps;
    String corp1 = "Worldwide";
    String corp2 = "Imperial";
    String corp3 = "Saxon";
    Tile tileA;
    Tile tileB;
    Tile tileC;
    Tile tileD;

    @BeforeEach
    void setUp(){
        corpNames = new ArrayList<>();
        corpNames.add(corp1);
        corpNames.add(corp2);
        corpNames.add(corp3);

        domCorpName = corpNames.get(0);
        subCorpNames = new ArrayList(corpNames.subList(1, corpNames.size()));

        tcorps = new Corporations(corpNames);

        tileA = new Tile(1, 1);
        tileB = new Tile(1, 2);
        tileC = new Tile(2, 3);
        tileD = new Tile(2, 2);

        tcorps.addTileToCorp(domCorpName, tileA);
        tcorps.addTileToCorp(domCorpName, tileB);
        tcorps.addTileToCorp(corpNames.get(1), tileC);
        tcorps.addTileToCorp(corpNames.get(2), tileD);
    }

    @AfterEach
    void teardown(){
        corpNames = new ArrayList<>();
        domCorpName = "";
        subCorpNames = new ArrayList<>();
        tcorps = new Corporations(corpNames);
    }

    /**
     * Validates the size of the tiles in the dominate corp after merging
     */
    @Test void mergeIncreasesDomSize(){
        tcorps.mergeCorps(domCorpName, subCorpNames);
        assertTrue(tcorps.getCorp(domCorpName).getCorpSize() == 4);
    }

    @Test void mergeDecreasesSubSize(){
        tcorps.mergeCorps(domCorpName, subCorpNames);
        assertTrue(tcorps.getCorp(subCorpNames.get(1)).getCorpSize() == 0);
    }

    @Test
    void mergeUpdatesTileCorp(){
        tcorps.mergeCorps(domCorpName, subCorpNames);
        assertTrue(tcorps.getTilesCorp(tileD).equals(domCorpName));
    }

    @Test
    void mergeRemovesTileFromCorp(){
        tcorps.mergeCorps(domCorpName, subCorpNames);
        assertFalse(tcorps.getTilesCorp(tileD).equals(subCorpNames.get(1)));
    }

    /**
     * Asserts to confirm that the number of corporations initialized are correct
     */
    @Test void initSizeTest(){
        assertTrue(tcorps.getCorps().size() == 3);
    }

    @Test
    void testGetTilesCorp(){
        Tile t = new Tile(2,8);
        tcorps.addTileToCorp(corp1, t);
        assertTrue(tcorps.getTilesCorp(t) == corp1);
    }

    @Test
    void corpAddsTile(){
        Tile t = new Tile(2,8);
        Integer oldCorpSize = tcorps.getCorp(corp1).getCorpSize();
        tcorps.addTileToCorp(corp1, t);
        assertTrue(tcorps.getCorp(corp1).getCorpSize() == oldCorpSize +1);
    }

    @Test
    void getBonusMajorityReturnsCorrectValue(){
        Integer bonusVal = tcorps.getBonus(corp1, "Majority");
        assertTrue(bonusVal == 3000);
    }

    @Test
    void getBonusMinorityReturnsCorrectValue(){
        Integer bonusVal = tcorps.getBonus(corp1, "Minority");
        assertTrue(bonusVal == 1500);
    }

    @Test
    void testStockValueCorrectlyIncrements(){
        tcorps.setStockValue(corp1);
        assertTrue(tcorps.getCorp(corp1).getStockPrice() == 300);
    }
}
