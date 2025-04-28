package org.openjfx.table;

public class DVD extends MediaItem {
    private int durationMinutes;

    public DVD(int titleId, String title, int loanTypeId, int antalExemplar,
               int durationMinutes) {
        super(titleId, title, loanTypeId, antalExemplar);
        this.durationMinutes = durationMinutes;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    @Override
    public String getDetails() {
        return "Speltid: " + durationMinutes + " min";
    }
}