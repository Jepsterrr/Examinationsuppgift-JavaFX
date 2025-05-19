package org.openjfx.controller;

import java.sql.SQLException;
import java.util.ArrayList; // Importera ArrayList
import java.util.List;

import org.openjfx.dao.LoanDAO;
import org.openjfx.dao.LoanItemDAO; // Importera LoanItemDAO
import org.openjfx.table.LoanItem;
import org.openjfx.table.Loan;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label; // Importera Label för placeholder-text

public class LoanOverview {

    private final LoanDAO loanDao = new LoanDAO();
    private final LoanItemDAO loanItemDao = new LoanItemDAO(); // Ny instans av LoanItemDAO

    @FXML
    private ListView<Loan> loanListView;

    @FXML
    private ListView<LoanItem> loanItemListView;

    @FXML
    public void initialize() {
        // Konfigurera den vänstra ListView (loanListView) för att visa Lån
        if (loanListView != null) {
            loanListView.setCellFactory(lv -> new ListCell<Loan>() {
                @Override
                protected void updateItem(Loan loan, boolean empty) {
                    super.updateItem(loan, empty);
                    if (empty || loan == null) {
                        setText(null);
                    } else {
                        String loanDateStr = (loan.getLoanDate() != null) ? loan.getLoanDate().toString() : "Okänt lånedatum";
                        String dueDateStr = (loan.getDueDate() != null) ? loan.getDueDate().toString() : "Inget förfallodatum";
                        String returnDateStr = (loan.getReturnDate() != null) ? "Återlämnad: " + loan.getReturnDate().toString() : "Ej återlämnad";
                        
                        setText("Lån ID: " + loan.getLanId() +
                                " | Låntagare ID: " + loan.getLantagarId() +
                                " | Lånat: " + loanDateStr +
                                " | Förfaller: " + dueDateStr +
                                " | Status: " + returnDateStr);
                    }
                }
            });
            loadAllLoans();
        } else {
            System.err.println("FEL: loanListView är null i initialize(). Kontrollera fx:id i FXML-filen och @FXML-annoteringen i controllern.");
        }

        // Grundläggande konfiguration för den högra ListView (loanItemListView)
        if (loanItemListView != null) {
            loanItemListView.setCellFactory(lv -> new ListCell<LoanItem>() {
                @Override
                protected void updateItem(LoanItem item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        String dueDateText = item.getDueDate() != null ? item.getDueDate().toString() : "N/A";
                        // Antag att LoanItem har en metod för att hämta t.ex. bokens titel via streckkoden,
                        // eller så behöver du hämta den informationen separat om det behövs.
                        // För nu visar vi bara streckkoden.
                        setText("Streckkod: " + item.getStreckkod() + " | Returneras senast: " + dueDateText);
                    }
                }
            });
            loanItemListView.setPlaceholder(new Label("Välj ett lån från listan till vänster för att se dess låneobjekt."));
        } else {
            System.err.println("FEL: loanItemListView är null i initialize(). Kontrollera fx:id i FXML-filen och @FXML-annoteringen i controllern.");
        }

        // Sätt upp lyssnaren för val i loanListView
        setupLoanSelectionListener();
    }

    private void loadAllLoans() {
        if (loanListView == null) {
             System.err.println("FEL: loanListView är null i loadAllLoans(). Kan inte ladda lån.");
            return;
        }
        try {
            List<Loan> allLoans = loanDao.getAll();
            loanListView.getItems().setAll(allLoans);
        } catch (SQLException e) {
            e.printStackTrace();
            loanListView.getItems().clear();
            System.err.println("Ett fel uppstod vid hämtning av lån: " + e.getMessage());
            // Här kan du också visa ett felmeddelande för användaren, t.ex. i en Alert-dialog
        }
    }

    /**
     * Sätter upp en lyssnare på loanListView för att uppdatera loanItemListView
     * när ett lån väljs.
     */
    private void setupLoanSelectionListener() {
        if (loanListView != null && loanItemListView != null) {
            loanListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    // Ett nytt lån har valts
                    try {
                        List<LoanItem> items = loanItemDao.findByLoan(newSelection.getLanId());
                        loanItemListView.getItems().setAll(items);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        // Hantera felet, t.ex. visa ett meddelande och töm listan
                        loanItemListView.getItems().clear();
                        loanItemListView.setPlaceholder(new Label("Kunde inte ladda låneobjekt för valt lån."));
                        System.err.println("Fel vid hämtning av låneobjekt för lån ID " + newSelection.getLanId() + ": " + e.getMessage());
                        // Överväg att visa en Alert-dialog för användaren här
                    }
                } else {
                    // Inget lån är valt (eller urvalet har rensats)
                    loanItemListView.getItems().clear();
                    loanItemListView.setPlaceholder(new Label("Välj ett lån från listan till vänster för att se dess låneobjekt."));
                }
            });
        }
    }
}