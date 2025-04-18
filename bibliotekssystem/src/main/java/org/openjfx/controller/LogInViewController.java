package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LogInViewController {

    @FXML
    private Label statusLabel;

    @FXML
    private void pressHome() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void testLogIn() {
        statusLabel.setText("Du har tryckt på knappen!");
    }
}
