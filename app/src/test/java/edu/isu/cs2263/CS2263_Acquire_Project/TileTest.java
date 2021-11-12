package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile testTile = new Tile(1, 1, null);

    @BeforeEach
    void setUp(){
        testTile.setRow(1);
        testTile.setCol(1);
        testTile.setCorp(null);
    }

    @Test
    void testGetLocation(){
        assertEquals("1, 1", testTile.getLocation(), "Should return string of coordinates");
    }

    @Test
    void testSetRow(){
        testTile.setRow(3);
        assertTrue(testTile.getRow() == 3);

        testTile.setRow(-1);
        assertTrue(testTile.getRow() != -1);

        testTile.setRow(10);
        assertTrue(testTile.getRow() != 10);
    }

    @Test
    void testSetCol(){
        testTile.setCol(10);
        assertTrue(testTile.getCol() == 10);

        testTile.setCol(-1);
        assertTrue(testTile.getCol() != -1);

        testTile.setCol(13);
        assertTrue(testTile.getCol() != 13);
    }

    @Test
    void testSetCorp(){
        testTile.setCorp("American");
        assertEquals( "American", testTile.getCorp());
        testTile.setCorp("not a corp");
        assertEquals( "not a corp", testTile.getCorp());
    }


}
