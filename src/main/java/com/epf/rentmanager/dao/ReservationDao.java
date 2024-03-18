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
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String FIND_RESERVATIONS_BY_ID = "SELECT client_id, vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM Reservation;";
	private static final String COUNT_CLIENT_ID = "SELECT COUNT(*) AS count FROM Reservation WHERE client_id = ?";
	private static final String COUNT_UNIQUE_VEHICLE = "SELECT COUNT(DISTINCT vehicle_id) AS nombre_voitures FROM Reservation WHERE client_id = ?;";
	private static final String VERIFY_DATE_RESERVATION = "SELECT COUNT(*) AS nombre_de_reservations FROM Reservation WHERE vehicle_id = ? AND NOT (debut > ? OR fin < ?);";
	private static final String VERIFY_30DAYS = "SELECT COUNT(*) AS nombre_de_reservations FROM (SELECT *, LEAD(debut) OVER (ORDER BY debut) AS prochain_debut FROM Reservation WHERE vehicle_id = ?) AS reservations WHERE DATEDIFF(prochain_debut, debut) >= 30;";
	private static final String COUNT_VEHICLE_ID = "SELECT COUNT(*) AS count FROM Reservation WHERE vehicle_id = ?";
	private static final String COUNT_UNIQUE_CLIENT = "SELECT COUNT(DISTINCT client_id) AS nombre_clients FROM Reservation WHERE vehicle_id = ?;";
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
			throw new DaoException("Problème DAO");
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
			throw new DaoException("Problème DAO");
		}
	}

	
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		ArrayList<Reservation> list = new ArrayList<>();
		try (
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);)
		{
			pstmt.setLong(1, clientId);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Reservation((long) rset.getInt(1), clientId,(long) rset.getInt(2), rset.getDate(3).toLocalDate(), rset.getDate(4).toLocalDate()));
			}
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
		}
		return list;
	}
	
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		ArrayList<Reservation> list = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			pstmt.setInt(1, (int)vehicleId);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Reservation((long) rset.getInt(1), (long) rset.getInt(2), vehicleId, rset.getDate(3).toLocalDate(), rset.getDate(4).toLocalDate()));
			}
			System.out.println(list);
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
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
			throw new DaoException("Problème DAO");
		}
		return list;
	}

	public Reservation findByID(long id) throws DaoException{
		try (
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_RESERVATIONS_BY_ID);)
		{
			pstmt.setInt(1,(int) id);
			ResultSet rset = pstmt.executeQuery();
			if(rset.next()) {
				Reservation res = new Reservation(id, rset.getLong(1), rset.getLong(2), rset.getDate(3).toLocalDate(), rset.getDate(4).toLocalDate());
				return res;
			}
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
		}
		return null;
	}

	public int count() throws DaoException {
		int nb = 0;
		try (
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(COUNT_QUERY);
				ResultSet rset = pstmt.executeQuery();)
		{
			if (rset.next()){
				nb = rset.getInt("count");
			}
		}catch(SQLException e){
			throw new DaoException("Problème DAO");
		}
		return nb;
	}

	public int countResaByClientId(long clientId) throws DaoException {
		int count = -1;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_CLIENT_ID);
			pstmt.setInt(1, (int) clientId);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()){
				count = rset.getInt("count");
			}
			rset.close();
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
		}
		return count;
	}

	public int countVehicleByClientId(long clientId) throws DaoException{
		int count = -1;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_UNIQUE_VEHICLE);
			pstmt.setInt(1, (int) clientId);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()){
				count = rset.getInt("nombre_voitures");
			}
			rset.close();
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
		}
		return count;
	}

	public boolean verifyDateResa(Reservation reservation) throws DaoException{
		int nb = 0;
		try (
				Connection conn = ConnectionManager.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(VERIFY_DATE_RESERVATION);)
		{
			pstmt.setInt(1, (int) reservation.getVehicule_id());
			pstmt.setDate(2, Date.valueOf(reservation.getFin()));
			pstmt.setDate(3, Date.valueOf(reservation.getDebut()));
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()){
				nb = rset.getInt("nombre_de_reservations");
			}
		}catch(SQLException e){
			throw new DaoException("Problem DAO");
		}
		return nb>0;
	}

	public int countResaByVehicleId(long vehicleId) throws DaoException {
		int count = -1;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_VEHICLE_ID);
			pstmt.setInt(1, (int) vehicleId);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()){
				count = rset.getInt("count");
			}
			rset.close();
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
		}
		return count;
	}

	public int countClientByVehicleId(long vehicleId) throws DaoException{
		int count = -1;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(COUNT_UNIQUE_CLIENT);
			pstmt.setInt(1, (int) vehicleId);
			ResultSet rset = pstmt.executeQuery();
			if (rset.next()){
				count = rset.getInt("nombre_clients");
			}
			rset.close();
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException("Problème DAO");
		}
		return count;
	}

}
