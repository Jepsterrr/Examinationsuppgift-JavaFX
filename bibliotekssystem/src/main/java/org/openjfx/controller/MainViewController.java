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

    public void handleLogIn() {
        // Visa dashboard-panelen
        profileButton.setVisible(true);
        profileButton.setManaged(true);

        // Dölja logga in-knappen
        logInButton.setVisible(false);
        logInButton.setManaged(false);

        // Visa dropdown-knapp istället
        logInButtonMenu.setVisible(true);
        logInButtonMenu.setManaged(true);
    }

    public void handleLogout() {
        // Dölja dashboard-panelen
        profileButton.setVisible(false);
        profileButton.setManaged(false);

        // Visa logga in-knappen
        logInButton.setVisible(true);
        logInButton.setManaged(true);

        // Dölja dropdown-knappen
        logInButtonMenu.setVisible(false);
        logInButtonMenu.setManaged(false);
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
        System.out.println("Profil");
    }
}