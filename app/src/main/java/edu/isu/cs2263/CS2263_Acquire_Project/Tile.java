package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.Arrays;
import java.util.List;

public class Tile {
    int row = 0;
    int col = 0;
    String corp= "";

    public int getRow(){
        return row;
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

    public Tile(int row, int col, String corp) {
        this.row = row;
        this.col = col;
        this.corp = corp;
    }
}
