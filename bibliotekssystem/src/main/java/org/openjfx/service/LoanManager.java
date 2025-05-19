package org.openjfx.service;

import java.util.ArrayList;
import java.util.List;

import org.openjfx.dao.CopyDAO;
import org.openjfx.dao.LoanItemDAO;
import org.openjfx.dao.LoanTypeDAO;
import org.openjfx.dao.MediaItemDAO;
import org.openjfx.table.Copy;
import org.openjfx.table.Loan;
import org.openjfx.table.MediaItem;


public class LoanManager {
    
    public static Loan createLoan(int loanUserId, String barcode) {
        // Implement logic to create a loan

        // 
        return null;
    }


    public static Copy getAvailableObjects(int titelId) {
        // Först ta fram alla exemplar av titeln
        // och sedan kolla om något av dem är tillgängligt
        CopyDAO copyDAO = new CopyDAO();
    
        LoanTypeDAO loanTypeDAO = new LoanTypeDAO();
        List<Copy> copies = new ArrayList<>();
        try {
            //Hämta lånetypen för titeln
            int loanTypeid = loanTypeDAO.getLoanTypeId(titelId);
            //Om lånetiden för typen är 0, returneras null
            if (loanTypeDAO.getLoanTime(loanTypeid) == 0) {
                return null;
            } else {
            copies = copyDAO.findByTitleId(titelId);
            System.out.println(copies);
            for (Copy copy : copies) {
            if (!copy.isUtlanad() ) {
                return copy;
            }
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
        
      
        
    }

    public boolean isEligibleForLoan(int userId) {
        // Implement logic to check if the loan is eligible
        /* 
         * Varje låntagare har en begränsning för hur många objekt som får lånas samtidigt.
        Maxantalet beror på vilken kategori låntagaren tillhör (student, forskare, övriga
        universitetsanställda eller allmänheten)    
         */
        return false;
    }  
}

