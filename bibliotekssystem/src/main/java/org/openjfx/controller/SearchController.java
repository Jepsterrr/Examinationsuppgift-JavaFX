package org.openjfx.controller;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openjfx.App;
import org.openjfx.service.LoanManager;
import org.openjfx.service.SearchService;
import org.openjfx.table.Copy;
import org.openjfx.table.MediaItem;
import org.openjfx.util.DetailHelper;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SearchController {

    private final SearchService service = new SearchService();

    @FXML
    private TextField searchBox;

    @FXML
    private ListView<MediaItem> listView;

    @FXML 
    private StackPane mainStackPane;

    @FXML
    private VBox searchViewVBox;

    @FXML 
    private VBox detailDialogPane;

    @FXML
    private Label dialogItemTitleLabel;

    @FXML
    private Label dialogItemAuthorLabel;

    @FXML
    private Label dialogItemYearLabel;

    @FXML
    private Label dialogItemTypeLabel;

    @FXML 
    private Label dialogItemISBNLabel;

    @FXML
    private Text dialogItemDescriptionText; 

    @FXML
    private Button dialogBorrowButton;

    @FXML 
    private Button dialogBackButton;

    @FXML
    private ComboBox keywordCombo;

    @FXML
    private Button copyBarcodeButton;

    private MediaItem currentMediaItemInDialog; // Antag att din ListView visar MediaItem-objekt

    private Copy currentRecommendedCopy;

    @FXML
    public void initialize() {
        // 1) Sätt upp listView-cellfactory
        listView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(MediaItem item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null
                        ? null
                        : item.getTitle() + item.getDetails());
            }
        });

        // 2) Hämta och visa alla mediaobjekt initialt
        refillAllItems();

        // 3) Dubbelklick för att visa detalj-dialog
        listView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && App.getCurrentUser() != null) {
                MediaItem selected = listView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    showDetailDialog(selected);
                }
            }
        });

        // 4) Fyll ComboBox med nyckelord
        try {
            List<String> allKeys = service.getAllKeywords();
            keywordCombo.getItems().setAll(allKeys);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // 5) Filtrera direkt vid val i ComboBox
        keywordCombo.setOnAction(evt -> applyKeywordFilter());

        // 6) Lyssna på textändring i sökrutan
        searchBox.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.isEmpty()) {
                refillAllItems();
            } else {
                updateSearch();
            }
        });
    }

    // Läser in alla mediaobjekt i listan
    private void refillAllItems() {
        try {
            List<MediaItem> all = service.getAll();
            listView.getItems().setAll(all);
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

            for (MediaItem item : hits) {
                System.out.println(item.getTitle() + item.getDetails());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void applyKeywordFilter() {
        String keyword = (String) keywordCombo.getValue();
        if (keyword == null || keyword.isEmpty()) {
            // Om inget nyckelord är valt, visa alla objekt
            refillAllItems();
            return;
        }

        try {
            List<MediaItem> filteredItems = service.searchAll(keyword);
            listView.getItems().setAll(filteredItems);
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
        System.out.println(item.getDetails());
        Map <String, String> detailsMap = new HashMap<>();
        detailsMap = DetailHelper.parseItemDetails(item.getDetails());
        // Fyll label-texterna med information från 'item'
        dialogItemTitleLabel.setText(item.getTitle());
        // Antag att MediaItem har getters för dessa:
        dialogItemAuthorLabel.setText("Författare: " + detailsMap.getOrDefault("Författare", "Okänd")); // Exempel
        dialogItemYearLabel.setText("Utgivningsår: " + detailsMap.getOrDefault("Utgivningsår", "Okänt")); // Exempel
        dialogItemTypeLabel.setText("Typ: " + detailsMap.getOrDefault("Typ", "Okänt")); // Exempel
        dialogItemISBNLabel.setText("ISBN: " + detailsMap.getOrDefault("ISBN", "Okänt")); // Exempel
        dialogItemDescriptionText.setText("Beskrivning: " + item.getDetails()); // Exempel
        
        detailDialogPane.setVisible(true);
        detailDialogPane.setManaged(true);

        copyBarcodeButton.setVisible(false);
        copyBarcodeButton.setManaged(false);

        // Gör bakgrunden (searchViewVBox) mindre framträdande
        searchViewVBox.setOpacity(0.5);
        searchViewVBox.setDisable(true); // Inaktivera interaktion med bakgrunden
    }


    @FXML
    private void handleDialogBackAction() {
        detailDialogPane.setVisible(false);
        detailDialogPane.setManaged(false);
        currentMediaItemInDialog = null; // Rensa referens

        // Återställ bakgrunden
        searchViewVBox.setOpacity(1.0);
        searchViewVBox.setDisable(false);
    }

    @FXML
    private void handleDialogBorrowAction() {
        if (currentMediaItemInDialog != null) {
            System.out.println("Försöker låna: " + currentMediaItemInDialog.getTitle());
            Copy suggestion = LoanManager.getAvailableObjects(currentMediaItemInDialog.getTitleId());
            currentRecommendedCopy = suggestion;
            System.out.println("TitelID: " + currentMediaItemInDialog.getTitleId());
            if (suggestion != null) {
                System.out.println("Förslag på exemplar: " + suggestion.getStreckkod());
                dialogItemDescriptionText.setText("Objekt med streckkod: " + suggestion.getStreckkod() + " är tillgänglig för utlån. Du hittar den i sektion " + suggestion.getPlatsId());
                if (copyBarcodeButton != null) {
                    copyBarcodeButton.setVisible(true);
                    copyBarcodeButton.setManaged(true);
                }
            } else {
                System.out.println("Inga tillgängliga exemplar för: " + currentMediaItemInDialog.getTitle());
                dialogItemDescriptionText.setText("Inga tillgängliga exemplar för: " + currentMediaItemInDialog.getTitle());
                if (copyBarcodeButton != null) {
                    copyBarcodeButton.setVisible(false);
                    copyBarcodeButton.setManaged(false);
                }
            }
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

    @FXML
    private void handleCopyBarcode() {
        if (currentRecommendedCopy != null && currentRecommendedCopy.getStreckkod() != null) {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(currentRecommendedCopy.getStreckkod());
            clipboard.setContent(content);

            // Ge feedback till användaren
            if (dialogItemDescriptionText != null) {
                dialogItemDescriptionText.setText("Streckkod '" + currentRecommendedCopy.getStreckkod() + "' kopierad till urklipp!");
                try {
                    // Vänta en stund för att låta användaren se meddelandet
                    //Thread.sleep(1000);
                    App.setRoot("CreateLoanView");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    
              
            } else { // Fallback om label inte finns
                new Alert(Alert.AlertType.INFORMATION, "Streckkod kopierad!").showAndWait();
            }
            // Du kan välja att dölja kopieraknappen igen efter klick eller låta den vara kvar
            // if (kopieraStreckkodButton != null) kopieraStreckkodButton.setDisable(true);

        } else {
            if (dialogItemDescriptionText != null) {
                dialogItemDescriptionText.setText("Ingen streckkod att kopiera.");
            } else {
                 new Alert(Alert.AlertType.WARNING, "Ingen streckkod tillgänglig att kopiera.").showAndWait();
            }
        }
    }

        private void setDialogToInitialDetailState() {
        // Visa ursprungliga detalj-labels
        if (dialogItemAuthorLabel != null) dialogItemAuthorLabel.setVisible(true);
        // ... (visa andra ursprungliga detail-labels om du dolt dem) ...
        if (dialogItemDescriptionText != null) dialogItemDescriptionText.setVisible(true);


        // Dölj rekommendations-label och rensa dess text
        if (dialogItemDescriptionText != null) {
            dialogItemDescriptionText.setText("");
            dialogItemDescriptionText.setVisible(false);
            dialogItemDescriptionText.setManaged(false);
        }

        // Visa "Kolla tillgänglighet"-knapp, dölj "Kopiera"-knapp
        if (dialogBorrowButton != null) {
            dialogBorrowButton.setVisible(true);
            dialogBorrowButton.setManaged(true);
            dialogBorrowButton.setDisable(false); // Se till att den är aktiv
        }
        if (copyBarcodeButton != null) {
            copyBarcodeButton.setVisible(false);
            copyBarcodeButton.setManaged(false);
        }
    }


}

