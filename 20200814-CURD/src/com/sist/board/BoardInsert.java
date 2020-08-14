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


// 글쓰기 INSERT ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆

@WebServlet("/BoardInsert")
public class BoardInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;


	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");	// 부트스트랩 링크
		out.println("<style type=text/css>");
		out.println(".row {margin:0px auto; width:500px}");
		out.println("h2 {text-align:center;}");
		out.println("</style>");
		out.println("</head>");
		
		
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h2>글쓰기</h2>");
		out.println("<div class=row>");
		
	// 이름 제목 내용 비밀번호 글쓰기/취소 버튼
		out.println("<form method=post action=BoardInsert>");		// BoardInsert로 post 형태로 보내기
		out.println("<table class=\"table table-hover\">");//============================================================================================================ ●
		out.println("<tr>");
		out.println("<td width=15% class=text-right>이름</td>");		// 이름 입력창
		out.println("<td width=85%>");
		out.println("<input type=text size=15 class=input-sm name=name>");		// 값을 보내기 위해 name 설정
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>제목</td>");		// 제목 입력창
		out.println("<td width=85%>");
		out.println("<input type=text size=45 class=input-sm name=subject>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>내용</td>");		// 내용 입력창
		out.println("<td width=85%>");
		out.println("<textarea cols=50 rows=10 name=content></textarea>");		// cols : 가로 	rows : 세로
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=15% class=text-right>비밀번호</td>");	// 비밀번호 입력창
		out.println("<td width=85%>");
		out.println("<input type=password size=10 class=input-sm name=pwd>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td colspan=2 class=text-center>");
		out.println("<input type=submit class=\"btn btn-sm btn-danger\" value=글쓰기>");
		out.println("<input type=button class=\"btn btn-sm btn-info\" value=취소 onclick=\"javascript:history.back()\">");		// 취소버튼 : 이전 페이지로 돌아가기
		out.println("</td>");
		out.println("</tr>");
		
		out.println("</table>");//======================================================================================================================================= ●
		out.println("</form>");
		
		
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}



	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	// 한글로 인코딩하기
		try {
			request.setCharacterEncoding("EUC-KR");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
	// 전송된 값들 받기
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
		BoardVO vo = new BoardVO();
		vo.setName(name);
		vo.setSubject(subject);
		vo.setContent(content);
		vo.setPwd(pwd);
		
	// DAO로 전송하고 오라클로 INSERT
		BoardDAO dao = new BoardDAO();
		dao.boardInsert(vo);
		
	// INSERT 완료 후 게시물 목록으로 이동
		response.sendRedirect("BoardList");
	}

}
