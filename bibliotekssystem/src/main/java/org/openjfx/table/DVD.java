package org.openjfx.table;

public class DVD extends MediaItem {
    private int durationMinutes;

    public DVD(int titleId, String title, int loanTypeId, int antalExemplar, String creatorNames,
               int durationMinutes) {
        super(titleId, title, loanTypeId, antalExemplar, creatorNames);
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
        return String.format(
            ", Speltid: %d min, Medverkande: %s",
            durationMinutes,
            getCreatorNames() != null ? getCreatorNames() : ""
        );
    }
}