package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import commons.Constants;
import models.Contact;

public class ContactRepositorySQLite implements ContactRepository {


	@Override
	public void insert(Contact contact) {
		Connection con;
		PreparedStatement prepStmt;
		String sql = "INSERT INTO "
				+ "Contatos (first_name, last_name, email, phone) "
				+ "VALUES (?, ?, ?, ?)";
		try {
			con = DriverManager.getConnection(Constants.URL);
			prepStmt = con.prepareStatement(sql);
			prepStmt.setString(1, contact.getFirstName());
			prepStmt.setString(2, contact.getLastName());
			prepStmt.setString(3, contact.getEmail());
			prepStmt.setString(4, contact.getPhone());
			prepStmt.executeUpdate();
			prepStmt.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void remove(int id) {
		Connection con;
		PreparedStatement prepStmt;
		try {
			con = DriverManager.getConnection(Constants.URL);
			prepStmt = con.prepareStatement("DELETE FROM Contatos WHERE id=?");
			prepStmt.setInt(1, id);
			prepStmt.executeUpdate();
			prepStmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Contact[] findBy(String fieldName, String value) {
		Connection con;
		PreparedStatement stmt;
		String sql = "SELECT * FROM Contatos WHERE ";
		if(fieldName.equals("*")) {
			sql += "first_name like ? OR last_name like ? OR email like ? OR phone like ?";
		} else  {
			sql +=fieldName+" like ?";
		}
		
		try {

			con = DriverManager.getConnection(Constants.URL);
			stmt = con.prepareStatement(sql);
			
			if(!fieldName.equals("*")) {
				stmt.setString(1, "%"+value+"%");
			}else {
				for (int i = 1; i <= 4; i++) {
					stmt.setString(i, "%"+value+"%");
				}
			}
			
			ResultSet rs = stmt.executeQuery();
			int count = 0;
			while (rs.next()) {
				count++;
			}

			Contact[] contacts = new Contact[count];
			rs = stmt.executeQuery(); // rs.beforeFirst();
			int index = 0;
			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");

				Contact c = new Contact(id, fname, lname, phone, email);
				contacts[index] = c;
				index++;
			}
			rs.close();
			stmt.close();
			con.close();

			return contacts;
			
		} catch (Exception e) {
			e.printStackTrace();

			return new Contact[0];
		}
	}

	@Override
	public Contact[] findAll() {
		Connection con;
		Statement stmt;
		String sql = "SELECT id, first_name, last_name, email, phone FROM Contatos";

		try {
			con = DriverManager.getConnection(Constants.URL);
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				count++;
			}

			Contact[] contacts = new Contact[count];
			rs = stmt.executeQuery(sql); // rs.beforeFirst();
			int index = 0;
			while (rs.next()) {
				int id = rs.getInt("id");
				String fname = rs.getString("first_name");
				String lname = rs.getString("last_name");
				String email = rs.getString("email");
				String phone = rs.getString("phone");

				Contact c = new Contact(id, fname, lname, phone, email);
				contacts[index] = c;
				index++;
			}
			rs.close();
			stmt.close();
			con.close();

			return contacts;

		} catch (SQLException e) {
			e.printStackTrace();

			return new Contact[0];
		}
	}

	@Override
	public void update(Contact contact) {
		Connection con;
		PreparedStatement prepStmt;
		String sql = "UPDATE Contatos SET first_name=?, last_name=?, email=?, phone=? "
				+ "WHERE id=?";
		try {
			con = DriverManager.getConnection(Constants.URL);
			prepStmt = con.prepareStatement(sql);
			prepStmt.setString(1, contact.getFirstName());
			prepStmt.setString(2, contact.getLastName());
			prepStmt.setString(3, contact.getEmail());
			prepStmt.setString(4, contact.getPhone());
			prepStmt.setInt(5, contact.getId());
			prepStmt.executeUpdate();
			prepStmt.close();
			con.close();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}           

	}

}
