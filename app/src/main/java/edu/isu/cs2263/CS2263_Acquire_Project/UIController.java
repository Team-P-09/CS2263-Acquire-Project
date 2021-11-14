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
    public void playTile1(ActionEvent event){
        scene = ((Node)event.getSource()).getScene();
        GameState gameState = GameState.getInstance(null);


    }
}
