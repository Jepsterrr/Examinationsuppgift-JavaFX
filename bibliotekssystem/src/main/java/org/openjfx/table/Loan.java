package org.openjfx.table;

import java.time.LocalDate;

public class Loan {
    private int lanId;
    private int lantagarId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(int lanId, int lantagarId, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.lanId = lanId;
        this.lantagarId = lantagarId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public int getLanId() { 
        return lanId;
    }

    public void setLanId(int lanId) {
        this.lanId = lanId;
    }

    public int getLantagarId() {
        return lantagarId;
    }
    public void setLantagarId(int lantagarId) {
        this.lantagarId = lantagarId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}