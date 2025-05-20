package org.openjfx.controller;

import org.openjfx.App;
import org.openjfx.service.LoanManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateLoanController {
    @FXML
    private TextField barcodeInput;

    @FXML
    private Label feedbackLabel;

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
            loanManager.createLoan(App.getCurrentUser(), barcode);
            updateFeedback("Lån skapat för: " + barcode, "green");
            barcodeInput.clear();

        } catch (Exception e) {
            updateFeedback("" + e.getMessage(), "red");
        }
    }

    private void updateFeedback(String message, String color) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
