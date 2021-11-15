package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TileTest {

    Tile testTile = new Tile(1, 1);

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

}
