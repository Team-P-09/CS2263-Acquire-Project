package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.Arrays;
import java.util.List;

public class Tile {
    int row ;
    int col;
    String corp;
    String location;
    boolean status;

    public int getRow(){
        return row;
    }

    public Tile(int r, int c){
        row = r;
        col = c;
        location = String.valueOf(col) + ", " + String.valueOf(row);
        corp = null;
        status = false;
    }

    public int getCol(){
        return col;
    }

    public String getLocation(){
        String location = this.getCol() + ", " + this.getRow();
        return location;
    }

    public String getCorp(){
        return corp;
    }

    public void setRow(int y){
        if (y >= 1 && y <= 9){
            row = y - 1;
        }
        else{
            row = row;
        }
    }

    public void setCol(int x){
        if (x >= 1 && x <= 12){
            col = x - 1;
        }
        else{
            col = col;
        }
    }

    public void setCorp(String name){
        corp = name;
    }

    public void activateTile(){
        status = true;
    }

}
