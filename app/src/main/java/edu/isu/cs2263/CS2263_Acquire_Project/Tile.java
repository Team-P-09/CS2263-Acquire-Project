package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter @Setter
public class Tile {
    int row;
    int col;
    String corp;
    String location;
    boolean status;

    public Tile(int r, int c){
        row = r;
        col = c;
        location = String.valueOf(col) + ", " + String.valueOf(row);
        corp = null;
        status = false;
    }

    public void activateTile(){
        status = true;
    }

}
