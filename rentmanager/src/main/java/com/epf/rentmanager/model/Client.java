package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Client {
    private static int nextID = 1; // Initialise le premier ID à 1
    private long ID;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate naissance;

    public Client(){

    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Client{" +
                "ID=" + ID +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }

    public Client(String nom, String prenom, String mail, LocalDate naissance){
        this.ID = nextID;
        this.nom = nom;
        this.prenom = prenom;
        this.email = mail;
        this.naissance = naissance;
        nextID++;
    }

    public Client(long ID, String nom, String prenom, String email, LocalDate naissance) {
        this.ID = ID;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.naissance = naissance;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public long getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}