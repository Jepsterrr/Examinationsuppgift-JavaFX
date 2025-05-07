package org.openjfx.service;

public class ISBNFormatter {
    public static String formatISBN(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("Indata kan inte vara null.");
        }
        // Få fram endast siffror
        String cleanIsbn = isbn.replaceAll("[\\s-]", "");

        if (cleanIsbn.length() != 13) {
            throw new IllegalArgumentException("Ogiltig längd på ISBN. Måste vara 13 siffror lång.");
        }

        // Kolla så att användaren isåfall bara matat in siffror
        if (!cleanIsbn.matches("\\d+")) {
            throw new IllegalArgumentException("ISBN får endast innehålla siffror.");
        }

        // Formatera ISBN-numret
        return cleanIsbn.substring(0, 3) + "-" +
               cleanIsbn.substring(3, 5) + "-" +
               cleanIsbn.substring(5, 10) + "-" +
               cleanIsbn.substring(10, 12) + "-" +
               cleanIsbn.substring(12, 13);
    }
}
