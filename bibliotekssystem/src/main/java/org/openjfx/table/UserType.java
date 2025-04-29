package org.openjfx.table;

public class UserType {
    private int anvandarId;
    private String typNamn;
    private int maxAntalLan;

    public UserType(int anvandarId, String typNamn, int maxAntalLan) {
        this.anvandarId = anvandarId;
        this.typNamn = typNamn;
        this.maxAntalLan = maxAntalLan;
    }

    public int getAnvandarId() {
        return anvandarId;
    }

    public void setAnvandarId(int anvandarId) {
        this.anvandarId = anvandarId;
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