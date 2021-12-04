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

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.*;

import static edu.isu.cs2263.CS2263_Acquire_Project.Scoreboard.loadScoreboard;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestScoreboard extends ApplicationTest{

    private HashMap<String, Integer[]> testDisplayInfo;
    private Scoreboard s;
    private String p1Name;
    private String p2Name;
    private Tile tileA;
    private Tile tileB;
    private Tile tileC;
    private Tile tileD;
    private Scene scene;

    @BeforeEach
    void setUp(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};

        s = new Scoreboard(2);

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

        p1Name = "Player 1";
        p2Name = "Player 2";
    }

//    @BeforeEach
//    @Override
//    public void start(Stage stage) throws Exception {
//        Circle circ = new Circle(40, 40, 30);
//        Group root = new Group(circ);
//        scene = new Scene(root, 400, 300);
//
//        stage.setTitle("My JavaFX Application");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//
//    @Test
//    void testinitBuyAddsStock(){
//        s.initBuy(p1Name, "Worldwide",3);
//        assertTrue(s.getCorporations().getCorp("Worldwide").getAvailableStocks() == 22);
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
    void testJsonFileCreated() throws IOException {
        String jsonFile = "savedScoreboard";
        assertNotNull(s.saveScoreboard(jsonFile,s)) ;
    }

    @Test
    void testForNullSaveFile() throws IOException {
        String nullFile = "";
        assertTrue(s.saveScoreboard(nullFile,s) == null);
    }

    @Test
    void testLoadScoreboard(){
        String jsonFile = "savedScoreboard";
        assertNotNull(loadScoreboard(jsonFile));
    }
}
