package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainViewController {

    @FXML
    private Button adminButton;

    @FXML
    private Button loanButton;

    @FXML
    private Button returnButton;

    @FXML
    public void initialize() {
        updateState();
    }

    public void updateState() {
        boolean loggedIn = (App.getCurrentUser() != null);
        boolean isAdmin = loggedIn && App.getCurrentUser().getUserType().equals("admin");

        adminButton.setVisible(isAdmin);
        adminButton.setManaged(isAdmin);
        loanButton.setVisible(loggedIn && !isAdmin);
        loanButton.setManaged(loggedIn && !isAdmin);
        returnButton.setVisible(loggedIn && !isAdmin);
        returnButton.setManaged(loggedIn && !isAdmin);
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
    private void btnAdmin() throws IOException {
        App.setRoot("AdminView");
    }

    @FXML
    private void btnLoan() throws IOException {
        App.setRoot("CreateLoanView");
    }

    @FXML
    private void btnReturn() throws IOException {
        App.setRoot("ReturnView");
    }
}