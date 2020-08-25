<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*, java.text.*"%>
<% 
	String strPage = request.getParameter("page");
	if(strPage == null){
		strPage = "1";
	}

	MusicDAO dao = new MusicDAO();

	int currpage = Integer.parseInt(strPage);
	int totalpage = dao.boardTotalPage();
	
	ArrayList<MovieVO> list = dao.movieAllData(currpage);
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
		<table width=1400 border=1>
			<tr>
				<th width=25%>제목</th>
				<th width=20%>출연</th>
				<th width=10%>감독</th>
				<th width=10%>장르</th>
				<th width=15%>등급</th>
				<th width=20%>개봉일</th>
			</tr>
			<% for(MovieVO vo : list){
			%>
			<tr>
				<td><%=vo.getTitle() %></td>
				<td><%=vo.getActor() %></td>
				<td><%=vo.getDirector() %></td>
				<td><%=vo.getGenre() %></td>
				<td><%=vo.getGrade() %></td>
				<td><%=vo.getRegdate() %></td>
			</tr>
			<%
			}
			%>
		</table>
		<table>
			<tr>
				<td align="left">
				</td>
				<td align="right">
					<a href="movie.jsp?page=<%=currpage>1?currpage-1:currpage%>">이전</a>
					<%=currpage %> page / <%=totalpage %> pages
					<a href="movie.jsp?page=<%=currpage<totalpage?currpage+1:currpage%>">다음</a>
				</td>
			</tr>
		</table>
				
	</center>
</body>
</html>