package org.openjfx.table;

public class Article extends MediaItem {
    private int pages;
    private String journal;

    public Article(int titleId, String title, int loanTypeId, int antalExemplar, String creatorNames,
                   int pages, String journal) {
        super(titleId, title, loanTypeId, antalExemplar, creatorNames);
        this.pages = pages;
        this.journal = journal;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    @Override
    public String getDetails() {
        return String.format(
            ", Sidor: %d, Tidskrift: %s, Författare: %s",
            pages,
            journal,
            getCreatorNames() != null ? getCreatorNames() : ""
        );
    }
}