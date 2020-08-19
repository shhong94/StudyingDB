package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;		// MovieDAO
import com.sist.manager.*;	// MovieVO


@WebServlet("/MovieMain")
public class MovieMain extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<center>");
		out.println("<h1 class=text-center>��ȭ���</h1>");
		out.println("<div class=row>");
		out.println("<a href=MovieMain?no=1 class=\"btn btn-sm btn-primary\">����󿵿�ȭ</a>");
		out.println("<a href=MovieMain?no=2 class=\"btn btn-sm btn-danger\">����������ȭ</a>");
		out.println("<a href=MovieMain?no=3 class=\"btn btn-sm btn-info\">�ְ��ڽ����ǽ�</a>");
		out.println("<a href=MovieMain?no=4 class=\"btn btn-sm btn-warning\">�����ڽ����ǽ�</a>");
		out.println("<a href=MovieMain?no=5 class=\"btn btn-sm btn-success\">�����ڽ����ǽ�</a>");
		out.println("<a href=NewsMain class=\"btn btn-sm btn-primary\">����</a>");
		out.println("</div>");
		out.println("<div class=row>");
		
	// ���۹��� �� �ޱ�
		String no = request.getParameter("no");
		if(no == null) {
			no = "1";
		}
		
	// �ݺ������� ��ȭ��� ���	
		MovieDAO dao = new MovieDAO();
		ArrayList<MovieVO> list = dao.movieListData(Integer.parseInt(no));
		
		for(MovieVO vo : list) {
			
		out.println("<div class=\"col-md-3\">");
		out.println("<div class=\"thumbnail\">");
		out.println("<a href=\"#\">");
		out.println("<img src="+vo.getPoster()+" alt=\"Lights\" style=\"width:100%\">");
		out.println("<div class=\"caption\">");
		String title = vo.getTitle();
		if(title.length() > 18) {
			title = title.substring(0, 18) + "...";
		}
		out.println("<p>"+title+"</p>");
		out.println("</div>");
		out.println("</a>");
		out.println("</div>");
		out.println("</div>");
		
		}
		
		out.println("</div>");
		out.println("</div>");
		
		out.println("</center>");
		out.println("</body>");	
		out.println("</html>");
		
	}

}
