package org.openjfx.controller;

import java.time.LocalDate;

import org.openjfx.App;
import org.openjfx.service.LoanManager;
import org.openjfx.table.Loan;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateLoanController {
    @FXML
    private TextField barcodeInput;

    @FXML
    private Label feedbackLabel;

    @FXML
    private VBox receiptBox;

    @FXML
    private Label loanUserIdLabel;

    @FXML
    private Label loanIdLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label dueDateLabel;

    @FXML 
    private void initLoan() {
        // Påbörja lånprocessen
        String barcode = barcodeInput.getText();
        feedbackLabel.setVisible(true);

        try {
            if (barcode.isEmpty()) {
                throw new IllegalArgumentException("Ange en streckkod");
            }

            LoanManager loanManager = new LoanManager();
            Loan createdLoanId = loanManager.createLoan(App.getCurrentUser(), barcode);;
            updateFeedback("Lån skapat för: " + barcode, "green");
            barcodeInput.clear();

            showReceipt(createdLoanId);

        } catch (Exception e) {
            updateFeedback("" + e.getMessage(), "red");
        }
    }

    private void updateFeedback(String message, String color) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: " + color + ";");
    }

    private void showReceipt(Loan loan) {
        loanIdLabel.setText(String.valueOf(loan.getLanId()));
        loanUserIdLabel.setText(String.valueOf(loan.getLantagarId()));
        dateLabel.setText(LocalDate.now().toString());
        dueDateLabel.setText(loan.getDueDate().toString());
        receiptBox.setVisible(true);
    }
}
