package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.BoardDAO;
import com.sist.dao.BoardVO;

import java.util.*;


// �۾��� INSERT �ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ�

@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;


	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");	// ��Ʈ��Ʈ�� ��ũ
		out.println("<style type=text/css>");
		out.println(".row {margin:0px auto; width:500px}");
		out.println("h2 {text-align:center;}");
		out.println("</style>");
		out.println("</head>");
		
		
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h2>�۾���</h2>");
		out.println("<div class=row>");
		
	// �̸� ���� ���� ��й�ȣ �۾���/��� ��ư
		out.println("<form method=post action=BoardInsert>");		// BoardInsert�� post ���·� ������
		out.println("<table class=\"table table-hover\">");//============================================================================================================ ��
		out.println("<tr>");
		out.println("<td width=15% class=text-right>�̸�</td>");		// �̸� �Է�â
		out.println("<td width=85%>");
		out.println("<input type=text size=15 class=input-sm name=name>");		// ���� ������ ���� name ����
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>����</td>");		// ���� �Է�â
		out.println("<td width=85%>");
		out.println("<input type=text size=45 class=input-sm name=subject>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>����</td>");		// ���� �Է�â
		out.println("<td width=85%>");
		out.println("<textarea cols=50 rows=10 name=content></textarea>");		// cols : ���� 	rows : ����
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>��й�ȣ</td>");	// ��й�ȣ �Է�â
		out.println("<td width=85%>");
		out.println("<input type=password size=10 class=input-sm name=pwd>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit class=\"btn btn-sm btn-danger\" value=�۾���>");
		out.println("<input type=button class=\"btn btn-sm btn-info\" value=��� onclick=\"javascript:history.back()\">");		// ��ҹ�ư : ���� �������� ���ư���
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");//======================================================================================================================================= ��
		out.println("</form>");
		
		
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}



	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	// �ѱ۷� ���ڵ��ϱ�
		try {
			request.setCharacterEncoding("EUC-KR");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	// ���۵� ���� �ޱ�
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
	// DAO�� �����ϰ� ����Ŭ�� INSERT
		BoardDAO dao = new BoardDAO();
		dao.boardInsert(vo);
		
	// INSERT �Ϸ� �� �Խù� ������� �̵�
		response.sendRedirect("BoardList");
	}

}
