package org.openjfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchController {

    @FXML
    private TextField searchBox;

    @FXML
    private ListView<String> listView;

    @FXML
    private void updateSearch() {
        System.out.println("Du klickade på knappen!");
     
        String text = searchBox.getText();
        searchBox.clear();
        System.out.println(text);
    }
}

