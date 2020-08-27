<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<h1>post로 데이터 전송</h1>
		<form method=post action="output2.jsp">
		<table border=1 width=350>
			<tr>
				<td width=30% align=right>이름</td>
				<td width=70% align=left>
					<input type=text name=name size=15>
				</td>
			</tr>
			<tr>
				<td width=30% align=right>성별</td>
				<td width=70% align=left>
					<input type=radio value="남자" name=sex checked>남자
					<input type=radio value="여자" name=sex>여자
				</td>
			</tr>
			<tr>
				<td width=30% align=right>지역</td>
				<td width=70% align=left>
					<select name=loc>
						<option>서울</option>			<!-- value가 있으면 seoul이 넘어가고, 없으면 옵션태그 사이의 '서울'이 넘어감 -->
						<option>부산</option>
						<option>인천</option>
						<option>경기</option>
						<option>강원</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width=30% align=right>취미</td>
				<td width=70% align=left>
					<input type=checkbox value=등산 name=hobby>등산
					<input type=checkbox value=게임 name=hobby>게임
					<input type=checkbox value=독서 name=hobby>독서
					<input type=checkbox value=영화 name=hobby>영화
					<input type=checkbox value=여행 name=hobby>여행
				</td>
			</tr>
			<tr>
				<td width=30% align=right>소개</td>
				<td width=70% align=left>
					<textarea name=content cols=30 rows=7></textarea>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=center>
					<input type=submit value=전송>
				</td>
			</tr>
		</table>
		</form>
	</center>
</body>
</html>