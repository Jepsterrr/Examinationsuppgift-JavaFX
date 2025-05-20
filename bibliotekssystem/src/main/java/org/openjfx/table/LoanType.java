package org.openjfx.table;

public enum LoanType {
    NORMAL_BOOK(1000, "Vanlig bok (30 dagar)"),
    COURSE_BOOK(1001, "Kurslitteratur (14 dagar)"),
    DVD(1002, "DVD (7 dagar)"),
    ARTICLE(1003, "Artikel (0 dagar)"),
    REFERENCE(1004, "Referenslitteratur (0 dagar)");

    private final int id;
    private final String displayName;

    LoanType(int id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return displayName;
    }

    public static LoanType fromId(int id) {
        for (LoanType lt : values()) {
            if (lt.id == id) return lt;
        }
        return null;
    }
}