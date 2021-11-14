package edu.isu.cs2263.CS2263_Acquire_Project;

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
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Getter @Setter
public class Corporations {
    HashMap<String, CorpInfo> corps;

    public Corporations(ArrayList<String> corpNames){
        corps = initializeCorps(corpNames);
    }

    /**
     * Removes all tiles from sub corps and adds them to the dominate corp in the merge
     * @param domCorpName
     * @param subCorpNames
     */
    public void mergeCorps(String domCorpName, ArrayList<String> subCorpNames){
        for(String corpName : subCorpNames){
            CorpInfo domCorp = getCorp(domCorpName);
            HashMap<String, Tile> domTiles = domCorp.getCorpTiles();

            CorpInfo subCorp = getCorp(corpName);
            HashMap<String, Tile> subTiles = subCorp.retrieveTiles();

            domTiles.putAll(subTiles);
            getCorp(corpName).setCorpTiles(domTiles);
            getCorp(corpName).setStatus(false);
        }
    }

    private HashMap<String, CorpInfo> initializeCorps(ArrayList<String> corpNames){
        HashMap<String, CorpInfo> initCorp = new HashMap<>();

        for(String corpName : corpNames){
            initCorp.put(corpName, new CorpInfo());
        }
        return initCorp;
    }

    public CorpInfo getCorp(String corpName){
        return this.corps.get(corpName);
    }

    /**
     * Calls lower tier method
     * Step 3/4
     * Method Order:
     *      1 - GameState : placeTile
     *      2 - Scoreboard : initCorpTileAdd
     *      3 - Corporations : addTileToCorp
     *      4 - CorpInfo : addCorpTile
     * @param corpName
     * @param t
     */
    public void addTileToCorp(String corpName, Tile t){
        this.corps.get(corpName).addCorpTile(t);
    }

    public String getTilesCorp(Tile t){
        for(String cName : getCorps().keySet()){
            if(getCorp(cName).getCorpTiles().containsKey(t.getLocation())){
                return cName;
            }
        }
        return null; //CHANGE TO THROW EXCEPTION
    }

    /**
     * Sets the value of all corporation stocks in the array list to 0
     * Used after a merge in Scoreboard
     * @param subCorps
     */
    public void clearStockValues(ArrayList<String> subCorps) {
        for (String cName : subCorps) {
            getCorp(cName).setStockPrice(0);
        }
    }
    /**
     * @param jsonFile (String to become json file)
     * @param corps (corporation object to save)
     * @return File (json File to be later deserialized)
     * @throws IOException
     */
    public static File saveCorporations(String jsonFile, Corporations corps) throws IOException {
        //create Gson instance
        Gson gson = new Gson();
        //create json string to hold data
        String jsonString = gson.toJson(corps);

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
     * @param jsonFile (jsonFile string that was created in saveCorporations)
     * @return returns a Corporations object that was previously saved
     */
    public Corporations loadCorporations(String jsonFile){
        try {
            //create Gson instance
            Gson gson = new Gson();

            //create a reader
            Reader reader = Files.newBufferedReader(Paths.get(jsonFile));

            //set type for corporations
            Type corporationsType = new TypeToken<Corporations>(){}.getType();

            //convert JSON string to Corporations obj
            Corporations corporations_obj = gson.fromJson(reader, corporationsType);

            //close reader
            reader.close();

            return corporations_obj;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
