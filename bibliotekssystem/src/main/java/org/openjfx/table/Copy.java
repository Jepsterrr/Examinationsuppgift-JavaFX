package org.openjfx.table;

public class Copy {
    private String streckkod;
    private int platsId;
    private int titelId;
    private boolean utlanad;
    private boolean referenslitteratur;

    public Copy(String streckkod, int platsId, int titelId, boolean utlanad, boolean referenslitteratur) {
        this.streckkod = streckkod;
        this.platsId = platsId;
        this.titelId = titelId;
        this.utlanad = utlanad;
        this.referenslitteratur = referenslitteratur;
    }

    public String getStreckkod() {
        return streckkod;
    }

    public void setStreckkod(String streckkod) {
        this.streckkod = streckkod;
}

    public int getPlatsId() {
        return platsId;
    }

    public void setPlatsId(int platsId) {
        this.platsId = platsId;
    }

    public int getTitelId() { 
        return titelId;
    }

    public void setTitelId(int titelId) {
        this.titelId = titelId;
    }

    public boolean isUtlanad() {
        return utlanad;
    }

    public void setUtlanad(boolean utlanad) {
        this.utlanad = utlanad;
    }

    public boolean isReferenslitteratur() {
        return referenslitteratur;
    }

    public void setReferenslitteratur(boolean referenslitteratur) {
        this.referenslitteratur = referenslitteratur;
    }
}