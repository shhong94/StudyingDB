<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
<%
	String strPage = request.getParameter("page");		// 사용자가 페이지 변경할 때마다 파라미터 받아옴
	if(strPage == null){
		strPage = "1";
	}
	int currpage = Integer.parseInt(strPage);			// 현재페이지
	
	ReplyBoardDAO dao = new ReplyBoardDAO();
	ArrayList<ReplyBoardVO> list = dao.boardListData(currpage);
	
	int count = dao.boardRowCount();					// 게시글 개수 출력
	count = count-((currpage*10)-10);					// 다음 페이지로 넘어갈 때마다 ((currpage*10)-10)만큼  빼줘야 함 ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆ㅍ
	
	int totalpage = (int)(Math.ceil(count/10.0));		// 전체 개수 count를 10.0으로 나누고 올림하면 됨 (count를 이미 구했으므로 DAO에서 처리하지 않아도 됨) ★☆★☆★☆★☆★☆★☆★☆★☆★☆
	
	
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
		<h1>묻고답하기</h1>
		<table class=table_content width=700>
			<tr>
				<td>
					<a href="insert.jsp">등록</a>
				</td>
			</tr>
		</table>
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
						<td width=10% align=center><%=count-- %></td>				<!-- 게시물 출력시마다 게시물번호도 함께 줄어듬 ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆-->
						<td width=45%>
						<%
							if(vo.getGroup_tab() > 0){								// 제목 앞에 공백 주기 ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆
								for(int i = 0; i < vo.getGroup_tab(); i++){
									out.println("&nbsp;&nbsp;");
								}
						%>
								<img src="image/icon_reply.gif" style="border:none">
						<%
							}
						%>
						<%=vo.getSubject() %>
						&nbsp;
						<%
							Date date = new Date();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
							String today = sdf.format(date);
							String dbday = vo.getRegdate().toString();
							
							if(today.equals(dbday)){
						%>
								<sup><img src="image/new.gif" style="border:none"></sup>
						<%		
							}
						%>
						</td>
						<td width=15% align=center><%=vo.getName() %></td>
						<td width=20% align=center><%=vo.getRegdate() %></td>
						<td width=10% align=center><%=vo.getHit() %></td>
					</tr>
			<%
				}
			%>
		</table>
		<table class=table_content width=700>
			<tr>
				<td align=left>
				Search: 
				<select name=fd>								<!-- WHERE fd LIKE '%홍%' -->
					<option value=name>이름</option>				
					<option value=subject>제목</option>			
					<option value=content>내용</option>
					
				</select>
				<input type=text name=ss size=10>
				<input type=submit value=찾기>
				</td>
				<td align=right>
				<a href="list.jsp?page=<%=currpage>1 ? currpage-1 : currpage%>">이전</a>
				<%=currpage %> page / <%=totalpage %> pages
				<a href="list.jsp?page=<%=currpage<totalpage ? currpage+1 : currpage%>">다음</a>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>