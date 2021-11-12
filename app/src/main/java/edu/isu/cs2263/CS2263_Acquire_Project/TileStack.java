package edu.isu.cs2263.CS2263_Acquire_Project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileStack {
    int height;
    int width;
    ArrayList<Tile> tileStack;

    public TileStack(){
        height = 9;
        width  = 12;
        tileStack = new ArrayList<>();
    }

    public boolean isEmpty(){
        return tileStack.isEmpty();
    }

    public void initializeTiles(){
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Tile tile = new Tile(j, i);
                tileStack.add(tile);
            }
        }
        for (Tile tile : tileStack){
            System.out.println(tile.getLocation());
        }
    }

    public void saveTileStack(){
        String gsonWrite = new Gson().toJson(tileStack);
    }

    public void loadTileStack(String filename, Type Tile){
        Gson gsonRead = new Gson();

        Type tileListType = new TypeToken<ArrayList<Tile>>(){}.getType();
        tileStack = gsonRead.fromJson(filename, tileListType);

        for (Tile tile : tileStack){
            System.out.println(tile.getLocation());
        }
    }

    public Tile popTile(){
        Tile current;
        current = tileStack.get(0);
        tileStack.remove(0);
        return  current;
    }
}
