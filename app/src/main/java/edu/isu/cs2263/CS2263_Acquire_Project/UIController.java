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

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class UIController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private Boolean hasPlayed = false;

    @FXML
    public void render(Event event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        GameState gameState = GameState.getInstance(null);

        //updates the current players hand
        PlayerInfo playerInfo = gameState.getCurrentPlayer();
        Text playerLabel = (Text) scene.lookup("#turnLabel");
        playerLabel.setText(playerInfo.getPName());
        int i = 0;
        for (Tile tile : playerInfo.getPHand().getPlayersTiles()){
            String id = "#Tile"+i;
            Button button = (Button) scene.lookup(id);

            button.setText(tile.getLocation());
            i++;
        }

        //update the gameboard
        Tile[][] gameboard = gameState.getGameboard().getGameboard();
        GridPane gridPane = (GridPane) scene.lookup("#gameboard");
        gridPane.getChildren().clear();
        for (int y = 0; y < gameboard.length; y++){
            for (int x = 0; x < gameboard[y].length; x++){
                Tile tile = gameboard[y][x];
                StackPane pane = new StackPane();
                Text text = new Text();
                text.setText(tile.getLocation());
                pane.getChildren().add(text);
                String corpVal = tile.getCorp() != null ? tile.getCorp() : "empty";
//                System.out.println(tile.status);
//                System.out.println(tile.getCorp());

                if(tile.isStatus() == true){
                    switch (corpVal){
                        case "Festival":
                            pane.setStyle("-fx-background-color: green; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: white");
                            break;
                        case "Imperial":
                            pane.setStyle("-fx-background-color: magenta; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: white");
                            break;
                        case "Worldwide":
                            pane.setStyle("-fx-background-color: brown; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: white");
                            break;
                        case "American":
                            pane.setStyle("-fx-background-color: navy; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: white");
                            break;
                        case "Sackson":
                            pane.setStyle("-fx-background-color: red; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: white");
                            break;
                        case "Tower":
                            pane.setStyle("-fx-background-color: yellow; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: black");
                            break;
                        case "Continental":
                            pane.setStyle("-fx-background-color: cyan; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: black");
                            break;
                        default:
                            pane.setStyle("-fx-background-color: gray; -fx-border-color: darkgray");
                            text.setStyle("-fx-fill: black");
                            break;
                    }
                }
                else {
                    pane.setStyle("-fx-background-color: lightgray; -fx-border-color: darkgray");
                    text.setStyle("-fx-fill: black");
                }
                gridPane.add(pane, x, y);
            }
        }

        //updates scoreboard
        GridPane scorePane = (GridPane) scene.lookup("#Scoreboard");
        for (int playerIndex = 0; playerIndex < gameState.getScoreboard().getPlayers().getActivePlayers().size(); playerIndex++){
            PlayerInfo playerToAdd = gameState.getScoreboard().getPlayers().getPlayerByName("Player "+(playerIndex+1));

            //show cash
            Text cash = (Text) scene.lookup("#p"+(playerIndex+1)+"Cash");
            cash.setText(String.valueOf(playerToAdd.getPWallet().getCash()));
            //show Stock
            Text festival = (Text) scene.lookup("#p"+(playerIndex+1)+"Festival");
            festival.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("Festival")));

            Text Imperial = (Text) scene.lookup("#p"+(playerIndex+1)+"Imperial");
            Imperial.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("Imperial")));

            Text WorldWide = (Text) scene.lookup("#p"+(playerIndex+1)+"Worldwide");
            WorldWide.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("Worldwide")));

            Text American = (Text) scene.lookup("#p"+(playerIndex+1)+"American");
            American.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("American")));

            Text Sackson = (Text) scene.lookup("#p"+(playerIndex+1)+"Sackson");
            Sackson.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("Sackson")));

            Text Tower = (Text) scene.lookup("#p"+(playerIndex+1)+"Tower");
            Tower.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("Tower")));

            Text Continental = (Text) scene.lookup("#p"+(playerIndex+1)+"Continental");
            Continental.setText(String.valueOf(playerToAdd.getPWallet().getStocks().get("Continental")));

            Text score = (Text) scene.lookup("#p"+(playerIndex+1)+"Score");
            score.setText(String.valueOf(gameState.getScoreboard().getPlayerScore("Player "+(playerIndex+1))));
        }

        //updates StockMarket
        List<String> corpNames = Arrays.asList("Festival", "Imperial", "Worldwide", "American", "Sackson", "Tower", "Continental");
        for (String string : corpNames){
            //update remaining
            CorpInfo currentCorp = gameState.getScoreboard().getCorporations().getCorp(string);
            Text currentCorpRemaining = (Text) scene.lookup("#"+string+"Quantity");
            currentCorpRemaining.setText(String.valueOf(currentCorp.getAvailableStocks()));
            //updates price
            Text currentCorpPrice = (Text) scene.lookup("#"+string+"Price");
            currentCorpPrice.setText(String.valueOf(currentCorp.getStockPrice()));
            //updates size
            Text currentCorpSize = (Text) scene.lookup("#"+string+"Size");
            currentCorpSize.setText(String.valueOf(currentCorp.getCorpSize()));
        }

        //check end game validity
        Button endButton = (Button) scene.lookup("#endGameButton");
        if (gameState.checkIfGameCanEnd() == false){
            endButton.setText("Game can not end!");
            endButton.setStyle("-fx-background-color: gray; -fx-fill: black");
        }
        else {
            endButton.setText("End game this turn?");
            endButton.setStyle("-fx-background-color: darkred; -fx-fill: white");
        }

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void buyButton(ActionEvent event) throws IOException {
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();

        //popup to get number of players
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Festival");
        choices.add("Imperial");
        choices.add("Worldwide");
        choices.add("American");
        choices.add("Sackson");
        choices.add("Tower");
        choices.add("Continental");


        ChoiceDialog<String> dialog = new ChoiceDialog("Festival", choices);
        dialog.setTitle("Buy");
        dialog.setHeaderText("What corporation would you like to buy?");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()){
            gameState.getScoreboard().initBuy(gameState.getCurrentPlayer().getPName(), result.get());

            render(event);
        }
    }

    @FXML
    public void sellButton(ActionEvent event) throws IOException {
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();

        //popup to get number of players
        ArrayList<String> choices = new ArrayList<String>();
        choices.add("Festival");
        choices.add("Imperial");
        choices.add("Worldwide");
        choices.add("American");
        choices.add("Sackson");
        choices.add("Tower");
        choices.add("Continental");


        ChoiceDialog<String> dialog = new ChoiceDialog("Festival", choices);
        dialog.setTitle("Sell");
        dialog.setHeaderText("What corporation would you like to sell?");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            gameState.getScoreboard().initSell(gameState.getCurrentPlayer().getPName(), result.get(), false);

            render(event);
        }
    }

    @FXML
    public void endTurnButton(ActionEvent event) throws IOException{
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();
        if (gameState.getHasPlayed() == true) {
            if (gameState.getEndGame() == true) {
                gameState.endGame();
            }

            PlayerInfo currentPlayer = gameState.getCurrentPlayer();
            if (gameState.getCurrentPlayer().getPHand().getPlayersTiles().size() < 6) {
                gameState.drawTileToPlayer(currentPlayer.getPName());
            }

            gameState.nextPlayer();
            //tiles must be removed at the start of the players turn else a tile can be played the turn after it becomes unplayable
            currentPlayer = gameState.getCurrentPlayer();
            gameState.checkPlayerHandForRefresh(currentPlayer.getPName());
            render(event);
        }
    }


    @FXML
    public void ShowRulesButton() throws IOException{
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rules");
        alert.setHeaderText("How to play the game!");
        alert.setContentText("https://www.ultraboardgames.com/acquire/game-rules.php");

        alert.showAndWait();
    }

    @FXML
    public  void handleEndGameButton(ActionEvent event){
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();
        if(gameState.checkIfGameCanEnd() == true){
            gameState.setEndGame();
        }
    }

    @FXML
    public void handleSaveGameButton(ActionEvent event){
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();
        gameState.saveGameState(gameState.getScoreboard(), gameState.getGameboard());
    }

    @FXML
    public void playTile(ActionEvent event) throws IOException{
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();
        if (gameState.hasPlayed == false){
            String id = button.getId();
            id = id.replace("Tile","");
            Tile playTile = gameState.getCurrentPlayer().getPHand().getPlayersTiles().get(Integer.parseInt(id));
            gameState.placeTile(playTile, gameState.getCurrentPlayer().getPName());
            gameState.hasPlayed();
        }

        render(event);
    }

    //MENU BUTTONS
    @FXML
    public void handleStartNewGameButton(ActionEvent event) throws IOException {
        event.consume();

        //popup to get number of players
        ArrayList<Integer> choices = new ArrayList<Integer>();
        choices.add(2);
        choices.add(3);
        choices.add(4);

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(2, choices);
        dialog.setTitle("Number of players");
        dialog.setHeaderText("How many players?");

        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()){
            GameState gameState = GameState.getInstance(result.get());
            render(event);
        }
    }

    @FXML
    public void handleLoadGameButton(ActionEvent event){
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();

//        gameState.loadGameState();

    }

    @FXML
    public void handleExitGameButton(ActionEvent event){

        System.exit(0);
    }
}
