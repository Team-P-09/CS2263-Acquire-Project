package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    @BeforeEach
    void setUp(){
        Tile testTile = new Tile(1, 1, null);
    }

    @Test
    void testGetLocation(){
        assertEquals("1, 1", testTile.getLocation(), "Should return string of coordinates");
    }

    @Test
    void testSetRow(){
        assertEquals("1, 3", testTile.setRow(3), "Should return string of changed coordinates");
        assertEquals("invalid input", testTile.setRow(-1), "Should return error message");
        assertEquals("invalid input", testTile.setRow(10), "Should return error message");
    }

    @Test
    void testSetCol(){
        assertEquals("1, 3", testTile.setCol(10), "Should return string of changed coordinates");
        assertEquals("invalid input", testTile.setCol(-1), "Should return error message");
        assertEquals("invalid input", testTile.setCol(13), "Should return error message");
    }

    @Test
    void testSetLocation(){
        assertEquals("3, 6", testTile.setLocation(3, 6), "Should return string of coordinates");
        assertEquals("invalid input", testTile.setLocation(-1, 6), "Should return error message");
        assertEquals("invalid input", testTile.setLocation(1, 13), "Should return error message");
    }

    @Test
    void testSetCorp(){
        assertEquals("Festival", testTile.setCorp(1), "Should return name of corporation");
        assertEquals("invalid input", testTile.setCorp(-1, "Should return error message");
        assertEquals("invalid input", testTile.setCorp(8), "Should return error message");
    }


}
