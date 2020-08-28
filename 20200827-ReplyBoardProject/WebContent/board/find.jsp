<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%
	try{
		request.setCharacterEncoding("UTF-8");
	}catch(Exception e){
		
	}

	String fs = request.getParameter("fs");
	String ss = request.getParameter("ss");
	
	ReplyBoardDAO dao = new ReplyBoardDAO();
	ArrayList<ReplyBoardVO> list = dao.boardFindData(fs, ss);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/table.css">
</head>
<body>
	<center>
		<h1>검색결과</h1>
		<table class=table_content width=700>
			<tr>
				<th width=10%>번호</th>
				<th width=45%>제목</th>
				<th width=15%>이름</th>
				<th width=20%>작성일</th>
				<th width=10%>조회수</th>
			</tr>
			<%
				for(ReplyBoardVO vo : list){
			%>
					<tr>
						<td width=10%><%=vo.getNo() %></td>
						<td width=45%><%=vo.getSubject() %></td>
						<td width=15%><%=vo.getName() %></td>
						<td width=20%><%=vo.getRegdate() %></td>
						<td width=10%><%=vo.getHit() %></td>
					</tr>
			<%
				}
			%>
		</table>
	</center>
</body>
</html>