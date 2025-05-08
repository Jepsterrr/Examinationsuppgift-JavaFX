package org.openjfx.controller;
import java.sql.SQLException;
import java.util.List;

import org.openjfx.service.SearchService;
import org.openjfx.table.MediaItem;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class SearchController {

    private final SearchService service = new SearchService();

    @FXML
    private TextField searchBox;

    @FXML
    private ListView<MediaItem> listView;

    @FXML
    public void initialize() {
        listView.setCellFactory(lv -> new ListCell<>() {
            // Används för att visa MediaItem-objekt i listan på ett lämpligt sätt
            @Override
            protected void updateItem(MediaItem item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null)
                    ? null
                    : item.getTitle() + item.getDetails());
            }
        });

        // Hämta alla MediaItem-objekt och lägg till dem i listan
        try {
            List<MediaItem> allItems = service.getAll();
            listView.getItems().setAll(allItems);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateSearch() {
        String term = searchBox.getText();
        try {
            List<MediaItem> hits = service.searchAll(term);

            // Fyll listan med MediaItem-objekt
            listView.getItems().setAll(hits);

            searchBox.clear();
            for (MediaItem item : hits) {
                System.out.println(item.getTitle() + item.getDetails());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

