package com.example.projectif4firebase.models;

public class Programme {


    private String pgr_id;
    private String date_debut;
    private int duree;
    private String maladie;

    public Programme() {

    }

    public Programme(String identifier,String date_debut, int duree, String maladie) {
        this.pgr_id = identifier;
        this.date_debut = date_debut;
        this.duree = duree;
        this.maladie = maladie;
    }

    public String getPgr_id() {
        return pgr_id;
    }

    public void setPgr_id(String pgr_id) {
        this.pgr_id = pgr_id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getMaladie() {
        return maladie;
    }

    public void setMaladie(String maladie) {
        this.maladie = maladie;
    }

}
