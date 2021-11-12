package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CorporationsTest {

    @Test void corpsCanMerge(){
        String[] corpNames = new String[]{"tcorpA", "tcorpB", "tcorpC"};

        String domCorpName = corpNames[0];
        String[] subCorpNames = Arrays.copyOfRange(corpNames, 1,corpNames.length);

        Corporations tcorps = new Corporations(corpNames);

        Tile tileA = new Tile('a', '1');
        Tile tileB = new Tile('a', '2');
        Tile tileC = new Tile('b', '3');
        Tile tileD = new Tile('b', '2');

        tcorps.addTileToCorp(domCorpName, tileA);
        tcorps.addTileToCorp(domCorpName, tileB);
        tcorps.addTileToCorp(corpNames[1], tileC);
        tcorps.addTileToCorp(corpNames[2], tileD);

        tcorps.mergeCorps(domCorpName, subCorpNames);

        assertTrue(tcorps.getCorp(domCorpName).getCorpSize() == 4);
    }

    /**
     * Asserts to confirm that the number of corporations initialized are correct
     */
    @Test void initSizeTest(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};
        Corporations tcorp = new Corporations(corpNames);
        assertTrue(tcorp.corps.size() == 7);
    }
}
