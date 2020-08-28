<%@ page info="상세보기(2020.08.28)" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>

<%
	String no = request.getParameter("no");
	String strPage = request.getParameter("page");
	
	
	ReplyBoardDAO dao = new ReplyBoardDAO();
	ReplyBoardVO vo = dao.boardDetail(Integer.parseInt(no), 1);
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
		<h1>내용보기</h1>
		<table class="table_content" width=700>
			<tr>
				<th width=20%>번호</th>
				<td width=20% align="center"><%=vo.getNo()%></td>
				<th width=20%>작성일</th>
				<td width=20% align="center"><!-- <%=vo.getRegdate()%>--></td>
			</tr>
			<tr>
				<th width=20%>이름</th>
				<td width=20% align="center"><%=vo.getName()%></td>
				<th width=20%>조회수</th>
				<td width=20% align="center"><%=vo.getHit()%></td>
			</tr>
			<tr>
				<th width=20%>제목</th>
				<td colspan=3 align="left"><%=vo.getSubject()%></td>
			</tr>
			<tr>
				<td colspan=4 height=200 valign="top"><pre><%=vo.getContent()%></pre></td>		<!--  td는 무조건 한줄로 출력되므로 pre태그를 붙이기 -->
			</tr>
			<tr>
				<td colspan=4 align="right">
					<a href="reply.jsp?no=<%=vo.getNo()%>&page=<%=strPage%>">답변</a>			<!-- 답변등록 후 원래 페이지로 돌아가기 위해 page 파라미터도 넘겨줌 -->
					<a href="update.jsp?no=<%=vo.getNo()%>&page=<%=strPage%>">수정</a>&nbsp;
					<a href="#">삭제</a>&nbsp;
					<a href="list.jsp?page=<%=strPage%>">목록</a>								<!-- 목록버튼 누르면 strPage에 해당되는 페이지로 돌아가기 -->
				</td>
			</tr>
		</table>
	</center>
</body>
</html>