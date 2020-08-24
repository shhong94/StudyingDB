<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<% 
	MusicDAO dao = new MusicDAO();
	ArrayList<MovieVO> list = dao.movieAllData();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	for(MovieVO vo : list){
		vo.getTitle();	
	}
%>

</body>
</html>