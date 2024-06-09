package com.example.projectif4firebase.models;

public class Temperature {

    private String temp_id;
    private float degres;

    public Temperature() {
    }

    public Temperature(String temp_id, float degres) {
        this.temp_id = temp_id;
        this.degres = degres;
    }

    public String getTemp_id() {
        return temp_id;
    }

    public void setTemp_id(String temp_id) {
        this.temp_id = temp_id;
    }

    public float getDegres() {
        return degres;
    }

    public void setDegres(float degres) {
        this.degres = degres;
    }
}
