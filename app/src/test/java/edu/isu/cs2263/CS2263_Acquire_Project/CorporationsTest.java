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

    @BeforeEach
    void setUp(){
        corpNames = new ArrayList<>();
        corpNames.add("tcorpA");
        corpNames.add("tcorpB");
        corpNames.add("tcorpC");

        domCorpName = corpNames.get(0);
        subCorpNames = new ArrayList(corpNames.subList(1, corpNames.size()));

        tcorps = new Corporations(corpNames);

        Tile tileA = new Tile(1, 1);
        Tile tileB = new Tile(1, 2);
        Tile tileC = new Tile(2, 3);
        Tile tileD = new Tile(2, 2);

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

        assertTrue(tcorps.getCorp(subCorpNames.get(0)).getCorpSize() == 0);
    }

    /**
     * Asserts to confirm that the number of corporations initialized are correct
     */
    @Test void initSizeTest(){
//        corpNames;
//        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};
//        Corporations tcorp = new Corporations(corpNames);
        assertTrue(tcorps.getCorps().size() == 3);
    }

    @Test
    void checkValidation(){

    }
}
