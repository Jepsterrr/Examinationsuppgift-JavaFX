package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

public class MainViewController {

    @FXML
    private Button profileButton;

    @FXML
    private Button logInButton;

    @FXML
    private MenuButton logInButtonMenu;

    @FXML
    public void initialize() {
        boolean loggedIn = (App.getCurrentUser() != null);
        profileButton.setVisible(loggedIn);
        profileButton.setManaged(loggedIn);
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
    private void pressButton() throws IOException {
        System.out.println("Button pressed");
    }

    @FXML
    private void btnProfile() throws IOException {
        App.setRoot("ProfileView");
    }
}