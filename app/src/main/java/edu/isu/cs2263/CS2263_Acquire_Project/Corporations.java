/*
 * MIT License
 *
 * Copyright (c) 2021 Thomas Evans, David Lindeman, and Natalia Castaneda
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
     * updates stock tier based on corporation name
     * @param corpName
     */
    public void setStockValue(String corpName){
        Integer corpSize = getCorp(corpName).getCorpSize();
        Integer stockTier = 0;
        HashMap<Integer, Integer> stockTiers = new HashMap<>();
        for(int i = 1 ; i < 13 ; i++){
            stockTiers.put(i, 100+100*i);
        }


        if(corpName.equals("Imperial") || corpName.equals("Continental")){
            stockTier = 2;
        }else if(corpName.equals("American") || corpName.equals("Worldwide") || corpName.equals("Festival")){
            stockTier = 1;
        }else{ //corpName will be Tower or Saxon
            stockTier = 0;
        }
        stockTier += checkTier(corpSize);

        if(stockTier !=0) {
            getCorp(corpName).setStockPrice(stockTiers.get(stockTier));
        }
    }

    public Integer getBonus(String corpName, String bonusType){
        Integer corpSize = getCorp(corpName).getCorpSize();
        Integer bonusTier = 0;
        Integer bonusAmt = 0;
        HashMap<Integer, Integer> majorityTiers = new HashMap<>();
        HashMap<Integer, Integer> minorityTiers = new HashMap<>();
        for(int i = 0 ; i < 12 ; i++){
            majorityTiers.put(i, 2000+1000*i);
            minorityTiers.put(i, 1000+500*i);
        }

        if(corpName.equals("Imperial") || corpName.equals("Continental")){
            bonusTier = 2;
        }else if(corpName.equals("American") || corpName.equals("Worldwide") || corpName.equals("Festival")){
            bonusTier = 1;
        }else{ //corpName will be Tower or Luxor
            bonusTier = 0;
        }
        bonusTier += checkTier(corpSize);
        if(bonusType.equals("Majority")){
            bonusAmt += majorityTiers.get(bonusTier);
        }else{
            bonusAmt += minorityTiers.get(bonusTier);
        }
        return bonusAmt;
    }

    /**
     * returns a number to increment the tier of a corporation for accuract retreival or stock price
     * @param corpSize
     * @return
     */
    public Integer checkTier(Integer corpSize){
        Integer[] tierArray = new Integer[]{1,2,3,4,5,6,11,21,31,41};
        Integer tierIncrement = 0;
        if(corpSize > 0){
            Integer i = 0;
            while(i<tierArray.length && corpSize < tierArray[i]){
                tierIncrement = tierArray[i];
            }
        }
        return tierIncrement;
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
