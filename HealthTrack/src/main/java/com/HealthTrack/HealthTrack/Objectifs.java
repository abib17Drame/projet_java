package com.HealthTrack.HealthTrack;

public class Objectifs {
    private int objectifPas;
    private int objectifEau;
    private double objectifPoids;
    private double objectifSommeil;

    public Objectifs(int objectifPas, int objectifEau, double objectifPoids, double objectifSommeil) {
        this.objectifPas = objectifPas;
        this.objectifEau = objectifEau;
        this.objectifPoids = objectifPoids;
        this.objectifSommeil = objectifSommeil;
    }

    public int getObjectifPas() {
        return objectifPas;
    }

    public int getObjectifEau() {
        return objectifEau;
    }

    public double getObjectifPoids() {
        return objectifPoids;
    }

    public double getObjectifSommeil() {
        return objectifSommeil;
    }

    public void setObjectifPas(int objectifPas) {
        this.objectifPas = objectifPas;
    }

    public void setObjectifEau(int objectifEau) {
        this.objectifEau = objectifEau;
    }

    public void setObjectifPoids(double objectifPoids) {
        this.objectifPoids = objectifPoids;
    }

    public void setObjectifSommeil(double objectifSommeil) {
        this.objectifSommeil = objectifSommeil;
    }
}
