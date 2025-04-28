package org.openjfx.table;

public class Book extends MediaItem {
    private String isbn;
    private int numberOfPages;
    private int publisherId;

    public Book(int titleId, String title, int loanTypeId, int antalExemplar, 
                String isbn, int numberOfPages, int publisherId) {
        super(titleId, title, loanTypeId, antalExemplar);
        this.numberOfPages = numberOfPages;
        this.publisherId = publisherId;
        this.isbn = isbn;
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

    @Override
    public String getDetails() {
        return "ISBN: " + isbn + ", Antal sidor: " + numberOfPages + ", Förlag ID: " + publisherId;
    }
}
