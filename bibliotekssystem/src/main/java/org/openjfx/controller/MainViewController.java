package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainViewController {

    @FXML
    private Button profileButton;

    @FXML
    private Button adminButton;

    @FXML
    public void initialize() {
        updateState();
    }

    public void updateState() {
        boolean loggedIn = (App.getCurrentUser() != null);
        boolean isAdmin = loggedIn && App.getCurrentUser().getUserType().equals("admin");

        adminButton.setVisible(isAdmin);
        adminButton.setManaged(isAdmin);
        profileButton.setVisible(loggedIn && !isAdmin);
        profileButton.setManaged(loggedIn && !isAdmin);
    }

    @FXML
    private void btnSearch() throws IOException {
        App.setRoot("search");
    }


    @FXML
    private void btnLogIn() throws IOException {
        App.setRoot("LogInView");
    }

    @FXML
    private void btnHome() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void btnProfile() throws IOException {
        App.setRoot("ProfileView");
    }

    @FXML
    private void btnAdmin() throws IOException {
        App.setRoot("AdminView");
    }
}