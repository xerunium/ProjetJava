package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Provider;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientCli {

    @Autowired
    private ClientService clientService;
    public void createClient(){
        String nom = IOUtils.readString("Entrez votre nom :", true);
        String prenom = IOUtils.readString("Entrez votre prenom :", true);
        String mail = IOUtils.readString("Entrez votre mail :", true);
        LocalDate naissance = IOUtils.readDate("Entrez votre date de naissance :", true);

        try {
            clientService.create(new Client(nom, prenom, mail, naissance));
        } catch (ServiceException e) {
            e.getMessage();
        } catch (DaoException e) {
            e.getMessage();
        }
    }

    public void findAll(){
        List<Client> list = new ArrayList<>();
        try {
            list = clientService.findAll();
        } catch (ServiceException e) {
            e.getMessage();
        }
        for (Client c : list){
            System.out.println(c);
        }
    }

}
