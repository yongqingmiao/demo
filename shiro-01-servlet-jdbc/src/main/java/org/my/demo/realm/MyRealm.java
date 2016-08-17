package org.my.demo.realm;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.my.demo.dao.UserDao;
import org.my.demo.entity.User;
import org.my.demo.util.DbUtil;

public class MyRealm extends AuthorizingRealm {

	private UserDao userDao = new UserDao();
	private DbUtil dbUtil = new DbUtil();

	
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			authorizationInfo.setRoles(userDao.getRoles(con, userName));
			authorizationInfo.setStringPermissions(userDao.getPermissions(con, userName));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		Connection con = null;
		try {
			con = dbUtil.getCon();
			User user = userDao.getByUserName(con, userName);
			if (user != null) {
				AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xx");
				Map<String,Object> dataMap = new HashMap<String,Object>();
				dataMap.put("id", user.getId());
				SecurityUtils.getSubject().getSession().setAttribute("dataMap", dataMap);
				return authcInfo;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
