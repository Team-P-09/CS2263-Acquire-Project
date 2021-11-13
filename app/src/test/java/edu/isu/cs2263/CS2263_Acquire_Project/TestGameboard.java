package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameboard {

    Gameboard testGameboard;
    Tile testTile;


    @BeforeEach
    public void setUp(){
        Gameboard testGameboard = new Gameboard();
        Tile testTile = new Tile(3, 3);
        testTile.setRow(3);
        testTile.setCol(3);
    }

    @AfterEach
    public void teardown(){
        Gameboard testGameboard = new Gameboard();
        Tile testTile = new Tile(3, 3);
        testTile.setRow(3);
        testTile.setCol(3);
    }

    @Test
    public void testRecordTileActivation(){
        Tile tile = testTile;
        int row = tile.getRow();
        int col = tile.getCol();
        testGameboard.recordTile(tile);
        assertNotNull(testGameboard.gameboard[testTile.getRow()][testTile.getCol()]);
    }

    @Test
    public void testRecordTileGetsMerge(){

    }

    @Test
    public void testRecordTileGetNothing(){

    }

    @Test
    public void testRecordTileGetFounding(){

    }

    @Test
    public void testRecordTileGetAddtoCorp(){

    }
}
