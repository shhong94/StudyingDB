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
		out.println("<h1 class=text-center>영화목록</h1>");
		out.println("<div class=row>");
		out.println("<a href=MovieMain?no=1 class=\"btn btn-sm btn-primary\">현재상영영화</a>");
		out.println("<a href=MovieMain?no=2 class=\"btn btn-sm btn-danger\">개봉예정영화</a>");
		out.println("<a href=MovieMain?no=3 class=\"btn btn-sm btn-info\">주간박스오피스</a>");
		out.println("<a href=MovieMain?no=4 class=\"btn btn-sm btn-warning\">월간박스오피스</a>");
		out.println("<a href=MovieMain?no=5 class=\"btn btn-sm btn-success\">연간박스오피스</a>");
		out.println("<a href=NewsMain class=\"btn btn-sm btn-primary\">뉴스</a>");
		out.println("</div>");
		out.println("<div class=row>");
		
	// 전송받은 값 받기
		String no = request.getParameter("no");
		if(no == null) {
			no = "1";
		}
		
	// 반복문으로 영화목록 출력	
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
