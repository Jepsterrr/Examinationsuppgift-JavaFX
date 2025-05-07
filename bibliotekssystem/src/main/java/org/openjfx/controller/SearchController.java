package org.openjfx.controller;
import java.sql.SQLException;
import java.util.List;

import org.openjfx.service.SearchInputService;
import org.openjfx.service.ISBNFormatter;
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
            @Override
            protected void updateItem(MediaItem item, boolean empty) {
                super.updateItem(item, empty);
                setText((empty || item == null)
                    ? null
                    : item.getTitle() + " – " + item.getDetails());
            }
        });

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
        if (SearchInputService.isPotentialISBNAttempt(term)) {
            System.out.println("Förmodligen till ISBN");
            String isbn = ISBNFormatter.formatISBN(term);
            try {
                List<MediaItem> hits = service.searchByISBN(isbn);
                listView.getItems().setAll(hits);
                searchBox.clear();
                for (MediaItem item : hits) {
                    System.out.println(item.getTitle() + " - " + item.getDetails());
                }
            // Om det inte finns några träffar, sök i alla medier
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
        } else {
        try {
            List<MediaItem> hits = service.searchAll(term);

            // Fyll listan med MediaItem-objekt
            listView.getItems().setAll(hits);

            searchBox.clear();
            for (MediaItem item : hits) {
                System.out.println(item.getTitle() + " - " + item.getDetails());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
}

