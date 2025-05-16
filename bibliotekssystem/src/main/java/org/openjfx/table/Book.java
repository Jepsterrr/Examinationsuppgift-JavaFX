package org.openjfx.table;

public class Book extends MediaItem {
    private String isbn;
    private int numberOfPages;
    private int publisherId;
    private String publisherName;

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

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Override
    public String getDetails() {
        return String.format(
            ", ISBN: %s, Sidor: %d, Förlag: %s, Författare: %s",
            isbn,
            numberOfPages,
            publisherName != null ? publisherName : "",
            getCreatorNames() != null  ? getCreatorNames()  : ""
        );
    }
}
