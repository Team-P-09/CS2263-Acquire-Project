package edu.isu.cs2263.CS2263_Acquire_Project;

public class Tile {
    char row;
    char col;
    String location;
    String corpName;

    public char getRow(){
        return row;
    }

    public char getCol(){
        return col;
    }

    public String getLocation(){
        return location;
    }

    public String getCorpName(){
        return corpName;
    }

    public void setRow(char c){
        row = c;
    }

    public void setCol(char c){
        col = c;
    }

    public void setLocation(){
        location = String.valueOf(col) + String.valueOf(row);
    }

    public void setCorpName(String name){
        corpName = name;
    }
}
