package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;
    @Autowired
    private ReservationService(ReservationDao resDao) {
        this.reservationDao = resDao;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {
        try {
            if ((reservation.getClient_id()<1) || (reservation.getVehicule_id()<1)) {
                throw new ServiceException("Problem Service");
            }
            if(!reservationDao.verifyDateResa(reservation)){
                reservationDao.create(reservation);
            }
        } catch (DaoException e) {
            throw new ServiceException();
        }
        return reservation.getId();
    }

    public long delete(Reservation reservation) throws ServiceException, DaoException{
        long id_client = 0;
        try {
            id_client = reservationDao.delete(reservation);
        }
        catch(DaoException e){
            throw new ServiceException();
        }
        return id_client;
    }

    public List<Reservation> findByClientId(long id) throws ServiceException {
        try {
            List<Reservation> list = reservationDao.findResaByClientId(id);
            for (Reservation c : list) {
                if (c == null) {
                    throw new ServiceException();
                }
            }
            return list;
        } catch (DaoException e) {
            e.getMessage();
        }
        return null;

    }

    public List<Reservation> findByVehicleId(long id) throws ServiceException {
        try {
            List<Reservation> list = reservationDao.findResaByVehicleId(id);
            for (Reservation c : list)
                if (c == null) {
                    throw new ServiceException();
                }
            return list;
        } catch (DaoException e) {
            e.getMessage();
        }
        return null;
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            List<Reservation> list = reservationDao.findAll();
            for (Reservation c : list)
                if (c == null)
                    throw new ServiceException();
            return list;
        } catch (DaoException e) {
            e.getMessage();
        }
        return null;
    }

    public void delete(long id){
        try {
             reservationDao.delete(reservationDao.findByID(id));
        } catch (DaoException e) {
            e.getMessage();
        }
    }

    public int count(){
        try{
            return reservationDao.count();
        } catch (DaoException e) {
            e.getMessage();
            return 0;
        }
    }

    public int countByClientID(long clientid){
        try {
            return reservationDao.countResaByClientId(clientid);
        } catch (DaoException e) {
            e.getMessage();
        }
        return -1;
    }

    public int countVehicleByClientID(long clientid) throws ServiceException{
        try {
            return reservationDao.countVehicleByClientId(clientid);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public Reservation findById(long resId) throws ServiceException {
        try{
            Reservation reservation = reservationDao.findByID(resId);
            if(reservation!=null) {
                return reservation;
            }
            throw new ServiceException();
        }catch(DaoException e){
            e.getMessage();
        }
        return null;
    }

    public int countByVehicleID(long vehicleId){
        try {
            return reservationDao.countResaByVehicleId(vehicleId);
        } catch (DaoException e) {
            e.getMessage();
        }
        return -1;
    }

    public int countClientByVehicleID(long vehicleID) throws ServiceException{
        try {
            return reservationDao.countClientByVehicleId(vehicleID);
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }
}