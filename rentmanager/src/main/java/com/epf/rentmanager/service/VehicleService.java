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
			if ((vehicle.getConstructeur() == null) || (vehicle.getNb_places()<= 1)) {
				throw new ServiceException();
			}
			vehicleDao.create(vehicle);
		}
		catch(DaoException e){
			throw new DaoException();
		}
		return vehicle.getId();
	}

	public Vehicle findById(long id) throws ServiceException {
		try{
			Vehicle vehicle = vehicleDao.findById(id);
			if(vehicle!=null)
				return vehicle;
			throw new ServiceException();
		}catch(DaoException e){
			e.getMessage();
		}
		return null;
	}

	public List<Vehicle> findAll() throws ServiceException {
		try{
			List<Vehicle> list = vehicleDao.findAll();
			for (Vehicle c : list)
				if(c ==null)
					throw new ServiceException();
			return list;
		}catch(DaoException e){
			e.getMessage();
		}
		return null;
		
	}

	public int count(){
		try{
			return vehicleDao.count();
		} catch (DaoException e) {
            e.getMessage();
			return 0;
        }
    }
	
}
