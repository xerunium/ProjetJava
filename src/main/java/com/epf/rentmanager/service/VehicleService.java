package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance;

	@Autowired
	private VehicleService(VehicleDao vDao) {
		this.vehicleDao = vDao;
	}

	public long create(Vehicle vehicle) throws ServiceException, DaoException {
		try {
			if (vehicle.getConstructeur().isEmpty()) {
				throw new ServiceException("Le véhicule doit avoir un constructeur.");
			}
			if (vehicle.getModele().isEmpty()) {
				throw new ServiceException("Le véhicule doit avoir un model.");
			}
			if ((vehicle.getNb_places() < 2) || (vehicle.getNb_places() > 9)) {
				throw new ServiceException("Erreur de service");
			}
			return vehicleDao.create(vehicle);
		}
		catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public long delete(Vehicle vehicle) throws ServiceException, DaoException{
		long id_vehicle = 0;
		try {
			id_vehicle = vehicleDao.delete(vehicle);
		}
		catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
		return id_vehicle;
	}

	public Vehicle findById(long id) throws ServiceException {
		try{
			Vehicle vehicle = vehicleDao.findById(id);
			if(vehicle!=null) {
				return vehicle;
			}
			throw new ServiceException("Erreur de service");
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}

	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			List<Vehicle> list = vehicleDao.findAll();
			for (Vehicle c : list)
				if(c ==null)
					throw new ServiceException("Erreur de service");
			return list;
		}catch(DaoException e){
			throw new ServiceException(e.getMessage());
		}
	}

	public int count() throws ServiceException {
		try{
			return vehicleDao.count();
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
        }
    }
	
}
