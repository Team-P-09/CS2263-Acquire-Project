package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTileStack {

    TileStack testTileStack = new TileStack();

    @BeforeEach
    void setUp(){
        testTileStack = new TileStack();
    }

    @AfterEach
    void teardown(){
        testTileStack = new TileStack();
    }

    @Test
    void testPopTile(){
        testTileStack.initializeTiles();
        assertNotNull(testTileStack.popTile());
    }
}
