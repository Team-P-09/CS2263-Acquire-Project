package edu.isu.cs2263.CS2263_Acquire_Project;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
public class Players {
    ArrayList<PlayerInfo> activePlayers; //ORDER OF PLAYERS AND PLAYER NAMES
    TileStack tStack;


    public Players(Integer number, ArrayList<String> corpNames){
        tStack = new TileStack();
        activePlayers = initPlayers(number, corpNames);
    }

    /**
     *
     * @param number
     * @param corpNames
     * @return
     */
    public ArrayList<PlayerInfo> initPlayers(Integer number, ArrayList<String> corpNames){
        ArrayList<PlayerInfo> newPlayers = new ArrayList<>();
        ArrayList<String> playerList = namePlayers(number);
        ArrayList<String> pNames = sortPlayers(playerList);

        for(int i = 0 ; i < pNames.size() ; i++){
            ArrayList<Tile> tArry = getTStack().pullTiles(6);
            PlayerInfo newP = new PlayerInfo(pNames.get(i), corpNames, tArry);
            newPlayers.add(newP);
        }
        return newPlayers;
    }

    /**
     * Takes an interger and names players based on their position
     * Returns an ArrayList of player names as strings
     * @param number
     * @return
     */
    public ArrayList<String> namePlayers(Integer number){
        ArrayList<String> pSet = new ArrayList<>();
        String p = "Player ";
        for(int i = 1 ; i < number +1 ; i++){
            pSet.add(p + i);
        }
        return pSet;
    }

    /**
     * Sorts and names the players
     * @param pSet
     * @return
     */
    public ArrayList<String> sortPlayers(ArrayList<String> pSet){
        HashMap<String, Integer> playerOrderTM = new HashMap<>();
        ArrayList<Tile> tilesForPosition = new ArrayList<>();
        Tile t;
        for(String s : pSet){
            t = getTStack().popTile();
            tilesForPosition.add(t);
            playerOrderTM.put(s, t.getCol() + t.getRow());
        }
        getTStack().getTileStack().addAll(tilesForPosition);

        //THIS CODE WAS FOUND ON https://stackabuse.com/how-to-sort-a-hashmap-by-value-in-java/
        Map<String, Integer> sortedPlayers = playerOrderTM.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));

        return new ArrayList<>(sortedPlayers.keySet());
    }

    public PlayerInfo getPlayerByName(String name){
        Integer pindex = getActivePlayers().indexOf(name);
        return getActivePlayers().get(pindex);
    }

    public void buyStock(String pName, String cName, Integer qty, Integer stockVal){
        getPlayerByName(pName).getPWallet().addStock(cName, qty);
        getPlayerByName(pName).getPWallet().removeCash(qty * stockVal);
    }

    public void sellStock(String pName, String cName, Integer qty, Integer stockVal){
        getPlayerByName(pName).getPWallet().removeStock(cName, qty);
        getPlayerByName(pName).getPWallet().addCash(qty * stockVal);
    }
}
