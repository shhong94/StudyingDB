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
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");	// 부트스트랩 링크
		out.println("<style type=text/css>");
		out.println(".row {margin:0px auto; width:700px}");
		out.println("h2 {text-align:center;}");
		out.println("</style>");
		out.println("</head>");
		
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<h2>자유게시판</h2>");
		out.println("<div class=row>");
	// 상단 버튼
		out.println("<table class=\"table table-hover\">");//============================================================================================================ ●
		out.println("<tr>");
		out.println("<td>");
		out.println("<a href=BoardInsert class=\"btn btn-sm btn-success\">새글</a>");		// sm : 스몰 크기    success : 색상
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");//======================================================================================================================================= ●
		
	// 컬럼명
		out.println("<table class=\"table\">");//======================================================================================================= □
		out.println("<tr class=danger>");
		out.println("<th class=text-center width=10%>번호</th>");
		out.println("<th class=text-center width=45%>제목</th>");
		out.println("<th class=text-center width=15%>이름</th>");
		out.println("<th class=text-center width=20%>작성일</th>");
		out.println("<th class=text-center width=10%>조회수</th>");
		out.println("</tr>");
		
	// 목록 출력
		BoardDAO dao = new BoardDAO();
		ArrayList<BoardVO> list = dao.boardListData();
		
		for(BoardVO vo : list) {
			out.println("<tr>");
			out.println("<td class=text-center width=10%>"+vo.getNo()+"</td>");
			out.println("<td class=text-left width=45%>"
			+"<a href=BoardDetail?no="+vo.getNo()+">"		// BoardDetail로 no를 파라미터로 보내기
			+vo.getSubject()+"</a></td>");
			out.println("<td class=text-center width=15%>"+vo.getName()+"</td>");
			out.println("<td class=text-center width=20%>"+vo.getRegdate()+"</td>");
			out.println("<td class=text-center width=10%>"+vo.getHit()+"</td>");
			out.println("</tr>");
		}
		out.println("</table>");//======================================================================================================================= □
		
		out.println("<hr>");
		
	// 하단 버튼들
		out.println("<table class=\"table table-hover\">");//============================================================================================================ ●
		out.println("<tr>");
		out.println("<td class=text-left>");
		out.println("Serch:");
		out.println("<select class=input-sm>");		// select : 콤보박스
		out.println("<option>이름</option>");			// option : 콤보박스 내 옵션들
		out.println("<option>제목</option>");
		out.println("<option>내용</option>");
		out.println("</select>");
		
		out.println("<input type=text size=15 class=input-sm>");						// 입력창
		out.println("<input type=button value=찾기 class=\"btn btn-sm btn-danger\">");	// 찾기 버튼
		out.println("</td>");
		out.println("<td class=text-right>");
		out.println("<a href=BoardInsert class=\"btn btn-sm btn-primary\">이전</a>");		// primary : 색상
		out.println("0 page / 0 pages");
		out.println("<a href=BoardInsert class=\"btn btn-sm btn-primary\">다음</a>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");//======================================================================================================================================== ●
		
		out.println("</div>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
	}

}
