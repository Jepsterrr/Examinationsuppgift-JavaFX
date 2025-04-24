package org.openjfx.table;

public class Article extends MediaItem {
    private int pages;
    private String journal;
    private boolean onLoan;

    public Article(int titleId, String title, int loanTypeId, int antalExemplar,
                   int pages, String journal, boolean onLoan) {
        super(titleId, title, loanTypeId, antalExemplar);
        this.pages = pages;
        this.journal = journal;
        this.onLoan = onLoan;
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

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    @Override
    public String getDetails() {
        return "Sidor: " + pages + ", Tidskrift: " + journal + (onLoan ? " [Utlånad]" : " [Tillgänglig]");
    }
}