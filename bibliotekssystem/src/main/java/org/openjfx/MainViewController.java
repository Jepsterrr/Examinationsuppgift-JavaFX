package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainViewController {

    @FXML
    private Label statusLabel;

    @FXML
    private void searchButton() throws IOException {
        App.setRoot("search");
    }


    @FXML
    private void handleLogin() {
        statusLabel.setText("Du är inloggad!");
    }

    @FXML
    private void pressHome() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void pressButton() throws IOException {
        System.out.println("Button pressed");
    }
}