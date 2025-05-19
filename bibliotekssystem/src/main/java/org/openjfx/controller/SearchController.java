package org.openjfx.controller;
import java.sql.SQLException;
import java.util.List;

import org.openjfx.service.SearchService;
import org.openjfx.table.MediaItem;
import org.openjfx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SearchController {

    private final SearchService service = new SearchService();

    @FXML
    private TextField searchBox;

    @FXML
    private ListView<MediaItem> listView;

    @FXML private StackPane mainStackPane;
    @FXML private VBox searchViewVBox; 
    @FXML private VBox detailDialogPane;
    @FXML private Label dialogItemTitleLabel;
    @FXML private Label dialogItemAuthorLabel;
    @FXML private Label dialogItemYearLabel;
    @FXML private Label dialogItemTypeLabel;
    @FXML private Label dialogItemISBNLabel;
    @FXML private Text dialogItemDescriptionText; 
    @FXML private Button dialogBorrowButton;
    @FXML private Button dialogBackButton;

    private MediaItem currentMediaItemInDialog; // Antag att din ListView visar MediaItem-objekt

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

        // Lägg till en lyssnare för dubbelklick på listan
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && App.getCurrentUser() != null) {
                MediaItem selectedItem = listView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    // Öppna detaljvyn för det valda objektet
                    System.out.println("Öppnar detaljvy för: " + selectedItem.getTitle());
                    showDetailDialog(selectedItem);
                }
            }
        });
    }


    @FXML
    private void updateSearch() {
        String term = searchBox.getText();
        try {
            List<MediaItem> hits = service.searchAll(term);

            // Fyll listan med MediaItem-objekt
            listView.getItems().setAll(hits);

            for (MediaItem item : hits) {
                System.out.println(item.getTitle() + item.getDetails());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void checkEmpty() {
        if (searchBox.getText().isEmpty()) {
            listView.getItems().clear();
            try {
                List<MediaItem> allItems = service.getAll();
                listView.getItems().setAll(allItems);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            updateSearch();
        }
    }


    private void showDetailDialog(MediaItem item) {
    currentMediaItemInDialog = item; // Spara referens till objektet

    // Fyll label-texterna med information från 'item'
    dialogItemTitleLabel.setText(item.getTitle());
    // Antag att MediaItem har getters för dessa:
    dialogItemAuthorLabel.setText("Författare: "); // Exempel
    dialogItemYearLabel.setText("Utgivningsår: ");
    dialogItemTypeLabel.setText("Typ: ");
    dialogItemISBNLabel.setText("ISBN: " );
    dialogItemDescriptionText.setText("Beskrivning: ");
    // ... fyll fler fält ...

    detailDialogPane.setVisible(true);
    detailDialogPane.setManaged(true);

    // Valfritt: Gör bakgrunden (searchViewVBox) mindre framträdande
    // searchViewVBox.setOpacity(0.5);
    // searchViewVBox.setDisable(true); // Inaktivera interaktion med bakgrunden
    }


    @FXML
    private void handleDialogBackAction() {
        detailDialogPane.setVisible(false);
        detailDialogPane.setManaged(false);
        currentMediaItemInDialog = null; // Rensa referens

        // Valfritt: Återställ bakgrunden
        // searchViewVBox.setOpacity(1.0);
        // searchViewVBox.setDisable(false);
    }

        @FXML
    private void handleDialogBorrowAction() {
        if (currentMediaItemInDialog != null) {
            System.out.println("Försöker låna: " + currentMediaItemInDialog.getTitle());
            // Här implementerar du logiken för att låna boken/mediet.
            // Detta kan involvera anrop till en LoanService eller LoanDAO.
            // Exempel:
            // boolean success = loanService.borrowMediaItem(loggedInUser, currentMediaItemInDialog);
            // if (success) {
            //     showAlert(Alert.AlertType.INFORMATION, "Utlånat!", currentMediaItemInDialog.getTitle() + " har lånats ut.");
            //     handleDialogBackAction(); // Stäng dialogen efter lyckat lån
            // } else {
            //     showAlert(Alert.AlertType.ERROR, "Misslyckades", "Kunde inte låna ut " + currentMediaItemInDialog.getTitle());
            // }
        }
}

}

