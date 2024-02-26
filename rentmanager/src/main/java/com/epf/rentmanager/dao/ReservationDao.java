package com.epf.rentmanager.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	public static ReservationDao getInstance() {
		if(instance == null) {
			instance = new ReservationDao();
		}
		return instance;
	}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATIONS_BY_ID = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";



	public long create(Reservation reservation) throws DaoException {
		long id = reservation.getId();
		long client_id = reservation.getClient_id(), vehicle_id = reservation.getVehicule_id();
		LocalDate debut = reservation.getDebut(), fin = reservation.getFin();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_RESERVATION_QUERY);
			pstmt.setLong(1, client_id);
			pstmt.setLong(2, vehicle_id);
			pstmt.setDate(3, Date.valueOf(debut));
			pstmt.setDate(4, Date.valueOf(fin));
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return id;
		}catch (SQLException e){
			throw new DaoException();
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		long id = reservation.getId();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION_QUERY);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return id;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		ArrayList<Reservation> list = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			pstmt.setLong(1, clientId);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Reservation(rset.getLong(1), clientId, rset.getLong(3), rset.getDate(4).toLocalDate(), rset.getDate(5).toLocalDate()));
			}
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException();
		}
		return list;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		ArrayList<Reservation> list = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			pstmt.setLong(1, vehicleId);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Reservation(rset.getLong(1), rset.getLong(2),vehicleId, rset.getDate(4).toLocalDate(), rset.getDate(5).toLocalDate()));
			}
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException();
		}
		return list;
	}

	public List<Reservation> findAll() throws DaoException {
		ArrayList<Reservation> list = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_QUERY);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Reservation(rset.getLong(1), rset.getLong(2), rset.getLong(3), rset.getDate(4).toLocalDate(), rset.getDate(5).toLocalDate()));
			}
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException();
		}
		return list;
	}

	public Reservation findByID(long id) throws DaoException{
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_ID);
			pstmt.setLong(1, id);
			ResultSet rset = pstmt.executeQuery();
			Reservation res = new Reservation(rset.getLong(1), rset.getLong(2), rset.getLong(3), rset.getDate(4).toLocalDate(), rset.getDate(5).toLocalDate());
			pstmt.close();
			conn.close();
			return res;
		}catch (SQLException e){
			throw new DaoException();
		}
	}
}