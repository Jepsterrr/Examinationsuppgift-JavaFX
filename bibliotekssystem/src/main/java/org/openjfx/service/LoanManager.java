package org.openjfx.service;

import java.util.ArrayList;
import java.util.List;

import org.openjfx.dao.CopyDAO;
import org.openjfx.table.Copy;
import org.openjfx.table.Loan;

public class LoanManager {
    
    public static Loan createLoan(int loanUserId, String barcode) {
        // Implement logic to create a loan

        // 
        return null;
    }


    public Copy makeSuggestionForCopy() {

        CopyDAO copyDAO = new CopyDAO();
        List<Copy> copies = new ArrayList<>();
        try {
            copies = copyDAO.getAll();
            for (Copy copy : copies) {
            if (!copy.isUtlanad()) {
                return copy;
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
        
      
        
    }

    private boolean isEligibleForLoan(int userId) {
        // Implement logic to check if the loan is eligible
        /* 
         * Varje låntagare har en begränsning för hur många objekt som får lånas samtidigt.
        Maxantalet beror på vilken kategori låntagaren tillhör (student, forskare, övriga
        universitetsanställda eller allmänheten)    
         */
        return false;
    }
}

