package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;

public class AdminViewController {

    @FXML
    private void btnSearch() throws IOException {
        App.setRoot("search");
    }

}