<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
    
<%
	// 전송받은 게시물 번호 
	String no = request.getParameter("no");
	
	// DAO
	BoardDAO dao = new BoardDAO();
	BoardVO vo = dao.boardUpdateData(Integer.parseInt(no));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="table.css">
</head>
<body>
	<center>
		<h1>수정하기</h1>
		<form method=post action=update_ok.jsp>
			<table class="table_content" width=500>
				<tr>
					<th width=15% align="right">이름</th>
					<td width=85%>
						<input type=text name=name size=15 required value=<%=vo.getName() %>>				<!-- 기존의 데이터를 value로 출력 -->
						<input type=hidden name=no value=<%=no %>>											<!-- 사용자에게 감춘채로 게시물번호(no)를 넘김 -->
					</td>
				</tr>
				<tr>
					<th width=15% align="right">제목</th>
					<td width=85%>
						<input type=text name=subject size=45 required value="<%=vo.getSubject() %>">		<!-- 제목, 내용에는 공백이 있기  때문에 큰따옴표 내에 포함시켜야 함 -->
					</td>
				</tr>
				<tr>
					<th width=15% align="right">내용</th>
					<td width=85%>
						<textarea rows=7 cols=55 name=content required><%=vo.getContent() %></textarea>		<!-- textarea는 value가 없고 태그와 태그 사이에 값을 넣어야 함 -->
					</td>
				</tr>
				<tr>
					<th width=15% align="right">비밀번호</th>													<!-- 비밀번호는 본인 확인용이기 때문에 채워넣지 않음 -->
					<td width=85%>
						<input type=password name=pwd size=10 required>
					</td>
				</tr>
				<tr>
					<td colspan=2 align=center>
						<input type=submit value="수정">
						<input type=submit value="취소" onclick="javascript:history.back()">
					</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>