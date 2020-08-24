<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    errorPage="error.jsp"
    %>

<!DOCTYPE html>
<%
out.println("<html>");
out.println("<head>");
out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
out.println("<style type=text/css>");
out.println(".row{");
out.println("margin: 0px auto;");
out.println("width:300px;");
out.println("}");
out.println("</style>");
out.println("</head>");



out.println("<body>");
out.println("<div class=container>");
out.println("<center>");
out.println("<h1>로그인</h1>");
out.println("<div class=row>");

out.println("<form method=post action=Login>");											// Login으로 post 형식으로 전송
out.println("<table class=table>");
out.println("<tr>");
out.println("<td width=20% class=text-right>ID</td>");									// 아이디
out.println("<td width=\"75%\">");
out.println("<input type=text name=id size=15 class=input-sm>");
out.println("</td>");
out.println("</tr>");

out.println("<tr>");
out.println("<td width=20% class=text-right>Password</td>");							// 비번
out.println("<td width=\"75%\">");
out.println("<input type=password name=pwd size=15 class=input-sm>");
out.println("</td>");
out.println("</tr>");

out.println("<tr>");
out.println("<td colspan=2 class=text-center>");
out.println("<input type=submit value=로그인 class=\"btn btn-sm btn-success\">");			// 버튼
out.println("<input type=button value=취소 class=\"btn btn-sm btn-danger\">");
out.println("</td>");
out.println("</tr>");

out.println("</table>");
out.println("</form>");


out.println("</div>");
out.println("</center>");
out.println("</div>");
out.println("</body>");
out.println("</html>");

%>