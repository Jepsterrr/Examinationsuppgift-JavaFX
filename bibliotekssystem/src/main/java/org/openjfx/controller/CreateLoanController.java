package org.openjfx.controller;

import org.openjfx.App;
import org.openjfx.service.LoanManager;
import org.openjfx.table.Loan;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CreateLoanController {
    @FXML
    private TextField barcodeInput;
    private Button loanButton;
    
    

    @FXML 
    private void initLoan() {
        // Initialize the loan process
        String barcode = barcodeInput.getText();
        if (barcode != null && !barcode.isEmpty()) {
            Loan createdLoan = LoanManager.createLoan(App.getCurrentUser(), barcode);
            if (createdLoan != null) {
                // Successfully created loan
                System.out.println("Loan created successfully for barcode: " + barcode);
            } else {
                // Failed to create loan
                System.out.println("Failed to create loan for barcode: " + barcode);
            }
            System.out.println("Loan created for barcode: " + barcode);
        } else {
            System.out.println("Please enter a valid barcode.");
        }
    }
}
