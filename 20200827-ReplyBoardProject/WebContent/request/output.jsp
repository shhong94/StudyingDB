<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>화면 출력</h1>
		<%
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
		%>
		<p> id : <%=id %></p>
		<p> pwd : <%=pwd %></p>
		<p> name : <%=name %></p>
	</center>
</body>
</html>