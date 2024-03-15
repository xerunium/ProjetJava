package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Reservation {

    private static int nextid = 1; // Initialise le premier ID à 1
    private long id;
    private long client_id;
    private long vehicule_id;
    private LocalDate debut;
    private LocalDate fin;
    private String vehicleName;
    private String clientName;
    public Reservation(){}

    public Reservation(long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.id = nextid;
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin;

        nextid++;
    }

    public Reservation(long id, long client_id, long vehicule_id, LocalDate debut, LocalDate fin) {
        this.id = id;
        this.client_id = client_id;
        this.vehicule_id = vehicule_id;
        this.debut = debut;
        this.fin = fin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public long getVehicule_id() {
        return vehicule_id;
    }

    public void setVehicule_id(long vehicule_id) {
        this.vehicule_id = vehicule_id;
    }

    public LocalDate getDebut() {
        return debut;
    }

    public void setDebut(LocalDate debut) {
        this.debut = debut;
    }

    public LocalDate getFin() {
        return fin;
    }

    public void setFin(LocalDate fin) {
        this.fin = fin;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", vehicule_id=" + vehicule_id +
                ", debut=" + debut +
                ", fin=" + fin +
                ", vehicleName='" + vehicleName + '\'' +
                ", clientName='" + clientName + '\'' +
                '}';
    }
}
