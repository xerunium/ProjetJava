package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException, DaoException {
		try {
			if ((client.getNom() == null) || (client.getPrenom() == null)) {
				throw new ServiceException();
			}
			client.setNom(client.getNom().toUpperCase());
			clientDao.create(client);
		}
		catch(DaoException e){
			throw new DaoException();
		}
		return client.getID();
	}

	public Client findById(long id) throws ServiceException {
		try{
			Client client = clientDao.findById(id);
			if(client !=null)
				return client;
			throw new ServiceException();
		}catch(DaoException e){
			e.getMessage();
		}
		return null;
	}

	public List<Client> findAll() throws ServiceException {
		try{
			List<Client> list = clientDao.findAll();
			for (Client c : list)
				if(c ==null)
					throw new ServiceException();
			return list;
		}catch(DaoException e){
			e.getMessage();
		}
		return null;
	}
	
}
