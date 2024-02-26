package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO vehicle(constructeur, modele, nb_places) VALUES(?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_BY_ID_QUERY = "SELECT id, constructeur, modele, nb_places FROM vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
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
			PreparedStatement pstmt = conn.prepareStatement(FIND_VEHICLE_BY_ID_QUERY);
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
		List<Vehicle> vehicles = new ArrayList<>();
		try (Connection connexion = ConnectionManager.getConnection();
			 PreparedStatement preparedStatement = connexion.prepareStatement(FIND_VEHICLES_QUERY)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String constructeur = resultSet.getString("constructeur");
				String modele = resultSet.getString("modele");
				int nb_place = resultSet.getInt("nb_places");
				vehicles.add(new Vehicle(id, constructeur, modele, nb_place));
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return vehicles;
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
