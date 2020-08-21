package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.sist.dao.*;
import com.sist.manager.*;


@WebServlet("/NewsMain")
public class NewsMain extends HttpServlet {
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
		out.println("<h1>늬우스</h1>");
		out.println("<div class=row>");
		out.println("<a href=MovieMain?no=1 class=\"btn btn-sm btn-primary\">현재상영영화</a>");
		out.println("<a href=MovieMain?no=2 class=\"btn btn-sm btn-danger\">개봉예정영화</a>");
		out.println("<a href=MovieMain?no=3 class=\"btn btn-sm btn-info\">주간박스오피스</a>");
		out.println("<a href=MovieMain?no=4 class=\"btn btn-sm btn-warning\">월간박스오피스</a>");
		out.println("<a href=MovieMain?no=5 class=\"btn btn-sm btn-success\">연간박스오피스</a>");
		out.println("<a href=NewsMain class=\"btn btn-sm btn-primary\">뉴스</a>");		
		
		
		MovieDAO dao = new MovieDAO();
		ArrayList<NewsVO> list = dao.newsListData();
		
		for (NewsVO vo : list) {
			out.println("<table class=\"table table-hover\" width=800>");
			out.println("<tr>");
			out.println("<td rowspan=3 width=40%>");
			String poster = vo.getPoster();
			poster = poster.substring(0, poster.lastIndexOf(")"));
			out.println("<a href="+vo.getLink()+">");
			out.println("<img src="+poster+" width=100%></a>");
			out.println("</td>");
			out.println("<td width=60% class=text-center><b><font color=orange>"+vo.getTitle()+"</font><b></td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td width=60%>"+vo.getContent()+"</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td width=60% class=text-right>"+vo.getAuthor()+"</td>");
			out.println("</tr>");
			out.println("</table>");
			out.println("<hr>");
		}
		out.println("</div>");
		out.println("</div>");
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
