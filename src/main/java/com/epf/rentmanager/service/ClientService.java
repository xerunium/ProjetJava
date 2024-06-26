package com.epf.rentmanager.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;

	@Autowired
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}

	public long create(Client client) throws ServiceException, DaoException {
		try {
			if (client.getNom().length() < 3 || client.getPrenom().length() < 3) {
				throw new ServiceException("Erreur Service");
			}
			if ((client.getNom() == null) || (client.getPrenom() == null)) {
				throw new ServiceException("Erreur Service");
			}
			LocalDate dateActuelle = LocalDate.now();

			// Calcul de la période entre la date de naissance et la date actuelle
			Period periode = Period.between(client.getNaissance(), dateActuelle);
			// Récupération de l'âge en années
			int age = periode.getYears();
			if(age<18) {
				throw new ServiceException("erreur Service");
			}
			client.setNom(client.getNom().toUpperCase());
			if(!clientDao.verifyMail(client.getEmail())) {
				clientDao.create(client);
			}
			else{
				throw new ServiceException("Erreur Service");
			}
		}
		catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return client.getID();
	}

	public long delete(Client client) throws ServiceException, DaoException{
		long id_client = 0;
		try {
			id_client = clientDao.delete(client);
		}
		catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return id_client;
	}

	public Client findById(long id) throws ServiceException {
		try{
			Client client = clientDao.findById(id);
			if(client !=null)
				return client;
			throw new ServiceException("Erreur Service");

		}catch(DaoException e){
			throw new ServiceException("Erreur Service");
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			List<Client> list = clientDao.findAll();
			for (Client c : list)
				if (c == null)
					throw new ServiceException("Erreur Service");
			return list;
		} catch (DaoException e) {
			throw new ServiceException("Erreur Service");
		}
	}

	public int count() throws ServiceException {
		try{
			return clientDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
