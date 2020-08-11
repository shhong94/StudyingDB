package com.sist.dao;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

// 화면 출력
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		
// out.println("<>");
		
		out.println("<h1>Login</h1>");
		out.println("<form method=post action=LoginServlet>");		// 메소드를 post로 함으로써 doPost 메소드 호출
		out.println("<table width=250>");
		
		out.println("<tr>");
		out.println("<td width=15% align=right>이름</td>");
		out.println("<td width=85%>");						// 사원이름 입력창
		out.println("<input type=text name=ename size=17>");	
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% align=right>사번</td>");
		out.println("<td width=85%>");						// 사번 입력창
		out.println("<input type=password name=empno size=17>");	
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");								// 로그인 버튼
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=로그인>");
		out.println("</td>");
		out.println("</tr>");
		
		
		out.println("</table>");		
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
	}

// 요청(로그인) 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		String ename = request.getParameter("ename");		// 위의 사원이름 입력창의 name
		String empno = request.getParameter("empno");		// 위의 사번 입력창의 name
		
		MyDAO dao = new MyDAO();
		String result = dao.isLogin(ename.toUpperCase(), Integer.parseInt(empno));
		
		if (result.equals("No Name")) {		// 이름 틀림
			out.println("<script>");
			out.println("alert(\"이름이 존재하지 않습니다\")");
			out.println("history.back()");
			out.println("</script>");
		}
		else if(result.equals("No Empno")) {	// 사번 틀림
			out.println("<script>");
			out.println("alert(\"사번이 틀립니다\")");
			out.println("history.back()");
			out.println("</script>");
		}
		else {									// 로그인 성공
			response.sendRedirect("MusicServlet");
			
		}
	}
	

}
