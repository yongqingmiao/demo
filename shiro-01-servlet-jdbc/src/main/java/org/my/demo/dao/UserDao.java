package org.my.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;

import org.my.demo.entity.User;

public class UserDao {

	public User getByUserName(Connection con, String userName) throws Exception {
		User resultUser = null;
		String sql = "SELECT id,username,password FROM users where username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			resultUser = new User();
			resultUser.setId(rs.getInt("id"));
			resultUser.setUserName(rs.getString("username"));
			resultUser.setPassword(rs.getString("password"));
		}
		return resultUser;
	}

	public Set<String> getRoles(Connection con, String userName) throws Exception {
		Set<String> roles = new HashSet<String>();
		String sql = "SELECT t1.role FROM user_roles t1,users t2 where t1.user_id = t2.id and t2.username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			roles.add(rs.getString("role"));
		}
		return roles;
	}

	public Set<String> getPermissions(Connection con, String userName) throws Exception {
		Set<String> permissions = new HashSet<String>();
		String sql = "SELECT t1.permission FROM db_shiro.user_role_permissions t1,user_roles t2,users t3 where t1.role_id = t2.id and t2.user_id = t3.id and t3.username = ?";
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, userName);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			permissions.add(rs.getString("permission"));
		}
		return permissions;
	}
}
