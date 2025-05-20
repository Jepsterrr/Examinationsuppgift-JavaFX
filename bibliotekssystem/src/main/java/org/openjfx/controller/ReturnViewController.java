package org.openjfx.controller;

import org.openjfx.App;
import org.openjfx.service.LoanManager;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ReturnViewController {
    @FXML
    private TextField barcodeInput;

    @FXML
    private Label feedbackLabel;

    @FXML
    private void initReturn() {
        // Påbörja återlämning
        String barcode = barcodeInput.getText();
        feedbackLabel.setVisible(true);

        try {
            if (barcode.isEmpty()) {
                throw new IllegalArgumentException("Ange en gilltig streckkod");
            }

            // Returnera lånet
            LoanManager loanManager = new LoanManager();
            loanManager.returnLoan(App.getCurrentUser(), barcode);
            updateFeedback("Lån återlämnat för: " + barcode, "green");
            barcodeInput.clear();

        } catch (RuntimeException e) {
            handleLoanManagerException(e);
        }  catch (Exception e) {
            updateFeedback("Ett oväntat fel inträffade: " + e.getMessage(), "red");
        }
    }

    private void handleLoanManagerException(RuntimeException e) {
        Throwable rootCause = e;
        while (rootCause.getCause() != null) {
            rootCause = rootCause.getCause();
        }

        if (rootCause instanceof SecurityException) {
            updateFeedback(rootCause.getMessage(), "red");
        } else if (rootCause instanceof IllegalArgumentException) {
            updateFeedback(rootCause.getMessage(), "red");
        } else if (rootCause instanceof IllegalStateException) {
            updateFeedback(rootCause.getMessage(), "red");
        } else {
            updateFeedback("Ett systemfel inträffade: " + rootCause.getMessage(), "red");
        }
    }

    private void updateFeedback(String message, String color) {
        feedbackLabel.setText(message);
        feedbackLabel.setStyle("-fx-text-fill: " + color + ";");
    }
}
