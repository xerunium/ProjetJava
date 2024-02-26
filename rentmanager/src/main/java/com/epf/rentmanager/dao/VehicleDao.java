package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;

public class VehicleDao {
	
	private static VehicleDao instance = null;
	private VehicleDao() {}
	public static VehicleDao getInstance() {
		if(instance == null) {
			instance = new VehicleDao();
		}
		return instance;
	}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, nb_places FROM Vehicle;";
	private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM vehicle;";


	public long create(Vehicle vehicle) throws DaoException {
		long id = vehicle.getId();
		String constructeur = vehicle.getConstructeur();
		int nb = vehicle.getNb_places();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_VEHICLE_QUERY);
			pstmt.setString(1, constructeur);
			pstmt.setInt(2, nb);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return id;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		long id = vehicle.getId();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_VEHICLE_QUERY);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return id;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public Vehicle findById(long id) throws DaoException {
		String constructeur, modele;
		int nb;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_QUERY);
			pstmt.setLong(1, id);
			ResultSet rset = pstmt.executeQuery();
			constructeur = rset.getString(1);
			modele = rset.getString(2);
			nb = rset.getInt(3);
			pstmt.close();
			conn.close();
		}catch(SQLException e){
			throw new DaoException();
		}
		return new Vehicle(constructeur, modele, nb);
	}

	public List<Vehicle> findAll() throws DaoException {
		ArrayList<Vehicle> list = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLES_QUERY);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Vehicle(rset.getString(2), rset.getString(3), rset.getInt(4)));
			}
			pstmt.close();
			conn.close();
		}catch (SQLException e){
			throw new DaoException();
		}
		return list;
		
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
			throw new DaoException();
		}
		return nb;
	}
	

}
