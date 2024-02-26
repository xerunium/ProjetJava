package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationCli {

    @Autowired
    private ReservationService reservationService;
    public void createReservation(){
        long id_client = IOUtils.readInt("Entrez l'ID client :");
        long id_vehicle = IOUtils.readInt("Entrez l'ID vehicule :");
        LocalDate debut = IOUtils.readDate("Entrez la date de début :", true);
        LocalDate fin = IOUtils.readDate("Entrez la date de fin :", true);

        try {
            reservationService.create(new Reservation(id_client, id_vehicle, debut, fin));
        } catch (ServiceException e) {
            e.getMessage();
        } catch (DaoException e) {
            e.getMessage();
        }
    }

    public void deleteReservation(){
        long id_res = IOUtils.readInt("Entrez l'ID reservation :");
        reservationService.delete(id_res);
    }

    public void findAll(){
        List<Reservation> list = new ArrayList<>();
        try {
            list = reservationService.findAll();
        } catch (ServiceException e) {
            e.getMessage();
        }
        for (Reservation c : list){
            System.out.println(c);
        }
    }

}

