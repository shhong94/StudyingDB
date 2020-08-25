<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*, java.text.*"%>

<% 
	String strPage = request.getParameter("page");								// page 변수를 받아서 저장
	if(strPage == null){
		strPage="1";
	}

	BoardDAO dao = new BoardDAO();

	int curpage = Integer.parseInt(strPage);
	int totalpage = dao.boardTotalPage();

	ArrayList<BoardVO> list = dao.boardAllData(curpage);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="table.css">
<style type="text/css">

</style>

<title>Insert title here</title>
</head>
<body>
	<center>
		<div class="container">
			<h1>게시판</h1>
			<table border=1 width=1000 class="table_content">
				<tr>
					<td>
						<a href="insert.jsp">새글</a>
					</td>
				</tr>
			</table>
			<table border=1 width=1000 class="table_content">
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
						<tr class="dataTr">
							<td style="text-align:center"><%=vo.getNo() %></td>
							<td><%=vo.getName() %></td>
							<td>
							<a href="detail.jsp?no=<%=vo.getNo()%>"><%=vo.getSubject() %>
							</td>
							<td style="text-align:center"><%=vo.getRegdate() %>
							<%
							Date date = new Date();														//  최신 게시물 표시  / 날짜 변환
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 24hh:mm:ss");
							String today = sdf.format(date);
							if(today.equals(vo.getRegdate().toString())){
							%>
									<font color=red><sup>new</sup></font>
							<%
								}
							%>	</td>
							<td style="text-align:center"><%=vo.getHit() %></td>
						</tr>
				<%
					}
				%>
				
			
			</table>
			<table class="table_content" width=1000>
				<tr>
					<td align="left"></td>				
					<td align="right">
						<a href="list.jsp?page=<%=curpage>1?curpage-1:curpage%>">이전</a>		<!-- 현재 페이지가 1보다 크면 현재페이지-1, 아니면 현재페이지 -->
						<%=curpage %> page / <%=totalpage %> pages
						<a href="list.jsp?page=<%=curpage<totalpage?curpage + 1:curpage%>">다음</a>		<!-- 현재 페이지가 총 페이지보다 작으면 현재페이지+1, 그렇지않으면 현재페이지 -->
					</td>
				</tr>
			</table>
		</div>
	</center>

</body>
</html>