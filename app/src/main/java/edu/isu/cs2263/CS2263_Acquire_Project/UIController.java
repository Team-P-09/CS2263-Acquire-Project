package edu.isu.cs2263.CS2263_Acquire_Project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
