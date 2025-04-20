package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LogInViewController {

    @FXML
    private Label statusLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void btnHome() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void btnLogIn() {
        statusLabel.setText("Du har tryckt på knappen!");

        if (usernameField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
            statusLabel.setText("Inloggning försöktes!");
        }
        else {
            statusLabel.setText("Fyll i användarnamn och lösenord!");
        }
    }
}
