package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainViewController {

    @FXML
    private Label statusLabel;

    @FXML
    private void btnSearch() throws IOException {
        App.setRoot("search");
    }


    @FXML
    private void btnLogin() throws IOException{
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
}