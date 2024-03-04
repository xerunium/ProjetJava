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
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {

	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_QUERY = "SELECT COUNT(*) AS count FROM Client;";
	public long create(Client client) throws DaoException {
		long id = client.getID();
		String nom = client.getNom();
		String prenom = client.getPrenom();
		String email = client.getEmail();
		LocalDate naissance = client.getNaissance();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(CREATE_CLIENT_QUERY);
			pstmt.setString(1, nom);
			pstmt.setString(2, prenom);
			pstmt.setString(3, email);
			pstmt.setDate(4, Date.valueOf(naissance));
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return id;
		}catch (SQLException e){
			throw new DaoException();
		}
	}
	
	public long delete(Client client) throws DaoException {
		long id = client.getID();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(DELETE_CLIENT_QUERY);
			pstmt.setLong(1, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			return id;
		}catch (SQLException e){
			throw new DaoException();
		}
	}

	public Client findById(long id) throws DaoException {
		String nom, prenom, email;
		LocalDate naissance;
		try (
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENT_QUERY);)
		{
			pstmt.setInt(1, (int) id);
			ResultSet rset = pstmt.executeQuery();

			if(rset.next()) {
				nom = rset.getString(1);
				prenom = rset.getString(2);
				email = rset.getString(3);
				naissance = rset.getDate(4).toLocalDate();
				rset.close();
				return new Client(nom, prenom, email, naissance);
			}
		}catch(SQLException e){
			throw new DaoException();
		}
		return null;
	}

	public List<Client> findAll() throws DaoException {
		ArrayList<Client> list = new ArrayList<>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_CLIENTS_QUERY);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next()){
				list.add(new Client(rset.getInt(1), rset.getString(2), rset.getString(3), rset.getString(4), rset.getDate(5).toLocalDate()));
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
