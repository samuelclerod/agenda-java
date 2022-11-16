package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import commons.Constants;

public class Database {
	
	private static Connection con = null;

	public static boolean createConnection() {
		try {
			if(con == null) {
				con = DriverManager.getConnection(Constants.URL);				
			}	
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static ResultSet runQuery(String query){
		if(createConnection()) {
			try {
				Statement stmt = con.createStatement();
				return stmt.executeQuery(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
}








