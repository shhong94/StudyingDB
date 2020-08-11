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

// ȭ�� ���
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		
// out.println("<>");
		
		out.println("<h1>Login</h1>");
		out.println("<form method=post action=LoginServlet>");		// �޼ҵ带 post�� �����ν� doPost �޼ҵ� ȣ��
		out.println("<table width=250>");
		
		out.println("<tr>");
		out.println("<td width=15% align=right>�̸�</td>");
		out.println("<td width=85%>");						// ����̸� �Է�â
		out.println("<input type=text name=ename size=17>");	
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% align=right>���</td>");
		out.println("<td width=85%>");						// ��� �Է�â
		out.println("<input type=password name=empno size=17>");	
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");								// �α��� ��ư
		out.println("<td colspan=2 align=center>");
		out.println("<input type=submit value=�α���>");
		out.println("</td>");
		out.println("</tr>");
		
		
		out.println("</table>");		
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
		
	}

// ��û(�α���) ó��
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		String ename = request.getParameter("ename");		// ���� ����̸� �Է�â�� name
		String empno = request.getParameter("empno");		// ���� ��� �Է�â�� name
		
		MyDAO dao = new MyDAO();
		String result = dao.isLogin(ename.toUpperCase(), Integer.parseInt(empno));
		
		if (result.equals("No Name")) {		// �̸� Ʋ��
			out.println("<script>");
			out.println("alert(\"�̸��� �������� �ʽ��ϴ�\")");
			out.println("history.back()");
			out.println("</script>");
		}
		else if(result.equals("No Empno")) {	// ��� Ʋ��
			out.println("<script>");
			out.println("alert(\"����� Ʋ���ϴ�\")");
			out.println("history.back()");
			out.println("</script>");
		}
		else {									// �α��� ����
			response.sendRedirect("MusicServlet");
			
		}
	}
	

}
