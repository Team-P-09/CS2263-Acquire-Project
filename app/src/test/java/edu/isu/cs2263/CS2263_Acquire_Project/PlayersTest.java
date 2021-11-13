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
