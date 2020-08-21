package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sist.dao.MovieDAO;
import com.sist.dao.ReplyVO;
import com.sist.manager.MovieVO;

import java.util.*;



@WebServlet("/MovieDetail")
public class MovieDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		String no = request.getParameter("no");							// MovieMain에서 받은 no(영화번호)로 영화 상세정보 출력 준비 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		
		MovieDAO dao = new MovieDAO();
		MovieVO vo = dao.movieDetailData(Integer.parseInt(no));			// no(영화번호)에 해당하는 상세정보 출력 메소드 실행하여 MovieVO vo 객체에 담기 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<center>");
		out.println("<h1>영화 상세정보</h1>");
		out.println("<div class=row>");
		out.println("<div class=col-sm-8>");			// col-sm-8		12개의 세로 화면영역 중에서 영화상세보기가 8개를 가져감
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<iframe src=http://youtube.com/embed/"+vo.getKey()+" width=700 height=350></iframe>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td width=30% class=text-center rowspan=7>");
		out.println("<img src="+vo.getPoster()+" width=100%>");
		out.println("</td>");
		out.println("<td width=70%>"+vo.getTitle()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>감독 : "+vo.getDirector()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>출연 : "+vo.getActor()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>장르 : "+vo.getGenre()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>장르 : "+vo.getGrade()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>기타 : "+vo.getRegdate()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>"+vo.getScore()+"</td>");
		out.println("</tr>");
		
		String story = vo.getStory();
		if (story.length() > 340) {
			story = story.substring(0, 340) + "...";
		}
		
		out.println("<tr>");
		out.println("<td colspan=2 height=200 valign=top>"+story+"</td>");
		out.println("</tr>");
		out.println("</table>");		//============
		out.println("</div>");
		
		// ============================================================================================================================================ 댓글 댓글 댓글 댓글 댓글 댓글 댓글 댓글 댓글 댓글
		
		
		out.println("<div class=col-sm-4>");					// col-sm-4		12개의 세로 화면영역 중에서 댓글화면이 4개를 가져감
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		
		out.println("<form method=post action=MovieDetail>");
		out.println("<input type=hidden name=mno value="+no+">");			// no(영화정보)를 감춰서 MovieDetail의 doPost 메소드로 전달 (댓글 입장에서 영화번호는 mno니까 name은 mno라고 전달) ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		out.println("<input type=text size=25 class=input-sm name=msg>");	// msg를 MovieDetail의 doPost 메소드로 전달 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		out.println("<input type=submit class=\"btn btn-sm btn-primary\" value=댓글쓰기>");
		out.println("</form>");
		
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		ArrayList<ReplyVO> rList = dao.movieReplyData(Integer.parseInt(no));
		
		//===============
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		
		HttpSession session = request.getSession();						// 세션 생성
		String id = (String)session.getAttribute("id");					// 로그인 페이지에서 저장된 id세션을 가져오기
		
		for(ReplyVO rvo : rList) {
			out.println("</table class=table>");
			out.println("<tr>");
			out.println("<td class=test-left>");
			out.println(rvo.getId()+"("+rvo.getDbday()+")");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=test-right>");
			
			if(id.equals(rvo.getId())){														// 세션 id와 rList의 id가 같을 때만 수정삭제버튼 나타내기
				out.println("<a href=# class=\"btn btn-xs btn-primary\">수정</a>");
				out.println("<a href=MovieDelete?no="+rvo.getNo()+"&mno="+vo.getNo()+" class=\"btn btn-xs btn-danger\">삭제</a>");		// MovieDelete 페이지로 rvo의 no(댓글번호), vo의 no(영화번호) 넘기기
			}
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td colspan=2 height=100 valign=top class=test-left>");
			out.println("<pre>"+rvo.getMsg()+"</pre>");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("</table>");
		}
		
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		//================
		
		
		
		out.println("</div>");
		
		out.println("</div>");
		out.println("</center>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setCharacterEncoding("EUC-KR");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		String mno = request.getParameter("mno");	// 영화정보(댓글 입장에서 영화번호는 mno니까) mno 받기 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		String msg = request.getParameter("msg");	// msg 받기 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	
		
		HttpSession session = request.getSession();						// 세션 생성
		String id = (String)session.getAttribute("id");					// 로그인 페이지에서 저장된 id세션을 가져오기
		
		ReplyVO vo = new ReplyVO();
		
		vo.setMno(Integer.parseInt(mno));
		vo.setMsg(msg);
		vo.setId(id);							// 아이디, 내용, 영화번호를 ReplyVO vo에 담아서 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		
		// DAO 전송
		MovieDAO dao = new MovieDAO();				// movieReplyInsert 메소드 실행해서 댓글 DB에 저장 ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		dao.movieReplyInsert(vo);
		
		
		// 화면 이동
		response.sendRedirect("MovieDetail?no=" + mno);
		
	}

}
