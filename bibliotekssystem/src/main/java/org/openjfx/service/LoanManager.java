package org.openjfx.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.openjfx.dao.CopyDAO;
import org.openjfx.dao.LoanDAO;
import org.openjfx.dao.LoanItemDAO;
import org.openjfx.dao.LoanTypeDAO;
import org.openjfx.dao.UserTypeDAO;
import org.openjfx.table.Copy;
import org.openjfx.table.Loan;
import org.openjfx.table.LoanItem;
import org.openjfx.table.User;


public class LoanManager {
    
    public Loan createLoan(User user, String barcode) {
        try {
            CopyDAO copyDAO = new CopyDAO();
            LoanTypeDAO loanTypeDAO = new LoanTypeDAO();
            LoanDAO loanDAO = new LoanDAO();
            LoanItemDAO loanItemDAO = new LoanItemDAO();

            // Valideringar
            Copy copy = copyDAO.get(barcode);
            if (copy == null) throw new IllegalArgumentException("Exemplaret finns inte");
            if (copy.isUtlanad()) throw new IllegalStateException("Exemplaret är redan utlånat");
            if (!isEligibleForLoan(user)) throw new IllegalStateException("Max antal lån uppnått");

            // Beräkna lånedatum
            int loanTypeId = loanTypeDAO.getLoanTypeId(copy.getTitelId());
            int loanTime = loanTypeDAO.getLoanTime(loanTypeId);
            if (loanTime <= 0) throw new IllegalStateException("Kan inte låna denna typ av material");

            // Skapa lån
            Loan loan = new Loan(
                -1, // Autogenereras av databasen
                user.getLoanUserId(),
                LocalDate.now(),
                LocalDate.now().plusDays(loanTime),
                null
            );
            loanDAO.add(loan); // Får autogenererat ID från databasen

            // Uppdatera exemplarstatus
            copy.setUtlanad(true);
            copyDAO.update(copy);

            // Skapa låneföremål
            LoanItem loanItem = new LoanItem(
                -1, // Autogenereras av databasen
                loan.getLanId(),
                barcode,
                null,
                loan.getDueDate()
            );
            loanItemDAO.add(loanItem);

            return loan;

        } catch (Exception e) {
            throw new RuntimeException("Lånefel: " + e.getMessage(), e);
        }
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

    public static boolean isEligibleForLoan(User user) {
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
            // Returnera true om låntagaren har färre än maxantalet lån, annars false
            return listOfNotReturned.size() < maxLoan;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    } 

    public void returnLoan(User user, String barcode) {
        try {
            CopyDAO copyDAO = new CopyDAO();
            LoanItemDAO loanItemDAO = new LoanItemDAO();
            LoanDAO loanDAO = new LoanDAO();

            // Validera exemplar
            Copy copy = copyDAO.get(barcode);
            if (copy == null) {
                throw new IllegalArgumentException("Exemplaret finns inte");
            }

            // Validera att lånet är aktivt
            List<LoanItem> loanItems = loanItemDAO.findByBarcode(barcode).stream()
                .filter(li -> li.getReturnedDate() == null)
                .collect(Collectors.toList());
                
            if (loanItems.isEmpty()) {
                throw new IllegalStateException("Inget aktivt lån för detta exemplar");
            }

            for (LoanItem loanItem : loanItems) {
                Loan loan = loanDAO.get(loanItem.getLanId());
                if (loan == null) {
                    throw new IllegalStateException("Lån information saknas");
                }

                // Verifiera användare
                if (loan.getLantagarId() != user.getLoanUserId()) {
                    throw new SecurityException("Du har inte behörighet att returnera detta lån");
                }

                // Kontrollera om redan returnerat
                if (loan.getReturnDate() != null) {
                    throw new IllegalStateException("Exemplaret är redan återlämnat");
                }

                // Sätt returdatum
                LocalDate today = LocalDate.now();
                loan.setReturnDate(today);
                loanDAO.update(loan);

                loanItem.setReturnedDate(today);
                loanItemDAO.update(loanItem);
            }

            // Uppdatera exemplarstatus
            copy.setUtlanad(false);
            copyDAO.update(copy);

        } catch (SecurityException | IllegalArgumentException | IllegalStateException e) {
            // Skicka vidare specifika undantag direkt
            throw e;}
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

