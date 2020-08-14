package com.sist.board;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;


@WebServlet("/BoardList")
public class BoardList extends HttpServlet {
	private static final long serialVersionUID = 1L;

// <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
// out.println("");
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");	// ��Ʈ��Ʈ�� ��ũ
		out.println("<style type=text/css>");
		out.println(".row {margin:0px auto; width:700px}");
		out.println("h2 {text-align:center;}");
		out.println("</style>");
		out.println("</head>");
		
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h2>�����Խ���</h2>");
		out.println("<div class=row>");
	// ��� ��ư
		out.println("<table class=\"table table-hover\">");//============================================================================================================ ��
		out.println("<tr>");
		out.println("<td>");
		out.println("<a href=BoardInsert class=\"btn btn-sm btn-success\">����</a>");		// sm : ���� ũ��    success : ����
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");//======================================================================================================================================= ��
		
	// �÷���
		out.println("<table class=\"table\">");//======================================================================================================= ��
		out.println("<tr class=danger>");
		out.println("<th class=text-center width=10%>��ȣ</th>");
		out.println("<th class=text-center width=45%>����</th>");
		out.println("<th class=text-center width=15%>�̸�</th>");
		out.println("<th class=text-center width=20%>�ۼ���</th>");
		out.println("<th class=text-center width=10%>��ȸ��</th>");
		out.println("</tr>");
		
	// ��� ���
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardVO> list = dao.boardListData();
		
		for(BoardVO vo : list) {
			out.println("<tr>");
			out.println("<td class=text-center width=10%>"+vo.getNo()+"</td>");
			out.println("<td class=text-left width=45%>"
			+"<a href=BoardDetail?no="+vo.getNo()+">"		// BoardDetail�� no�� �Ķ���ͷ� ������
			+vo.getSubject()+"</a></td>");
			out.println("<td class=text-center width=15%>"+vo.getName()+"</td>");
			out.println("<td class=text-center width=20%>"+vo.getRegdate()+"</td>");
			out.println("<td class=text-center width=10%>"+vo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");//======================================================================================================================= ��
		
		out.println("<hr>");
		
	// �ϴ� ��ư��
		out.println("<table class=\"table table-hover\">");//============================================================================================================ ��
		out.println("<tr>");
		out.println("<td class=text-left>");
		out.println("Serch:");
		out.println("<select class=input-sm>");		// select : �޺��ڽ�
		out.println("<option>�̸�</option>");			// option : �޺��ڽ� �� �ɼǵ�
		out.println("<option>����</option>");
		out.println("<option>����</option>");
		out.println("</select>");
		
		out.println("<input type=text size=15 class=input-sm>");						// �Է�â
		out.println("<input type=button value=ã�� class=\"btn btn-sm btn-danger\">");	// ã�� ��ư
		out.println("</td>");
		out.println("<td class=text-right>");
		out.println("<a href=BoardInsert class=\"btn btn-sm btn-primary\">����</a>");		// primary : ����
		out.println("0 page / 0 pages");
		out.println("<a href=BoardInsert class=\"btn btn-sm btn-primary\">����</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");//======================================================================================================================================== ��
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
