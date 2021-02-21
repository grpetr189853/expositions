package com.grpetr.task.db.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private Connection dbConnection;
	
	public Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/expositions", "root", "18985320061980");
		dbConnection.setAutoCommit(false);
		return dbConnection;
	}
	
	public int executeQuery(String query) throws ClassNotFoundException, SQLException {
		return dbConnection.createStatement().executeUpdate(query);
	}

}
