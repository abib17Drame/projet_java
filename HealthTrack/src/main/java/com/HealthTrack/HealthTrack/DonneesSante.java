package com.HealthTrack.HealthTrack;

public class DonneesSante {
    private int pas;
    private int eau;
    private double poids;
    private double sommeil;

    public DonneesSante(int pas, int eau, double poids, double sommeil) {
        this.pas = pas;
        this.eau = eau;
        this.poids = poids;
        this.sommeil = sommeil;
    }

    public int getPas() {
        return pas;
    }

    public int getEau() {
        return eau;
    }

    public double getPoids() {
        return poids;
    }

    public double getSommeil() {
        return sommeil;
    }
}
