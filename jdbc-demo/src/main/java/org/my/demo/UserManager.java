package org.my.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserManager {

	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/myjdbc";
	private static final String DBUSER = "root";
	private static final String DBPASS = "root";

	public void addUser() {
		Connection conn = null;
		try {
			Class.forName(DBDRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(conn);
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		UserManager um = new UserManager();
		um.addUser();
	}
}
