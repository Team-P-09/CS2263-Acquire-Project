package edu.isu.cs2263.CS2263_Acquire_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class UIController {

    @FXML
    private Button startNewGameButton;

    @FXML
    public void handleStartNewGameButton(ActionEvent event) throws IOException {
        event.consume();
        Stage stage;
        Parent root;

        stage = (Stage) startNewGameButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getClassLoader().getResource("MainGame.fxml"));

//        fx:id="2_12" prefHeight="200.0" prefWidth="200.0" GridPane.columnIndex="11" GridPane.rowIndex="1" style="-fx-background-color: lightgray;"

        Scene scene = new Scene(root);
        GridPane gameboard = (GridPane) scene.lookup("#gameboard");

        Boolean check = false;
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 9; j++){
                Pane pane = new Pane();
                pane.setId(i + "_" + j);
                pane.setPrefWidth(5.0);
                pane.setPrefHeight(5.0);

                pane.setStyle("-fx-background-color: lightgray;-fx-border-color: darkgray;");
                gameboard.add(pane, i, j);

            }
        }

        stage.setScene(scene);
        stage.show();
    }

    public void refresh(){
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 9; j++){
                
            }
        }
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
