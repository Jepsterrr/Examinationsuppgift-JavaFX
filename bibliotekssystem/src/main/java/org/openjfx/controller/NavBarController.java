package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;

public class NavBarController {

    @FXML private Button logInButton;
    @FXML private MenuButton logInMenu;

    @FXML
    public void initialize() {
        updateState();
    }

    // Anropa varje gång vi vill uppdatera knapp-visningar
    public void updateState() {
        boolean loggedIn = App.getCurrentUser() != null;
        logInButton.setVisible(!loggedIn);
        logInButton.setManaged(!loggedIn);
        logInMenu.setVisible(loggedIn);
        logInMenu.setManaged(loggedIn);
    }

    @FXML
    private void btnHome() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void btnLogIn() throws IOException {
        App.setRoot("LogInView");
    }

    @FXML
    private void handleLogout() throws IOException {
        App.logout();
        updateState();

        if (App.getCurrentSceneName().equals("ProfileView")) {
            App.setRoot("MainView");
        }
    }
}