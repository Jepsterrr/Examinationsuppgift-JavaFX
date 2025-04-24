package org.openjfx.table;

public class DVD extends MediaItem {
    private int durationMinutes;
    private boolean onLoan;

    public DVD(int titleId, String title, int loanTypeId, int antalExemplar,
               int durationMinutes, boolean onLoan) {
        super(titleId, title, loanTypeId, antalExemplar);
        this.durationMinutes = durationMinutes;
        this.onLoan = onLoan;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isOnLoan() {
        return onLoan;
    }

    public void setOnLoan(boolean onLoan) {
        this.onLoan = onLoan;
    }

    @Override
    public String getDetails() {
        return "Speltid: " + durationMinutes + " min" + (onLoan ? " [Utlånad]" : " [Tillgänglig]");
    }
}