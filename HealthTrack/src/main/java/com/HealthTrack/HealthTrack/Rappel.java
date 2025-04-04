package com.HealthTrack.HealthTrack;

import java.sql.Time;

public class Rappel {
    private int id;
    private int idUtilisateur;
    private String description;
    private Time heure;

    public Rappel(int id, int idUtilisateur, String description, Time heure) {
        this.id = id;
        this.idUtilisateur = idUtilisateur;
        this.description = description;
        this.heure = heure;
    }

    public int getId() {
        return id;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public String getDescription() {
        return description;
    }

    public Time getHeure() {
        return heure;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeure(Time heure) {
        this.heure = heure;
    }
}
