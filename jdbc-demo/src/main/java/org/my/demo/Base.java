package org.my.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Base {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		template(); 
	}

	static void template() throws Exception {
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			conn = JdbcUtils.getConnection();
			// conn = JdbcUtilsSing.getInstance().getConnection(); 
			st = conn.createStatement();

			rs = st.executeQuery("select * from user");

			while (rs.next()) {
				System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t" + rs.getObject(4));
			}
		} finally {
			JdbcUtils.free(rs, st, conn);
		}

	}

	static void test() throws SQLException, ClassNotFoundException {
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		System.setProperty("jdbc.drivers", "com.mysql.jdbc.Driver");
		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://localhost:3306/jdbc";
		String user = "root";
		String password = "";
		Connection conn = DriverManager.getConnection(url, user, password);

		Statement st = conn.createStatement();

		ResultSet rs = st.executeQuery("select * from user");

		while (rs.next()) {
			System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t" + rs.getObject(4));
		}
		
		rs.close();
		st.close();
		conn.close();
	}
}
