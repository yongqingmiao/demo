package org.my.demo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class DBUtil {

	private final static String NAME = "com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/myjdbc";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";

	static {
		try {
			Class.forName(NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return conn;
	}

	public static boolean handle(String sql, Object... args) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0;
		boolean flag = false;

		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (args.length != 0) {
				int i = 1;
				for (Object o : args) {
					pst.setObject(i++, o);
				}
			}
			count = pst.executeUpdate();
			if (count != 0)
				flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeAll(rs, pst, conn);
		}
		return flag;
	}

	public static void closeConn(Connection conn) {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void closePreparedStatement(PreparedStatement pst) {
		if (pst != null)
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public static void closeAll(ResultSet rs, PreparedStatement pst, Connection conn) {
		closeConn(conn);
		closePreparedStatement(pst);
		closeResultSet(rs);
	}
	
	public static List<Map<String, Object>> read(String sql) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rmd = null;

		int count = 1;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			rmd = pst.getMetaData();
			count = rmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					map.put(rmd.getColumnLabel(i), rs.getObject(rmd.getColumnLabel(i)));
				}
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pst, conn);
		}
		return list;
	}

	public static List<Map<String, Object>> readArgs(String sql, Object... args) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rmd = null;
		int count = 1;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (args.length != 0) {
				int i = 1;
				for (Object o : args) {
					pst.setObject(i++, o);
				}
			}

			rs = pst.executeQuery();
			rmd = pst.getMetaData();
			count = rmd.getColumnCount();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= count; i++) {
					map.put(rmd.getColumnLabel(i), rs.getObject(rmd.getColumnLabel(i)));
				}
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pst, conn);
		}
		return list;

	}
	
	public static List<Map<Integer, Object>> readArgs2(String sql, Object... args) {

		List<Map<Integer, Object>> list = new ArrayList<Map<Integer, Object>>();
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rmd = null;
		int count = 1;
		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (args.length != 0) {
				int i = 1;
				for (Object o : args) {
					pst.setObject(i++, o);
				}
			}

			rs = pst.executeQuery();
			rmd = pst.getMetaData();
			count = rmd.getColumnCount();
			while (rs.next()) {
				Map<Integer, Object> map = new HashMap<Integer, Object>();
				for (int i = 1; i <= count; i++) {
					map.put(i, rs.getObject(rmd.getColumnLabel(i)));
				}
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pst, conn);
		}
		return list;

	}	

	public static <T> List<T> ormRead(String sql, Class<T> c, Object... args) {

		List<T> list = new ArrayList<T>();

		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rsd = null;

		int count = 0;
		Method[] method = c.getMethods();

		try {
			conn = getConnection();
			pst = conn.prepareStatement(sql);
			if (args.length != 0) {
				int i = 1;
				for (Object o : args) {
					pst.setObject(i++, o);
				}
			}

			rsd = pst.getMetaData();
			rs = pst.executeQuery();
			count = rsd.getColumnCount();
			while (rs.next()) {
				T t = c.newInstance();
				for (int i = 1; i <= count; i++) {
					String rowName = rsd.getColumnLabel(i);
					String mothodName = "set" + rowName.substring(0, 1).toUpperCase() + rowName.substring(1);
					for (Method m : method)
						if (mothodName.equals(m.getName())) {
							m.invoke(t, rs.getObject(rowName));
							break;
						}
				}
				list.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			closeAll(rs, pst, conn);
		}
		return list;
	}

	
	
	
	public static void main(String[] args) {

//		boolean boo = handle("insert into users(username,password) values(?,?)", "b","b");
//		System.out.println(boo);
//		boolean boo = handle("insert into users(username,password) values(?,?)", "b","b");
//		boolean boo = handle("update users set username=?,password=? where id = ?", "o","o",1);
//		System.out.println(boo);
		
		
		List<User> list0 = ormRead("select * from t_user", User.class);
		List<User> list1 = ormRead("select * from t_user where id = ?", User.class,1);
		List<Map<String, Object>> list2 = readArgs("select * from t_user where id = ?","2");
		List<Map<String, Object>> list3 = readArgs("select id,username from t_user");
		List<Map<String, Object>> list4 = readArgs("select * from t_user");
		
		List<Map<Integer, Object>> list5 = readArgs2("select * from t_user");
		
		//for (Map<String,Object> u : list4)
		//	System.out.println(u+"==\t"+u.get("id")+"---"+u.get("username")+"---"+u.get("password"));
				
				
	    for(int i=0;i<list5.size();i++){
	    	Map<Integer,Object> s = (Map)list5.get(i);
	    	System.out.println(s.get(1)+"---"+s.get(2)+"---"+s.get(3));
	    }
	}

}