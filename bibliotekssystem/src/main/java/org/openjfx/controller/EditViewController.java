package org.openjfx.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.openjfx.App;
import org.openjfx.dao.ArticleDAO;
import org.openjfx.dao.BookDAO;
import org.openjfx.dao.DvdDAO;
import org.openjfx.service.SearchService;
import org.openjfx.table.Article;
import org.openjfx.table.Book;
import org.openjfx.table.DVD;
import org.openjfx.table.MediaItem;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditViewController {

    // Tjänst för att hämta alla medieobjekt
    private final SearchService service = new SearchService();

    @FXML 
    private ListView<MediaItem> listView;

    @FXML 
    private VBox formBox;

    private MediaItem current;
    private final BookDAO bookDao = new BookDAO();
    private final DvdDAO  dvdDao = new DvdDAO();
    private final ArticleDAO artDao = new ArticleDAO();

    private TextField fldTitleId, fldTitle, fldLoanTypeId, fldExCount;
    private TextField fldIsbn, fldPages;
    private TextField fldDuration;
    private TextField fldArticlePages, fldJournal;

    @FXML
    public void initialize() {
        // 1) Ladda ListView med alla medieobjekt
        reloadList();

        // 2) Lägg till en lyssnare för att uppdatera formuläret när ett medieobjekt väljs
        listView.getSelectionModel().selectedItemProperty().addListener((obs, oldv, newv) -> {
            current = newv; // Uppdatera det aktuella medieobjektet
            buildForm(); // Bygg om formulärfälten dynamiskt
            if (newv != null) fillForm(newv); // Fyll i formuläret med det valda objektets data
        });

        // 3) Ställ in cellfabriken för ListView för att visa MediaItem-objekt
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
    }

    // Ladda om ListView med alla medieobjekt
    private void reloadList() {
        try {
            List<MediaItem> all = service.getAll(); // Hämta alla medieobjekt
            listView.getItems().setAll(all); // Uppdatera ListView
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Bygg dynamiskt formuläret baserat på typen av det aktuella medieobjektet
    private void buildForm() {
        // Ta bort gamla fält men behåll knappraden (sista elementet)
        Node buttons = formBox.getChildren().remove(formBox.getChildren().size() - 1);
        formBox.getChildren().clear();

        // Lägg till generella fält
        fldTitleId = addField("TitelId:");
        fldTitle = addField("Titel:");
        fldLoanTypeId = addField("LånetypId:");
        fldExCount = addField("Antal ex:");

        // Lägg till typ-specifika fält
        if (current instanceof Book) {
            fldIsbn = addField("ISBN:");
            fldPages = addField("Antal sidor:");
        } else if (current instanceof DVD) {
            fldDuration = addField("Längd (min):");
        } else if (current instanceof Article) {
            fldArticlePages = addField("Sidor:");
            fldJournal = addField("Tidskrift:");
        }

        // Lägg tillbaka knappraden
        formBox.getChildren().add(buttons);
    }

    // Hjälpmetod för att skapa en ny rad i formuläret och returnera TextField
    private TextField addField(String labelText) {
        Label lbl = new Label(labelText); // Skapa en etikett
        TextField tf = new TextField(); // Skapa ett textfält
        formBox.getChildren().addAll(lbl, tf); // Lägg till etiketten och textfältet i formuläret
        return tf;
    }

    // Fyll i formuläret med data från det aktuella medieobjektet
    private void fillForm(MediaItem m) {
        fldTitleId.setText(String.valueOf(m.getTitleId()));
        fldTitle.setText(m.getTitle());
        fldLoanTypeId.setText(String.valueOf(m.getLoanTypeId()));
        fldExCount.setText(String.valueOf(m.getAntalExemplar()));

        if (m instanceof Book) {
            Book b = (Book) m;
            fldIsbn.setText(b.getIsbn());
            fldPages.setText(String.valueOf(b.getNumberOfPages()));
        } else if (m instanceof DVD) {
            DVD d = (DVD) m;
            fldDuration.setText(String.valueOf(d.getDurationMinutes()));
        } else if (m instanceof Article) {
            Article a = (Article) m;
            fldArticlePages.setText(String.valueOf(a.getPages()));
            fldJournal.setText(a.getJournal());
        }
    }

    // Hantera klick på "Ny"-knappen för att rensa formuläret
    @FXML
    private void onNew() {
        current = null; // Rensa det aktuella medieobjektet
        buildForm(); // Bygg om formuläret
    }

    // Hantera klick på "Spara"-knappen för att spara eller uppdatera medieobjektet
    @FXML
    private void onSave() {
        try {
            if (current instanceof Book || current == null && fldIsbn != null) {
                // Skapa eller uppdatera en bok
                Book b = new Book(
                    parseInt(fldTitleId.getText()),
                    fldTitle.getText(),
                    parseInt(fldLoanTypeId.getText()),
                    parseInt(fldExCount.getText()),
                    fldIsbn.getText(),
                    parseInt(fldPages.getText()),
                    0
                );
                if (current == null) bookDao.add(b); // Lägg till ny bok
                else bookDao.update(b); // Uppdatera befintlig bok
            } else if (current instanceof DVD || current == null && fldDuration != null) {
                // Skapa eller uppdatera en DVD
                DVD d = new DVD(
                    parseInt(fldTitleId.getText()),
                    fldTitle.getText(),
                    parseInt(fldLoanTypeId.getText()),
                    parseInt(fldExCount.getText()),
                    parseInt(fldDuration.getText())
                );
                if (current == null) dvdDao.add(d); // Lägg till ny DVD
                else dvdDao.update(d); // Uppdatera befintlig DVD
            } else if (current instanceof Article || current == null && fldJournal != null) {
                // Skapa eller uppdatera en artikel
                Article a = new Article(
                    parseInt(fldTitleId.getText()),
                    fldTitle.getText(),
                    parseInt(fldLoanTypeId.getText()),
                    parseInt(fldExCount.getText()),
                    parseInt(fldArticlePages.getText()),
                    fldJournal.getText()
                );
                if (current == null) artDao.add(a); // Lägg till ny artikel
                else artDao.update(a); // Uppdatera befintlig artikel
            }
            reloadList(); // Ladda om ListView
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hantera klick på "Ta bort"-knappen för att ta bort det aktuella medieobjektet
    @FXML
    private void onDelete() {
        try {
            if (current != null) {
                int id = current.getTitleId();
                if (current instanceof Book) {
                    bookDao.delete(id); // Ta bort bok
                } else if (current instanceof DVD) {
                    dvdDao.delete(id); // Ta bort DVD
                } else if (current instanceof Article) {
                    artDao.delete(id); // Ta bort artikel
                }
                
                reloadList(); // Ladda om ListView
                onNew(); // Rensa formuläret
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hantera klick på "Tillbaka"-knappen för att navigera till AdminView
    @FXML
    private void onBack() throws IOException {
        App.setRoot("AdminView");
    }

    // Hjälpmetod för att säkert tolka ett heltal från en sträng
    private int parseInt(String s) {
        try { 
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0; // Returnera 0 om tolkningen misslyckas
        }
    }
}