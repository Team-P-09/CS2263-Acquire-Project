package edu.isu.cs2263.CS2263_Acquire_Project;

public class Scoreboard {
    //List<Player> players;
    String[] corpNames = new String[]{"Worldwide", "Sackson", "Festival", "Imperial", "American", "Tower", "Continental"};
    Corporations corporations;

    public void initializeScoreboard() {
        corporations = new Corporations(corpNames);
        //Initialize Players
    }

    public void displayCorpInfo(){
        System.out.println("displayCorpInfo");
    }

    public void displayPlayers(){
        System.out.println("displayPlayers");
    }

    public void addPlayer(){
        System.out.println("AddPlayer");
    }

    public void getWinners(){
        System.out.println("Get winners");
    }


}
