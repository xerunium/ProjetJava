package com.epf.rentmanager.service;

import java.time.LocalDate;
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


    public long create(Reservation reservation) throws ServiceException {
        try {
            LocalDate debut = reservation.getDebut();
            LocalDate fin = reservation.getFin();
            if (debut.plusDays(7).isBefore(fin)) {
                throw new ServiceException("Une reservation ne peut pas durer plus de 7 jours");
            }
            if (fin.isBefore(debut)) {
                throw new ServiceException("Le début ne peut pas être après la fin");
            }
            if ((reservation.getClient_id()<1) || (reservation.getVehicule_id()<1)) {
                throw new ServiceException("Problem Service");
            }
            if(!reservationDao.verifyDateResa(reservation) && !reservationDao.verifierReservationConsecutives(reservation)){
                reservationDao.create(reservation);
                return reservation.getId();
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return 0;
    }

    public long delete(Reservation reservation) throws ServiceException, DaoException{
        long id_client = 0;
        try {
            id_client = reservationDao.delete(reservation);
        }
        catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
        return id_client;
    }

    public List<Reservation> findByClientId(long id) throws ServiceException {
        try {
            List<Reservation> list = reservationDao.findResaByClientId(id);
            return list;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findByVehicleId(long id) throws ServiceException {
        try {
            List<Reservation> list = reservationDao.findResaByVehicleId(id);
            return list;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return reservationDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Erreur lors de la recherche des réservations.");
        }
    }

    public void delete(long id) throws ServiceException {
        try {
             reservationDao.delete(reservationDao.findByID(id));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int count() throws ServiceException {
        try{
            return reservationDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int countByClientID(long clientid) throws ServiceException {
        try {
            return reservationDao.countResaByClientId(clientid);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int countVehicleByClientID(long clientid) throws ServiceException{
        try {
            return reservationDao.countVehicleByClientId(clientid);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Reservation findById(long resId) throws ServiceException {
        try{
            Reservation reservation = reservationDao.findByID(resId);
            return reservation;
        }catch(DaoException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public int countByVehicleID(long vehicleId) throws ServiceException {
        try {
            return reservationDao.countResaByVehicleId(vehicleId);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int countClientByVehicleID(long vehicleID) throws ServiceException{
        try {
            return reservationDao.countClientByVehicleId(vehicleID);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}