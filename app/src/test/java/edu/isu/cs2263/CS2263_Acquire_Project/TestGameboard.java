package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestGameboard {

    Gameboard testGameboard;
    Tile testTile;


    @BeforeEach
    public void setUp(){
        testGameboard = new Gameboard();
        testTile = new Tile(2, 3);
        testTile.activateTile();
    }

    @AfterEach
    public void teardown(){
        testGameboard = new Gameboard();
        testTile = new Tile(3, 3);
    }

    @Test
    public void testRecordTileActivation(){
        int row = testTile.getRow();
        int col = testTile.getCol();
        testGameboard.recordTile(testTile);
        assertNotNull(testGameboard.getTile(row, col).isStatus());
    }

    @Test
    public void testRecordTileGetNothing(){

    }

    @Test
    public void testRecordTileGetAddtoCorp(){

    }
}
