package org.openjfx.table;

public abstract class MediaItem {
    private int titleId;
    private String title;
    private int loanTypeId;
    private int antalExemplar;
    private String creatorNames;

    public MediaItem(int titleId, String title, int loanTypeId, int antalExemplar) {
        this.titleId = titleId;
        this.title = title;
        this.loanTypeId = loanTypeId;
        this.antalExemplar = antalExemplar;
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

    public int getLoanTypeId() {
        return loanTypeId;
    }

    public void setLoanTypeId(int loanTypeId) {
        this.loanTypeId = loanTypeId;
    }

    public int getAntalExemplar() {
        return antalExemplar;
    }

    public void setAntalExemplar(int antalExemplar) {
        this.antalExemplar = antalExemplar;
    }

    public String getCreatorNames() {
        return creatorNames;
    }

    public void setCreatorNames(String creatorNames) {
        this.creatorNames = creatorNames;
    }

    public abstract String getDetails();
}