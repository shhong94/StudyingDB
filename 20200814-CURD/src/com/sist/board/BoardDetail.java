package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.sist.dao.*;


@WebServlet("/BoardDetail")
public class BoardDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
	// BoardList�κ��� no �ޱ�
		String no = request.getParameter("no");
		BoardDAO dao = new BoardDAO();
		BoardVO vo = dao.boardDetail(Integer.parseInt(no));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");	// ��Ʈ��Ʈ�� ��ũ
		out.println("<style type=text/css>");
		out.println(".row {margin:0px auto; width:600px}");
		out.println("h2 {text-align:center;}");
		out.println("</style>");
		out.println("</head>");
		
		
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h2>���� ����</h2>");
		out.println("<div class=row>");
		
	// ��ȣ �ۼ��� ����......
		out.println("<table class=\"table\">");//============================================================================================================================ ��
		
		
		out.println("<tr>");
		out.println("<td class=\"success text-center\" width=25%>��ȣ</td>");		// ��ȣ
		out.println("<td width=25% class=text-center>"+vo.getNo()+"</td>");
		out.println("<td class=\"success text-center\" width=25%>�ۼ���</td>");	// �ۼ���
		out.println("<td width=25% class=text-center>"+vo.getRegdate().toString()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td class=\"success text-center\" width=25%>�̸�</td>");		// �̸�
		out.println("<td width=25% class=text-center>"+vo.getName()+"</td>");
		out.println("<td class=\"success text-center\" width=25%>��ȸ��</td>");	// ��ȸ��
		out.println("<td width=25% class=text-center>"+vo.getHit()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td class=\"success text-center\" width=25%>����</td>");		// ����
		out.println("<td colspan=3>"+vo.getSubject()+"</td>");							// 3ĭ ��ġ��
		out.println("</tr>");
		
		out.println("<tr>");													// ����
		out.println("<td colspan=4 height=400 valign=top>"+vo.getContent()+"</td>");	// 4ĭ ��ġ�� , valign=top : ���������� ���
		out.println("</tr>");
			
		out.println("<tr>");													// ���� �ϴ� ��ư��
		out.println("<td colspan=4 class=text-right>");	
		out.println("<a href=# class=\"btn btn-sm btn-success\">����</a>");
		out.println("<a href=# class=\"btn btn-sm btn-warning\">����</a>");
		out.println("<a href=BoardList class=\"btn btn-sm btn-danger\">���</a>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");//============================================================================================================================================ ��
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
