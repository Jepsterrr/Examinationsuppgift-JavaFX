package org.openjfx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.App;
import org.openjfx.dao.CopyDAO;
import org.openjfx.dao.LoanDAO;
import org.openjfx.dao.LoanItemDAO;
import org.openjfx.dao.LoanTypeDAO;
import org.openjfx.dao.MediaItemDAO;
import org.openjfx.dao.UserTypeDAO;
import org.openjfx.table.Copy;
import org.openjfx.table.Loan;
import org.openjfx.table.LoanType;
import org.openjfx.table.MediaItem;
import org.openjfx.table.User;


public class LoanManager {
    
    public static Loan createLoan(User user, String barcode) {
        // Implement logic to create a loan
        LocalDate loanDate = LocalDate.now();
        LocalDate duedate = null;
        int loanUserId = user.getLoanUserId();
        int loanId = 0;
        CopyDAO copyDAO = new CopyDAO();
        LoanTypeDAO loanTypeDAO = new LoanTypeDAO();
        LoanDAO loanDAO = new LoanDAO();
        try {
            Copy copy = copyDAO.get(barcode);
            int titelId = copy.getTitelId();
            int loantime = loanTypeDAO.getLoanTime(loanTypeDAO.getLoanTypeId(titelId));
            duedate = loanDate.plusDays(loantime);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // Skapa nytt lån i databasen
        //Placeholder på lokalt objekt för lån i lånid som -1 för att denna inte kommer användas ändå för display eller liknande
        Loan loan = new Loan(1, loanUserId, loanDate, duedate, null);
        try {
            loanDAO.add(loan);
            return loan;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getFreshLoanId() {
        // Implement logic to get a fresh loan ID
        LoanDAO loanDAO = new LoanDAO();
        List<Loan> loans = new ArrayList<>();
        try {
            loans = loanDAO.getAll();
            for (Loan loan : loans) {
                if (loan.getLanId() > 0) {
                    return loan.getLanId() + 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
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

    public boolean isEligibleForLoan(User user) {
        /* 
         * Varje låntagare har en begränsning för hur många objekt som får lånas samtidigt.
        Maxantalet beror på vilken kategori låntagaren tillhör (student, forskare, övriga
        universitetsanställda eller allmänheten)    
         */
        int loanTypeid = user.getloanTypeId();
        UserTypeDAO userTypeDAO = new UserTypeDAO();
        try {
            int maxLoan = userTypeDAO.getMaxLoans(loanTypeid);
            List<Loan> list = new ArrayList<>();
            List<Loan> listOfNotReturned = new ArrayList<>();
            LoanDAO loanDAO = new LoanDAO();
            list = loanDAO.findByUser(user.getLoanUserId());
            for (Loan loan : list) {
                if (loan.getReturnDate() == null) {
                    // Om lånet inte har ett returdatum, är det fortfarande aktivt
                    listOfNotReturned.add(loan);
                }

            }
            if (listOfNotReturned.size() >= maxLoan) {
                // Om låntagaren har fler än maxantalet lån, returnera false
                return false;
            } else {
                // Om låntagaren har färre än maxantalet lån, returnera true
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        

        return false;
        }
    }  
}

