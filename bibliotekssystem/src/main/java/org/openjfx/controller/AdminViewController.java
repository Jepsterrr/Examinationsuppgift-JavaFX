package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;

public class AdminViewController {

    @FXML
    private void btnBack() throws IOException {
        App.setRoot("MainView");
    }

    @FXML
    private void btnList() throws IOException {
        App.setRoot("LoanOverview");
    }

    @FXML
    private void btnEdit() throws IOException {
        App.setRoot("EditView");
    }
}