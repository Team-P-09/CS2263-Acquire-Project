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

import java.util.ArrayList;
import java.util.Arrays;

public class PlayersTest {
    ArrayList<String> corpNames;
    Integer pNumb;
    ArrayList<PlayerInfo> tPlayers;

    @BeforeEach
    void setUp(){
        corpNames = new ArrayList<>(Arrays.asList("Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"));
        pNumb = 4;

    }

    @Test
    void setup(){
        //  Player player = new Player("P1", , );
    }

    @Test
    void testPlayTile(){

    }
    @Test
    void testStartTurn(){

    }
    @Test
    void testEndTurn(){

    }
    @Test
    void testDoTurn(){

    }
    @Test
    void testMerge(){

    }
    @Test
    void testOrderBuy(){

    }
    @Test
    void testOrderSell(){

    }
    @Test
    void testGetScore(){

    }

    @Test
    void testInitPlayers(){
        Players tPlayers = new Players(pNumb, corpNames);
        int tstackSize = 9*12 - 6*pNumb;
        assertTrue(tPlayers.getTStack().getTileStack().size() == tstackSize);
    }
}
