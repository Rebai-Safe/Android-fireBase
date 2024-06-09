package com.example.projectif4firebase.models;

public class Prise {

    private String prise_id;
    private String date_debut;
    private String decription;
    private int heure;
    private float qte;

    public Prise() {
    }

    public Prise(String prise_id, String date_debut, String decription, int heure, float qte) {
        this.prise_id = prise_id;
        this.date_debut = date_debut;
        this.decription = decription;
        this.heure = heure;
        this.qte = qte;
    }

    public String getPrise_id() {
        return prise_id;
    }

    public void setPrise_id(String prise_id) {
        this.prise_id = prise_id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public float getQte() {
        return qte;
    }

    public void setQte(float qte) {
        this.qte = qte;
    }
}
