package org.my.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class Demo01 {
	
	private static final String DBDRIVER = "com.mysql.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/myjdbc";
	private static final String DBUSER = "root";
	private static final String DBPASS = "root";
	
	public static void main(String[] args) throws Exception{
		
		Class.forName(DBDRIVER);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		String sql = "select * from t_user";
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		rsmd = rs.getMetaData();
		
		while(rs.next()){
			StringBuffer sb = new StringBuffer();
			for(int i=0;i<rsmd.getColumnCount();i++){
				sb.append(rs.getString(i+1)+",");
			}
			System.out.println(sb.toString());
			
		}
		
		rs.close();
		pstmt.close();
		conn.close();
		
	}
	
}
