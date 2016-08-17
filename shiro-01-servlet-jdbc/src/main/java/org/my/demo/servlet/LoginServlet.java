package org.my.demo.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.my.demo.constant.ContentConstant;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("login doget");
		req.getRequestDispatcher("login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("login dopost");
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String flag = req.getParameter("flag");
		ContentConstant.dbFlag = flag;
		Subject subject = SecurityUtils.getSubject();
//		UsernamePasswordToken token = new UsernamePasswordToken(userName, CryptographyUtil.md5(password, "java1234"));
		UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
		try {
			if (subject.isRemembered()) {
				System.out.println("---isRememberMe---");
			} else {
				token.setRememberMe(true);
			}
			subject.login(token);
			Session session = subject.getSession();
			System.out.println("sessionId:" + session.getId());
			System.out.println("sessionHost:" + session.getHost());
			System.out.println("sessionTimeout:" + session.getTimeout());
			session.setAttribute("info", "session");
			resp.sendRedirect("success.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("errorInfo", "xxxxxx");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
		}
	}

}
