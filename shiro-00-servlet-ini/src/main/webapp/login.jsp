<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	pageContext.setAttribute("basePath", basePath);
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="login" method="post">
	userName:<input type="text" name="userName"/><br/>
	password:<input type="password" name="password"/><br/>
	code:<input type="text" name="code"/><img src="<%=basePath %>authImage"/><br/>
	<input type="submit" value="登录"/>
	<%=request.getAttribute("errorInfo")==null?"":request.getAttribute("errorInfo") %>
</form> 
</body>
</html> 