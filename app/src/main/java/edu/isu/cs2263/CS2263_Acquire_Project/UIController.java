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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.Console;
import java.io.IOException;
import java.util.*;

public class UIController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void render(Event event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        GameState gameState = GameState.getInstance(null);

        //updates the current players hand
        PlayerInfo playerInfo = gameState.getCurrentPlayer();
        int i = 0;
        for (Tile tile : playerInfo.pHand.playersTiles){
            String id = "#Tile"+i;
            Button button = (Button) scene.lookup(id);

            button.setText(tile.getLocation());
            i++;
        }

        //update the gameboard
        Tile[][] gameboard = gameState.gameboard.gameboard;
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
                System.out.println("corpval: " + corpVal);
                switch (corpVal){
                    case "Festival":
                        pane.setStyle("-fx-background-color: green; -fx-border-color: darkgray");
                        text.setStyle("-fx-fill: white");
                        break;
                    case "Imperial":
                        pane.setStyle("-fx-background-color: magenta; -fx-border-color: darkgray");
                        text.setStyle("-fx-fill: white");
                        break;
                    case "WorldWide":
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
                        pane.setStyle("-fx-background-color: lightgray; -fx-border-color: darkgray");
                        text.setStyle("-fx-fill: black");
                        break;
                }
                gridPane.add(pane, x, y);
            }
        }

        //updates scoreboard
        GridPane scorePane = (GridPane) scene.lookup("#Scoreboard");
        for (int playerIndex = 0; playerIndex < gameState.scoreboard.players.activePlayers.size(); playerIndex++){
            PlayerInfo playerToAdd = gameState.scoreboard.players.getPlayerByName("Player "+(playerIndex+1));

            //show cash
            Text cash = (Text) scene.lookup("#p"+(playerIndex+1)+"Cash");
            cash.setText(String.valueOf(playerToAdd.pWallet.cash));
            //show Stock
            Text festival = (Text) scene.lookup("#p"+(playerIndex+1)+"Festival");
            festival.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("Festival")));

            Text Imperial = (Text) scene.lookup("#p"+(playerIndex+1)+"Imperial");
            Imperial.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("Imperial")));

            Text WorldWide = (Text) scene.lookup("#p"+(playerIndex+1)+"WorldWide");
            WorldWide.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("Worldwide")));

            Text American = (Text) scene.lookup("#p"+(playerIndex+1)+"American");
            American.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("American")));

            Text Sackson = (Text) scene.lookup("#p"+(playerIndex+1)+"Sackson");
            Sackson.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("Sackson")));

            Text Tower = (Text) scene.lookup("#p"+(playerIndex+1)+"Tower");
            Tower.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("Tower")));

            Text Continental = (Text) scene.lookup("#p"+(playerIndex+1)+"Continental");
            Continental.setText(String.valueOf(playerToAdd.pWallet.getStocks().get("Continental")));

            Text score = (Text) scene.lookup("#p"+(playerIndex+1)+"Score");
            score.setText(String.valueOf(gameState.scoreboard.getPlayerScore("Player "+(playerIndex+1))));
        }
        //updates StockMarket
        List<String> corpNames = Arrays.asList("Festival", "Imperial", "Worldwide", "American", "Sackson", "Tower", "Continental");
        for (String string : corpNames){
            //update remaining
            CorpInfo currentCorp = gameState.scoreboard.corporations.getCorp(string);
            Text currentCorpRemaining = (Text) scene.lookup("#"+string+"Quantity");
            currentCorpRemaining.setText(String.valueOf(currentCorp.getAvailableStocks()));
            //updates price
            Text currentCorpPrice = (Text) scene.lookup("#"+string+"Price");
            currentCorpPrice.setText(String.valueOf(currentCorp.getStockPrice()));
            //updates size
            Text currentCorpSize = (Text) scene.lookup("#"+string+"Size");
            currentCorpSize.setText(String.valueOf(currentCorp.getCorpSize()));
        }

        stage.setScene(scene);
        stage.show();
    }

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
    public void buyButton(ActionEvent event) throws IOException {
        scene = ((Node)event.getSource()).getScene();
        render(event);
    }

    @FXML
    public void handleLoadGameButton(ActionEvent event){
        scene = ((Node)event.getSource()).getScene();

        System.out.println("Clicked load button");
    }

    @FXML
    public void handleExitGameButton(ActionEvent event){

        System.exit(0);
    }

    @FXML
    public void playTile(ActionEvent event) throws IOException {
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);
        Button button = (Button) event.getSource();
        String id = button.getId();
        id = id.replace("Tile","");
        Tile playTile = gameState.getCurrentPlayer().pHand.playersTiles.get(Integer.parseInt(id));
        gameState.placeTile(playTile, gameState.getCurrentPlayer().pName);

        render(event);
    }
}
