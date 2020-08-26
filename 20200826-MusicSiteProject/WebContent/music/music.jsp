<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*, com.sist.manager.*"%>
    
<%
	MusicDAO dao = new MusicDAO();
	String mode = request.getParameter("mode");
	String genre = dao.musicGetGenre(Integer.parseInt(mode));
	String strPage = request.getParameter("page");
	if(strPage == null){
		strPage = "1";
	}
	int currpage = Integer.parseInt(strPage);
	ArrayList<MusicVO> list = dao.musicAllData(Integer.parseInt(mode), currpage);
	
	

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>장르별 음악(<%=genre %>)</h1>
		<table class="table table-hover" width=1000>
			<tr class="danger">
				<th class="text-center">순위</th>
				<th class="text-center"></th>
				<th class="text-center">곡명</th>
				<th class="text-center">가수명</th>
				<th class="text-center">앨범</th>
			</tr>
			<%
				for(MusicVO vo : list){
			%>
					<tr>
						<td class="text-center"><%=vo.getRank()+((currpage*30)-30) %></td>
						<td class="text-center"><img src="<%=vo.getPoster()%>" width=35 height=35 class="img-circle"></td>
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
						<li><a href="music_main.jsp?mode=<%=mode %>&page=<%=i%>"><%=i %></a></li>		<!-- 파라미터로 mode(장르), page를 보내야 함 / 네비게이션 바가 있는 music_main으로 보내야 함 -->
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