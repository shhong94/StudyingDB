<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, dao.*"%>
    
<% 

	MelonDAO dao = new MelonDAO();

	String strPage = request.getParameter("page");
	if(strPage == null){
		strPage = "1";
	}
	int currpage = Integer.parseInt(strPage);
	
	String mode = request.getParameter("mode");
	

	ArrayList<MelonVO> list = dao.melonAllData(currpage, Integer.parseInt(mode));


/*
	MelonDAO dao = new MelonDAO();
	String mode = request.getParameter("mode");
	String strPage = request.getParameter("page");
	if(strPage == null){
		strPage = "1";
	}
	int currpage = Integer.parseInt(strPage);
	ArrayList<MelonVO> list = dao.melonAllData(Integer.parseInt(mode), currpage);
*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<table class="table table-hover" width=1000>
			<tr>
				<th>순위</th>
				<th></th>
				<th>곡명</th>
				<th>가수</th>
				<th>앨범</th>
			</tr>
			<%
				for(MelonVO vo : list){
			%>
				<tr>
					<td><%=vo.getRank() + ((currpage*10)-10) %></td>
					<td>
					<img src="<%=vo.getPoster() %>">
					</td>
					<td><%=vo.getTitle() %></td>
					<td><%=vo.getSinger() %></td>
					<td><%=vo.getAlbum() %></td>
				</tr>
			<%
				}			
			%>
		</table>
		<table class="table">
			<tr class="text-center">
				<td >
				</td>
					<ul class="pagination">
						<%for(int i = 1; i <= 5; i++){
						%>
						<li><a href="melon_main.jsp?mode=<%=mode %>&page=<%=i%>"><%=i %></a></li>		<!-- 파라미터로 mode(장르), page를 보내야 함 / 네비게이션 바가 있는 music_main으로 보내야 함 -->
						<%																		// music_main에 music이 인크루드 되어있기 때문에 music_main으로 보낸 파라미터를 music도 사용할 수 있음			
						}
						%>
					</ul>
				</td>
			</tr>
		</table>
	</center>
</body>
</html>