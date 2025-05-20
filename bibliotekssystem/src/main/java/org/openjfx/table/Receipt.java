package org.openjfx.table;

import java.time.LocalDate;

public class Receipt {
    int receiptId = 0;
    int loanUserId = 0;
    int lanId = 0;
    LocalDate date;

    public Receipt(int loanUserId, int lanId, LocalDate date) {
        this.loanUserId = loanUserId;
        this.lanId = lanId;
        this.date = date;
    }

    public int getlanId(int receiptId) {
        return this.lanId;
    }
}
