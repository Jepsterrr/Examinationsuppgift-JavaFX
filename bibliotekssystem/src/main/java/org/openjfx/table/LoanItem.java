package org.openjfx.table;

import java.time.LocalDate;

public class LoanItem {
    private int loanItemId;
    private int lanId;
    private String streckkod;
    private LocalDate returnedDate;
    private LocalDate dueDate;

    public LoanItem(int loanItemId, int lanId, String streckkod, LocalDate returnedDate, LocalDate dueDate) {
        this.loanItemId = loanItemId;
        this.lanId = lanId;
        this.streckkod = streckkod;
        this.returnedDate = returnedDate;
        this.dueDate = dueDate;
    }

    public int getLoanItemId() {
        return loanItemId;
    }

    public void setLoanItemId(int loanItemId) {
        this.loanItemId = loanItemId;
    }

    public int getLanId() {
        return lanId;
    }

    public void setLanId(int lanId) {
        this.lanId = lanId;
    }

    public String getStreckkod() {
        return streckkod;
    }

    public void setStreckkod(String streckkod) {
        this.streckkod = streckkod;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}