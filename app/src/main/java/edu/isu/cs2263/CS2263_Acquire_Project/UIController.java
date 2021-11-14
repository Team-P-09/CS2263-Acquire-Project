package edu.isu.cs2263.CS2263_Acquire_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class UIController {

    @FXML
    private Button startNewGameButton;

    @FXML
    public void handleStartNewGameButton(ActionEvent event) throws IOException {
        event.consume();
        ArrayList<Integer> choices = new ArrayList<Integer>();
        choices.add(2);
        choices.add(3);
        choices.add(4);

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(2, choices);
        dialog.setTitle("Number of players");
        dialog.setHeaderText("How many players?");

        Optional<Integer> result = dialog.showAndWait();
        if (result.isPresent()){
            GameState newGame = new GameState(result.get());
            Stage stage;
            Parent root;

            stage = (Stage) startNewGameButton.getScene().getWindow();
            root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGame.fxml"));

            Scene scene = new Scene(root);
            GridPane gameboard = (GridPane) scene.lookup("#gameboard");

            Boolean check = false;
            for (int i = 0; i < 12; i++){
                for (int j = 0; j < 9; j++){
                    StackPane stackPane = new StackPane();
                    Text text = new Text();

                    stackPane.setId(i + "_" + j);
                    stackPane.setPrefWidth(5.0);
                    stackPane.setPrefHeight(5.0);

                    text.setText(i + ", " + j);

                    stackPane.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;");
                    stackPane.getChildren().add(text);
                    stackPane.setAlignment(text, Pos.CENTER);
                    gameboard.add(stackPane, i, j);

                }
            }

            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void handleConfirmButton(ActionEvent event) throws IOException {
        event.consume();
//        Stage stage;
//        Parent root;
//
//        stage = (Stage) startNewGameButton.getScene().getWindow();
//        root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGame.fxml"));
//
//        Scene scene = new Scene(root);
//        GridPane gameboard = (GridPane) scene.lookup("#gameboard");
//
//        Boolean check = false;
//        for (int i = 0; i < 12; i++){
//            for (int j = 0; j < 9; j++){
//                Pane pane = new Pane();
//                pane.setId(i + "_" + j);
//                pane.setPrefWidth(5.0);
//                pane.setPrefHeight(5.0);
//
//                pane.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;");
//                gameboard.add(pane, i, j);
//
//            }
//        }
//
//        stage.setScene(scene);
//        stage.show();
    }

    @FXML
    public void handleLoadGameButton(ActionEvent event){
        event.consume();
        System.out.println("Clicked load button");
    }

    @FXML
    public void handleExitGameButton(ActionEvent event){
        event.consume();
        System.exit(0);
    }
}
