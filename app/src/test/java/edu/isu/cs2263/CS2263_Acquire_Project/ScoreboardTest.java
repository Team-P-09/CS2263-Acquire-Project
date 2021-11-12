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

    @BeforeEach
    void setUp(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};

        Scoreboard s = new Scoreboard();

        //Corporations tcorps = new Corporations(corpNames);

        Tile tileA = new Tile('a', '1', null);
        Tile tileB = new Tile('a', '2', null);
        Tile tileC = new Tile('b', '3', null);
        Tile tileD = new Tile('b', '2', null);

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

    @Test void testinitBuy(){

    }

    @Test void testinitMerge(){

    }

    @Test void testFindDomCorp(){
        //MAYBE PUT THIS IN CORPORATIONS???
    }

    @Test void testinitPlayers(){
        //THIS WILL BE MOVED TO PLAYERS OBJECT
    }

    @Test void testGetWinners(){

    }
}
