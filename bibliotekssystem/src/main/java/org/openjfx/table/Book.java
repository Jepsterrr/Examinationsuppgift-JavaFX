package org.openjfx.table;

public class Book {
    // Kika mer på ifall alla tabeller osv behövs så den matchar databasen
    private int titleId;
    private String title;
    private String isbn;
    private boolean onLoan;

    public Book(int titleId, String title, String isbn, boolean onLoan) {
        this.titleId = titleId;
        this.title = title;
        this.isbn = isbn;
        this.onLoan = onLoan;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    @Override
    public String toString() {
        return title + "ISBN: (" + isbn + ")" + (onLoan ? " [Utlånad]" : "  [Tillgänglig]");
    }
}
