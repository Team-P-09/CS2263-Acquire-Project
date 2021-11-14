package edu.isu.cs2263.CS2263_Acquire_Project;

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
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class UIController {

    public Pane pane1_1;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void render(Event event) throws IOException {
        root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGame.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        GameState gameState = GameState.getInstance(null);
        PlayerInfo playerInfo = gameState.scoreboard.players.getCurrentPlayer();
        int i = 0;
        for (Tile tile : playerInfo.pHand.playersTiles){
            String id = "#Tile"+i;
            Button button = (Button) scene.lookup(id);

            button.setText(tile.getLocation());
            i++;
        }
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
                    case "Luxor":
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

        Pane currentPane = (Pane) scene.lookup("#pane1_1");
        currentPane.setStyle("-fx-background-color: black");
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
        Tile playTile = gameState.scoreboard.players.getCurrentPlayer().pHand.playersTiles.get(Integer.parseInt(id));
        System.out.println("tile corpval " + playTile.getCorp());
        gameState.gameboard.recordTile(playTile);
        render(event);
    }

}
