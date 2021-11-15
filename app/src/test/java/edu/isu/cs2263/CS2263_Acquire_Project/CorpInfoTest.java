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
    }

    @AfterEach
    void teardown(){
        tcorp = new CorpInfo();
        tHM = new HashMap<>();
    }


    @Test void retrieveTilesTestRemoves(){
        tHM = tcorp.retrieveTiles();
        assertTrue(tcorp.getCorpSize() == 0);
    }

    @Test void retrieveTilesTestExtracts(){
        tHM = tcorp.retrieveTiles();
        assertTrue(tHM.size() == 1);
    }
}
