package edu.isu.cs2263.CS2263_Acquire_Project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter @Setter
public class TileStack {
    int height;
    int width;
    ArrayList<Tile> tileStack;

    public TileStack(){
        height = 9;
        width  = 12;
        tileStack = initializeTiles();
    }

    public boolean isEmpty(){
        return getTileStack().isEmpty();
    }

    public ArrayList<Tile> initializeTiles(){
        ArrayList<Tile> newTStack = new ArrayList<>();
        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Tile tile = new Tile(j, i);
                newTStack.add(tile);
            }
        }
        Collections.shuffle(newTStack);
        return newTStack;
    }

    public ArrayList<Tile> pullTiles(Integer qty){
        ArrayList<Tile> pulledTiles = new ArrayList<>();
        for(int i = 0 ; i < qty ; i++){
            pulledTiles.add(popTile());
        }
        return pulledTiles;
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
        current = getTileStack().get(0);
        getTileStack().remove(0);
        return  current;
    }
}
