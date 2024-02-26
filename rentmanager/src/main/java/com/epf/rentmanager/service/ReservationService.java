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

import java.util.List;

public class ReservationService {

    private ReservationDao reservationDao;
    public static ReservationService instance;

    private ReservationService() {
        this.reservationDao = reservationDao.getInstance();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }

        return instance;
    }


    public long create(Reservation reservation) throws ServiceException, DaoException {
        try {
            if ((reservation.getClient_id()<1) || (reservation.getVehicule_id()<1)) {
                throw new ServiceException();
            }
            reservationDao.create(reservation);
        } catch (DaoException e) {
            throw new DaoException();
        }
        return reservation.getId();
    }


    public List<Reservation> findByClientId(long id) throws ServiceException {
        try {
            List<Reservation> list = reservationDao.findResaByClientId(id);
            for (Reservation c : list)
                if (c == null)
                    throw new ServiceException();
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
                if (c == null)
                    throw new ServiceException();
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
}