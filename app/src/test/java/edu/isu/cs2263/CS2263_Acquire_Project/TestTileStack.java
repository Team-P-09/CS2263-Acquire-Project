package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTileStack {

    TileStack testTileStack;

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
        Tile t = testTileStack.popTile();
        assertNotNull(t);
    }

    @Test
    void testPopTileRemovesFromStack(){
        Tile t = testTileStack.popTile();
        int newStackSize = 9*12-1;
        assertTrue(testTileStack.getTileStack().size() == newStackSize);
    }

    @Test
    void testInitTStack(){
        int stackSize = 9*12;
        assertTrue(testTileStack.getTileStack().size() == stackSize);
    }
}
