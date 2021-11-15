package edu.isu.cs2263.CS2263_Acquire_Project;

import javafx.application.Application;
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
    String p1Name;
    String p2Name;

    @BeforeEach
    void setUp(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};

        s = new Scoreboard(2);

        //Corporations tcorps = new Corporations(corpNames);

        Tile tileA = new Tile(1, 1);
        Tile tileB = new Tile(1, 2);
        Tile tileC = new Tile(5, 3);
        Tile tileD = new Tile(5, 2);

        s.getCorporations().addTileToCorp(corpNames[0], tileA);
        s.getCorporations().addTileToCorp(corpNames[0], tileB);
        s.getCorporations().addTileToCorp(corpNames[1], tileC);
        s.getCorporations().addTileToCorp(corpNames[1], tileD);


        //System.out.println(s.corporations.getCorp(corpNames[0]).getCorpSize());

        testDisplayInfo =  s.displayCorpInfo();

        p1Name = "Player 1";
        p2Name = "Player 2";
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

    @Test void testinitBuyAddsStock(){
        s.initBuy(p1Name, "Worldwide");
        assertTrue(s.getCorporations().getCorp("Worldwide").getAvailableStocks() == 12);
    }

    @Test void testinitBuyRemovesCash(){
        s.initBuy(p1Name,"Worldwide");
        Integer pcash = s.getPlayers().getPlayerByName(p1Name).getPWallet().getCash();
        System.out.println(pcash);
        assertTrue(pcash == 5700);
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
}
