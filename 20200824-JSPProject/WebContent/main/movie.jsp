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
<style type="text/css">
	
</style>
</head>
<body>
	<center>
		<h1>영화</h1>
			<%	for(MovieVO vo : list)
			{
			%>
			
				<table width=200 height=400 class=t>
					<tr width=100% height=70%>
						<td>
							<img src=<%=vo.getPoster() %> width=100% height=100%>
						</td>
					</tr>
					<tr width=100% height=15%>
						<td><%=vo.getTitle() %></td>
					</tr>
					<tr width=100% height=15%>
						<td><%=vo.getRegdate() %></td>
					</tr>
				</table>
			<%
			}
			%>
				
	</center>
</body>
</html>