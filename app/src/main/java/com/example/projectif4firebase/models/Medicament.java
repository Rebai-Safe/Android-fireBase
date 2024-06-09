package com.example.projectif4firebase.models;

public class Medicament {

    private String id_med;
    private String ref_med;
    private String date_debut_conso;
    private int duree;

    //required for db operation
    public Medicament() {
    }

    public Medicament(String id_med, String ref_med, String date_debut_conso, int duree) {
        this.id_med = id_med;
        this.ref_med = ref_med;
        this.date_debut_conso = date_debut_conso;
        this.duree = duree;
    }

    public String getRef_med() {
        return ref_med;
    }

    public void setRef_med(String ref_med) {
        this.ref_med = ref_med;
    }

    public String getDate_debut_conso() {
        return date_debut_conso;
    }

    public void setDate_debut_conso(String date_debut_conso) {
        this.date_debut_conso = date_debut_conso;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }


    public String getId_med() {
        return id_med;
    }

    public void setId_med(String id_med) {
        this.id_med = id_med;
    }
}
