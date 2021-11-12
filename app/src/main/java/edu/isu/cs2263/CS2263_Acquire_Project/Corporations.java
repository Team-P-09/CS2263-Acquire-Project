package edu.isu.cs2263.CS2263_Acquire_Project;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter @Setter
public class Corporations {
    HashMap<String, CorpInfo> corps;

    public Corporations(ArrayList<String> corpNames){
        corps = initializeCorps(corpNames);
    }

    public void mergeCorps(String domCorpName, ArrayList<String> subCorpNames){
        for(String corpName : subCorpNames){
            //this.getCorp(domCorpName).corpTiles.addAll(this.getCorp(corpName).retrieveTiles());
            CorpInfo domCorp = this.getCorp(domCorpName);
            HashMap<String, Tile> domTiles = domCorp.getCorpTiles();
            CorpInfo subCorp = this.getCorp(corpName);
            HashMap<String, Tile> subTiles = subCorp.retrieveTiles();
            domTiles.putAll(subTiles);
            this.getCorp(corpName).setCorpTiles(domTiles);
//
//            this.getCorp(domCorpName).setCorpTiles(this.getCorp(domCorpName).getCorpTiles().putAll(subTiles));
            this.getCorp(corpName).setStatus(false);
        }
    }

    private HashMap<String, CorpInfo> initializeCorps(ArrayList<String> corpNames){
        HashMap<String, CorpInfo> initCorp = new HashMap<>();
//        int stPrice = 100;
//        int avStocks = 25;
//        ArrayList<Tile> coTiles = new ArrayList<>();
//        int coSize = 0;
//        boolean stat = false;

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
}
