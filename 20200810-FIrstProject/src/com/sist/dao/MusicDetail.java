package com.sist.dao;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MusicDetail")	// <=========================================================================================== a�±��� �������� �̸� (���ϸ�, Ŭ�������� �ƴ�!!!!)
public class MusicDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		
		// �� �ޱ�
		String mno = request.getParameter("mno");	// ���󿡼��� �Ķ���ʹ� �׻� String �������� �޾ƿ�
		MusicDAO dao = new MusicDAO();
		MusicVO vo = dao.musicDetailData(Integer.parseInt(mno));
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>&lt;"+vo.getTitle()+"&gt; �󼼺���</h>");				// lt, gt : <, >
		
	// ��Ʃ�� ����
		out.println("<table width=700>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<iframe src=http://youtube.com/embed/"+vo.getKey()+" width=700 height=400></iframe>");			// iframe : ������ �ܾ�� ������ ����	video : pc�� ����� ���� ����
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
	// ��Ʃ�� ���� �� ������
		out.println("<table width=700>");
		out.println("<tr>");
	// ������
		out.println("<td width=45% rowspan=3>");						// rowspan : ������ ��ġ�� (�̹��� ������ ���� �����)
		out.println("<img src="+vo.getPoster()+" width=100%>");			
		out.println("</td>");
	// ����																// �����Ϳ� ������ ���� tr���� ����. �׷��� �����Ϳ��� rowspan �̿� ����
		out.println("<td>");
		out.print(vo.getTitle());
		out.println("</td>");
		out.println("</tr>");
	// ����
		out.println("<tr>");
		out.println("<td>");
		out.println(vo.getSinger());
		out.println("</td>");
		out.println("</tr");
	// �ٹ�
		out.println("<tr>");
		out.println("<td>");
		out.println(vo.getAlbum());
		out.println("</td>");
		out.println("</tr");
	// ������� ���ƿ���
		out.println("<tr>");
		out.println("<td>");
		out.println("<a href=MusicServlet>���</a>");
		out.println("</td>");
		out.println("</tr");	
		
		
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		
		
	}

}
