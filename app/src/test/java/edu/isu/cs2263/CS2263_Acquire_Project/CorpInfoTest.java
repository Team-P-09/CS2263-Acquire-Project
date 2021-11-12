package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CorpInfoTest {

    @Test void retrieveTilesTestRemoves(){
        CorpInfo tcorp = new CorpInfo();
        Tile t = new Tile('a', '1');
        tcorp.corpTiles.add(t);
        ArrayList<Tile> tl = tcorp.retrieveTiles();
        assertTrue(tcorp.getCorpSize() == 0);
    }

    @Test void retrieveTilesTestExtracts(){
        CorpInfo tcorp = new CorpInfo();
        Tile t = new Tile('a', '1');
        tcorp.corpTiles.add(t);
        ArrayList<Tile> tl = tcorp.retrieveTiles();
        assertTrue(tl.size() == 1);
    }
}
