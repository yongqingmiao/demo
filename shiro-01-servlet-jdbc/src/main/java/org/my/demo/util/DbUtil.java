package org.my.demo.util;

import java.sql.Connection;
import java.sql.DriverManager;

import org.my.demo.constant.ContentConstant;

public class DbUtil {

	public Connection getCon() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = null;
		if("db_shiro".equals(ContentConstant.dbFlag)){
			con = DriverManager.getConnection("jdbc:mysql://10.37.129.5:3306/db_shiro", "dbluffy", "dbluffy");
		}else{
			con = DriverManager.getConnection("jdbc:mysql://10.37.129.5:3306/db_cas", "dbluffy", "dbluffy");
		}
		return con;
	}
	
	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}

	public static void main(String[] args) {
		DbUtil dbUtil = new DbUtil();
		try {
			ContentConstant.dbFlag = "db_shiro";
			Connection conn = dbUtil.getCon();
			System.out.println("数据库连接成功");
			dbUtil.closeCon(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
