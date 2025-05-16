package org.openjfx.controller;

import java.sql.SQLException;
import java.util.List;

import org.openjfx.dao.LoanDAO;
import org.openjfx.table.Loan;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
// Importer för TextField, LoanItemDAO, SearchService, MediaItem, LoanItem är borttagna då de inte används i denna minimala version.

public class LoanOverview {

    private final LoanDAO loanDao = new LoanDAO(); // Endast LoanDAO behövs för att hämta lån

    @FXML
    private ListView<Loan> listView; // Din ListView som ska visa lånen

    // searchBox, updateSearch, checkEmpty är borttagna.
    // Om du har ett TextField med fx:id="searchBox" i din FXML och inte längre behöver det,
    // bör du ta bort det från FXML-filen eller kommentera bort @FXML-fältet här om det finns kvar.

    @FXML
    public void initialize() {
        // Konfigurera hur varje rad (Loan-objekt) i ListView ska se ut
        listView.setCellFactory(lv -> new ListCell<Loan>() {
            @Override
            protected void updateItem(Loan loan, boolean empty) {
                super.updateItem(loan, empty);
                if (empty || loan == null) {
                    setText(null); // Tom rad om det inte finns något lån att visa
                } else {
                    // Bygg en sträng med information från Loan-objektet
                    // Du kan anpassa detta precis som du vill.
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

        // Ladda in alla lån från databasen och visa dem i ListView
        loadAllLoans();
    }

    /**
     * Hämtar alla lån från LoanDAO och uppdaterar ListView.
     */
    private void loadAllLoans() {
        try {
            List<Loan> allLoans = loanDao.getAll(); // Hämta listan med alla lån
            listView.getItems().setAll(allLoans);   // Uppdatera ListView med de hämtade lånen
        } catch (SQLException e) {
            e.printStackTrace(); // Logga felet
            // För en bättre användarupplevelse, visa ett felmeddelande i gränssnittet,
            // t.ex. med en Alert-dialog.
            listView.getItems().clear(); // Töm listan vid fel
            // Du kan också sätta ett platshållar-meddelande i ListView:
            // listView.setPlaceholder(new Label("Kunde inte ladda lån från databasen."));
            System.err.println("Ett fel uppstod vid hämtning av lån: " + e.getMessage());
        }
    }
}