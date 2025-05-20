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
import org.openjfx.table.LoanType;
import org.openjfx.table.MediaItem;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class EditViewController {

    @FXML 
    private ChoiceBox<String> typeSelector;

    @FXML 
    private ListView<MediaItem> listView;
    
    @FXML
    private VBox formBox;

    @FXML
    private Button btnNew, btnSave, btnDelete, btnBack;

    @FXML
    private Label infoLabel;

    private MediaItem current;

    private final SearchService service = new SearchService();
    private final BookDAO bookDao = new BookDAO();
    private final DvdDAO dvdDao = new DvdDAO();
    private final ArticleDAO articleDao = new ArticleDAO();

    // Dynamiskt skapade fält
    private TextField fldTitleId, fldTitle, fldExCount, fldCreatorNames;
    private ChoiceBox<LoanType> loanTypeSelector;
    private TextField fldIsbn, fldPages, fldPublisherId;
    private TextField fldDuration;
    private TextField fldArticlePages, fldJournal;

    @FXML
    public void initialize() throws SQLException {
        // 1) Initiera typväljare och lånetypväljare
        typeSelector.getItems().addAll("Book","DVD","Article");
        typeSelector.getSelectionModel().selectFirst();
        typeSelector.getSelectionModel().selectedItemProperty().addListener((obs,oldType,newType)-> {
            buildForm();
            if (current != null) {
                fillForm(current);
            }
        });

        loanTypeSelector = new ChoiceBox<>();
        loanTypeSelector.getItems().addAll(LoanType.values());
        loanTypeSelector.getSelectionModel().selectFirst();

        // 2) Initiera listView
        reloadList();
        listView.getSelectionModel().selectedItemProperty().addListener((obs,oldItem,newItem)->{
            current = newItem;
            // sätt rätt typ i selector
            if (newItem instanceof Book) {
                typeSelector.setValue("Book");
            }
            else if (newItem instanceof DVD) {
                typeSelector.setValue("DVD");
            }
            else if (newItem instanceof Article) {
                typeSelector.setValue("Article");
            }
            buildForm();
            if (newItem!=null) fillForm(newItem);
        });
        listView.setCellFactory(lv->new ListCell<>() {
            @Override
            protected void updateItem(MediaItem it, boolean empty) {
                super.updateItem(it,empty);
                setText(empty||it==null?null:it.getTitle()+it.getDetails());
            }
        });

        // 3) Bygg formuläret första gången
        buildForm();

        // 4) Initiera knappar
        btnNew.setOnMouseEntered(e -> infoLabel.setText("Rensa formuläret och skapa ett nytt tomt objekt, välj typ uppe i vänstra hörnet"));
        btnSave.setOnMouseEntered(e -> infoLabel.setText("Spara ändringar (nytt eller uppdaterat objekt) till databasen"));
        btnDelete.setOnMouseEntered(e -> infoLabel.setText("Ta bort valt objekt från databasen"));
        btnBack.setOnMouseEntered(e -> infoLabel.setText("Gå tillbaka till administrationsvyn utan att spara"));

        // Töm infoLabel när musen lämnar knappen
        btnNew.setOnMouseExited(e -> infoLabel.setText(""));
        btnSave.setOnMouseExited(e -> infoLabel.setText(""));
        btnDelete.setOnMouseExited(e -> infoLabel.setText(""));
        btnBack.setOnMouseExited(e -> infoLabel.setText(""));
    }

    private void reloadList() {
        try {
            List<MediaItem> items = service.getAll();
            listView.getItems().setAll(items);
        } catch (SQLException sqlException) {
            System.err.println("Error: " + sqlException.getMessage());
        }
    }

    private void buildForm() {
        // Rensa gamla fält
        formBox.getChildren().clear();

        // Allmänna fält
        fldTitleId = addField("TitelId:");
        fldTitle = addField("Titel:");
        fldExCount = addField("Antal exemplar:");
        fldCreatorNames = addField("Kreatör:");

        // Lånetyp
        Label ltLabel = new Label("Lånetyp:");
        formBox.getChildren().addAll(ltLabel, loanTypeSelector);

        // Typ-specifika fält
        switch(typeSelector.getValue()) {
            case "Book":
                fldIsbn = addField("ISBN:");
                fldPages = addField("Antal sidor:");
                fldPublisherId = addField("Förlags-Id:");
                break;
            case "DVD":
                fldDuration = addField("Längd (min):");
                break;
            case "Article":
                fldArticlePages = addField("Sidor:");
                fldJournal = addField("Tidskrift:");
                break;
        }
    }

    private TextField addField(String labelTxt) {
        Label lbl = new Label(labelTxt);
        TextField tf = new TextField();
        formBox.getChildren().addAll(lbl, tf);
        return tf;
    }

    private void fillForm(MediaItem m) {
        fldTitleId.setText(String.valueOf(m.getTitleId()));
        fldTitle.setText(m.getTitle());
        fldExCount.setText(String.valueOf(m.getAntalExemplar()));
        fldCreatorNames.setText(m.getCreatorNames());

        LoanType lt = LoanType.fromId(m.getLoanTypeId());
        if (lt != null) {
            loanTypeSelector.getSelectionModel().select(lt);
        }

        if (m instanceof Book) {
            Book b = (Book) m;
            fldIsbn.setText(b.getIsbn());
            fldPages.setText(String.valueOf(b.getNumberOfPages()));
            fldPublisherId.setText(String.valueOf(b.getPublisherId()));
        } else if (m instanceof DVD) {
            DVD d = (DVD) m;
            fldDuration.setText(String.valueOf(d.getDurationMinutes()));
        } else if (m instanceof Article) {
            Article a = (Article) m;
            fldArticlePages.setText(String.valueOf(a.getPages()));
            fldJournal.setText(a.getJournal());
        }
    }

    @FXML private void onNew() {
        current = null;
        buildForm();
    }

    @FXML private void onBack() throws IOException {
        App.setRoot("AdminView");
    }
    
    @FXML private void onSave() {
        try {
            int id = Integer.parseInt(fldTitleId.getText().trim());
            String title = fldTitle.getText();
            int loanTypeId = loanTypeSelector.getValue().getId();
            int exCount = Integer.parseInt(fldExCount.getText().trim());

            switch (typeSelector.getValue()) {
                case "Book":
                    Book b = new Book(id, title, loanTypeId, exCount, fldCreatorNames.getText(), fldIsbn.getText(), parseInt(fldPages.getText()), parseInt(fldPublisherId.getText()));
                    if (current == null) {
                        bookDao.add(b);
                    } else {
                        bookDao.update(b);
                    }
                    break;
                case "DVD":
                    DVD d = new DVD(id, title, loanTypeId, exCount, fldCreatorNames.getText(), parseInt(fldDuration.getText()));
                    if (current == null) {
                        dvdDao.add(d);
                    } else {
                        dvdDao.update(d);
                    }
                    break;
                case "Article":
                    Article a = new Article(id, title, loanTypeId, exCount, fldCreatorNames.getText(), parseInt(fldArticlePages.getText()), fldJournal.getText());
                    if (current == null) {
                        articleDao.add(a);
                    } else {
                        articleDao.update(a);
                    }
                    break;
            }
            reloadList();
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }

    @FXML private void onDelete() {
        try {
            if(current!=null) {
                int id = current.getTitleId();
                switch(typeSelector.getValue()) {
                    case "Book":
                        bookDao.delete(id);
                        break;
                    case "DVD":
                        dvdDao.delete(id);
                        break;
                    case "Article":
                        articleDao.delete(id);
                        break;
                }
                reloadList();
                onNew();
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private int parseInt(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch(Exception e) { return 0; }
    }
}