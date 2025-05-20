package org.openjfx.table;

public class UserType {
    private int anvandartypId;
    private String typNamn;
    private int maxAntalLan;

    public UserType(int anvandarId, String typNamn, int maxAntalLan) {
        this.anvandartypId = anvandarId;
        this.typNamn = typNamn;
        this.maxAntalLan = maxAntalLan;
    }

    public int getAnvandarId() {
        return anvandartypId;
    }

    public void setAnvandarId(int anvandarId) {
        this.anvandartypId = anvandarId;
    }

    public String getTypNamn() {
        return typNamn;
    }

    public void setTypNamn(String typNamn) {
        this.typNamn = typNamn;
    }

    public int getMaxAntalLan() {
        return maxAntalLan;
    }

    public void setMaxAntalLan(int maxAntalLan) {
        this.maxAntalLan = maxAntalLan;
    }
}