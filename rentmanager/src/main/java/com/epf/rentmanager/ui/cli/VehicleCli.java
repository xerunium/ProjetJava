package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehicleCli {

    @Autowired
    private VehicleService vehicleService;
    public void createVehicle(){
        String constructeur = IOUtils.readString("Entrez le constructeur :", true);
        String modele = IOUtils.readString("Entrez le modele :", true);
        int nb = IOUtils.readInt("Entrez le nombre de place :");

        try {
            vehicleService.create(new Vehicle(constructeur, modele, nb));
        } catch (ServiceException e) {
            e.getMessage();
        } catch (DaoException e) {
            e.getMessage();
        }
    }

    public void findAll(){
        List<Vehicle> list = new ArrayList<>();
        try {
            list =vehicleService.findAll();
        } catch (ServiceException e) {
            e.getMessage();
        }
        for (Vehicle c : list){
            System.out.println(c);
        }
    }
}
