package edu.isu.cs2263.CS2263_Acquire_Project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameboard {

    Gameboard testGameboard = new Gameboard();
    Tile testTile = new Tile(3, 3, null);
    @BeforeEach
    public void setUp(){
        testTile.setRow(3);
        testTile.setCol(3);
        testTile.setCorp(null);
    }

    @Test
    public void testRecordTile(){
        Tile tile = testTile;
        testGameboard.recordTile(tile);
        assertNotNull(testGameboard.gameboard[testTile.getRow()][testTile.getCol()]);
    }
}
