package edu.isu.cs2263.CS2263_Acquire_Project;

import java.util.HashMap;
import java.util.List;

public class Corporations {
    HashMap<String, CorpInfo> corps;

    public Corporations(String[] corpNames){
        corps = initializeCorps(corpNames);
    }

    public void mergeCorps(HashMap<String, Boolean> mergeCorps){
        System.out.println("mergeCorps");
    }

    public HashMap<String, CorpInfo> initializeCorps(String[] corpNames){
        HashMap<String, CorpInfo> initCorp = new HashMap<>();
        int stPrice = 100;
        int avStocks = 25;
        int coSize = 0;
        boolean stat = false;

        for(String corpName : corpNames){
            initCorp.put(corpName, new CorpInfo(stPrice, avStocks, coSize, stat));
        }
        return initCorp;
    }

    public void getCorp(String corpName){
        System.out.println("Get Corp");
    }
}
