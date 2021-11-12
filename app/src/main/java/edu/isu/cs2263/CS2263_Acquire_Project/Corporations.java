package edu.isu.cs2263.CS2263_Acquire_Project;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Corporations {
    HashMap<String, CorpInfo> corps;

    public Corporations(String[] corpNames){
        corps = initializeCorps(corpNames);
    }

    public void mergeCorps(String domCorpName, String[] subCorpNames){
        for(String corpName : subCorpNames){
            this.getCorp(domCorpName).corpTiles.addAll(this.getCorp(corpName).retrieveTiles());
            this.getCorp(corpName).setStatus(false);
        }
    }

    private HashMap<String, CorpInfo> initializeCorps(String[] corpNames){
        HashMap<String, CorpInfo> initCorp = new HashMap<>();
        int stPrice = 100;
        int avStocks = 25;
        ArrayList<Tile> coTiles = new ArrayList<>();
        int coSize = 0;
        boolean stat = false;

        for(String corpName : corpNames){
            initCorp.put(corpName, new CorpInfo());
        }
        return initCorp;
    }

    public CorpInfo getCorp(String corpName){
        return this.corps.get(corpName);
    }

    public void addTileToCorp(String corpName, Tile t){
        this.corps.get(corpName).addCorpTile(t);
    }
}
