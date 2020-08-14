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
		
	// BoardList로부터 no 받기
		String no = request.getParameter("no");
		BoardDAO dao = new BoardDAO();
		BoardVO vo = dao.boardDetail(Integer.parseInt(no));
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");	// 부트스트랩 링크
		out.println("<style type=text/css>");
		out.println(".row {margin:0px auto; width:600px}");
		out.println("h2 {text-align:center;}");
		out.println("</style>");
		out.println("</head>");
		
		
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h2>내용 보기</h2>");
		out.println("<div class=row>");
		
	// 번호 작성일 내용......
		out.println("<table class=\"table\">");//============================================================================================================================ ●
		
		
		out.println("<tr>");
		out.println("<td class=\"success text-center\" width=25%>번호</td>");		// 번호
		out.println("<td width=25% class=text-center>"+vo.getNo()+"</td>");
		out.println("<td class=\"success text-center\" width=25%>작성일</td>");	// 작성일
		out.println("<td width=25% class=text-center>"+vo.getRegdate().toString()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td class=\"success text-center\" width=25%>이름</td>");		// 이름
		out.println("<td width=25% class=text-center>"+vo.getName()+"</td>");
		out.println("<td class=\"success text-center\" width=25%>조회수</td>");	// 조회수
		out.println("<td width=25% class=text-center>"+vo.getHit()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td class=\"success text-center\" width=25%>제목</td>");		// 제목
		out.println("<td colspan=3>"+vo.getSubject()+"</td>");							// 3칸 합치기
		out.println("</tr>");
		
		out.println("<tr>");													// 내용
		out.println("<td colspan=4 height=400 valign=top>"+vo.getContent()+"</td>");	// 4칸 합치기 , valign=top : 위에서부터 출력
		out.println("</tr>");
			
		out.println("<tr>");													// 내용 하단 버튼들
		out.println("<td colspan=4 class=text-right>");	
		out.println("<a href=# class=\"btn btn-sm btn-success\">수정</a>");
		out.println("<a href=# class=\"btn btn-sm btn-warning\">삭제</a>");
		out.println("<a href=BoardList class=\"btn btn-sm btn-danger\">목록</a>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");//============================================================================================================================================ ●
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
