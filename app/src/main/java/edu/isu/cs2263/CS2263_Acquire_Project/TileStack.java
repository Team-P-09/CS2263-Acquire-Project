package edu.isu.cs2263.CS2263_Acquire_Project;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        for (int r = 0; r < height; r++){
            for (int c = 0; c < width; c++){
                Tile tile = new Tile(r, c);
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

    /**
     * @param jsonFile (String to become json file)
     * @param tilePool (tileStack object to save)
     * @return File (json File to be later deserialized)
     * @throws IOException
     */
    public static File saveTileStack(String jsonFile, TileStack tilePool) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold data
        String jsonString = gson.toJson(tilePool);

        try {
            //create the jsonFile
            File file = new File(jsonFile);
            // file.createNewFile();

            //write the json string into the json file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonString);

            //close the file
            fileWriter.flush();
            fileWriter.close();

            return file;

        } catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param jsonFile (jsonFile string that was created in saveTileStack)
     * @return returns a TileStack object that was previously saved
     */
    public TileStack loadTileStack(String jsonFile){
        try {
            //create Gson instance
            Gson gson = new Gson();

            //create a reader
            Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

            //set type for players
            Type tileStackType = new TypeToken<TileStack>(){}.getType();

            //convert JSON string to players obj
            TileStack tileStack_obj = gson.fromJson(reader, tileStackType);

            //close reader
            reader.close();

            return tileStack_obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public Tile popTile(){
        Tile current;
        current = getTileStack().get(0);
        getTileStack().remove(0);
        return  current;
    }
}
