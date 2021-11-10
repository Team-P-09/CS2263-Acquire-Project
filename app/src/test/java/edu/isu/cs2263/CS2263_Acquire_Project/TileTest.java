package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    @BeforeEach
    void setUp(){
        Tile testTile = new Tile(1, 1, 0);
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
        testTile.setCorp(1);
        assertTrue(testTile.getCorp() == 1);
        testTile.setCorp(-1);
        assertTrue(testTile.getCorp() != -1);
        testTile.setCorp(8);
        assertTrue(testTile.getCorp() != 8);
    }


}
