package com.epf.rentmanager.model;

public class Vehicle {
    private static int nextID = 1; // Initialise le premier ID à 1
    private long id;
    private String constructeur;
    private String modele;
    private int nb_places;

    public Vehicle(){}

    @java.lang.Override
    public java.lang.String toString() {
        return "Véhicule{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", modele='" + modele + '\'' +
                ", nb_places=" + nb_places +
                '}';
    }

    public Vehicle(String constructeur, String modele, int nb_places) {
        this.id = nextID;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
        nextID++;
    }

    public long getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public int getNb_places() {
        return nb_places;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }
}

