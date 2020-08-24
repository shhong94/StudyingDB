<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>

<% BoardDAO dao = new BoardDAO();
	ArrayList<BoardVO> list = dao.boardAllData();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style type="text/css">

</style>

<title>Insert title here</title>
</head>
<body>
	<center>
		<div class="container">
			<h1>게시판</h1>
			<table border=1 width=1000 class="table">
				<tr height=40 style="background-color: #F5937E">
					<th width=5%>번호</th>
					<th width=15%>이름</th>
					<th width=50%>제목</th>
					<th width=20%>게시일</th>
					<th width=10%>조회수</th>
				</tr>

				<%
					for(BoardVO vo : list){
				%>
						<tr>
							<td style="text-align:center"><%=vo.getNo() %></td>
							<td><%=vo.getName() %></td>
							<td><%=vo.getSubject() %></td>
							<td style="text-align:center"><%=vo.getRegdate() %></td>
							<td style="text-align:center"><%=vo.getHit() %></td>
						</tr>
				<%
					}
				%>
				
			
			</table>
		</div>
	</center>

</body>
</html>