package org.openjfx.service;

import java.time.LocalDate;
import org.openjfx.table.Receipt;


public class ReceiptManager {
    
public Receipt genereateReceipt(int loanId, int loanUserId)  {
    LocalDate date = LocalDate.now();
    Receipt receipt = new Receipt(loanUserId, loanId,date);
    return receipt;
}
        
}
