package org.openjfx.table;

public class Book {
    // Kika mer på ifall alla tabeller osv behövs så den matchar databasen
    private int titleId;
    private String title;
    private String isbn;
    private int numberOfPages;
    private int publisherId;
    private boolean onLoan;

    public Book(int titleId, String title, String isbn, int numberOfPages, int publisherId, boolean onLoan) {
        this.titleId = titleId;
        this.title = title;
        this.numberOfPages = numberOfPages;
        this.publisherId = publisherId;
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

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
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
