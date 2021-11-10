package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.Arrays;
import java.util.List;

public class Tile {
    int row = 0;
    int col = 0;
    int corp = 0;
    List<String> corpNames = Arrays.asList("No Corporation", "Festival", "Imperial", "World", "American", "Luxor", "Tower", "Continent");

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

    public String getCorpName(){
        return corpNames.get(corp);
    }

    public void setRow(int i){
        row = i;
    }

    public void setCol(int i){
        col = i;
    }

    public void setCorpName(int i){
        this.corp = i;
    }

    public Tile(int row, int col, int corp) {
        this.row = row;
        this.col = col;
        this.corp = corp;
    }
}
