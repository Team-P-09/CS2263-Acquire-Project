package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CorporationsTest {

    @Test void corpsCanMerge(){

    }

    /**
     * Asserts to confirm that the number of corporations initialized are correct
     */
    @Test void initSizeTest(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};
        Corporations tcorp = new Corporations(corpNames);
        assertTrue(tcorp.corps.size() == 7);
    }

    /**
     * Asserts that the names of the corporations are correct
     */
    @Test void initNameCheck(){
        String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};
        Corporations tcorp = new Corporations(corpNames);
        String[] tcorpNames = tcorp.corps.keySet().toArray(new String[tcorp.corps.keySet().size()]);
        boolean checkBool = true;
        for (String str : corpNames){
            checkBool = Arrays.asList(tcorpNames).contains(str);
        }
        assertTrue(checkBool);
    }

    /**
     * Asserts that the stock prices are 100 after initialization
     */
    @Test void initStockPriceCheck(){

    }

    /**
     * Assert that the number of available stocks are 25 after initialization
     */
    @Test void initAvailStockCheck(){

    }

    /**
     * Assert that the status of the Stocks are all false
     */
    @Test void initStatusCheck(){

    }

    @Test void canGetCorpFromCorps(){

    }

}
