package org.my.demo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class DemoOracle04 {

	private static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DBURL = "jdbc:oracle:thin:@192.168.0.109:1521:orcl";
	private static final String DBUSER = "scott";
	private static final String DBPASS = "tiger";

	public static void main(String[] args) throws Exception {
		test2();
	}

	public static void test1() throws Exception {
		Class.forName(DBDRIVER);

		Connection conn = null;
		CallableStatement cs = null;
		conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);

		cs = conn.prepareCall("{call prol4(?,?)}");
		cs.setInt(1, 7900);
		cs.setDouble(2, 33.2f);

		cs.execute();
		// conn.commit();
		cs.close();
		conn.close();
	}

	public static void test2() throws Exception {
		Class.forName(DBDRIVER);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		String sql = "select fun1(7788) from dual";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		rsmd = rs.getMetaData();

		while (rs.next()) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				sb.append(rs.getString(i + 1) + ",");
			}
			System.out.println(sb.toString());
		}
		rs.close();
		pstmt.close();
		conn.close();
	}

}
