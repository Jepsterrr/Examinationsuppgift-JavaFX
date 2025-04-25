package org.openjfx.controller;

import java.io.IOException;

import org.openjfx.App;

import javafx.fxml.FXML;

public class ProfileViewController {

    @FXML
    private void btnSearch() throws IOException {
        App.setRoot("search");
    }

}
