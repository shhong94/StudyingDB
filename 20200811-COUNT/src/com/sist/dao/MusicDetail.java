package com.sist.dao;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/MusicDetail")	// <=========================================================================================== a태그의 웹페이지 이름 (파일명, 클래스명이 아님!!!!)
public class MusicDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		
		// 값 받기
		String mno = request.getParameter("mno");	// 웹상에서의 파라미터는 항상 String 형식으로 받아옴
		MusicDAO dao = new MusicDAO();
		MusicVO vo = dao.musicDetailData(Integer.parseInt(mno));
		
		PrintWriter out = response.getWriter();
		
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>&lt;"+vo.getTitle()+"&gt; 상세보기</h>");				// lt, gt : <, >
		
	// 유튜브 영상
		out.println("<table width=700>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<iframe src=http://youtube.com/embed/"+vo.getKey()+" width=700 height=400></iframe>");			// iframe : 웹에서 긁어온 동영상 삽입	video : pc에 저장된 영상 삽입
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
	// 유튜브 영상 및 상세정보
		out.println("<table width=700>");
		out.println("<tr>");
	// 포스터
		out.println("<td width=45% rowspan=3>");						// rowspan : 가로줄 합치기 (이미지 삽입할 공간 만들기)
		out.println("<img src="+vo.getPoster()+" width=100%>");			
		out.println("</td>");
	// 제목																// 포스터와 제목은 같은 tr내에 있음. 그래야 포스터에서 rowspan 이용 가능
		out.println("<td>");
		out.print(vo.getTitle());
		out.println("</td>");
		out.println("</tr>");
	// 가수
		out.println("<tr>");
		out.println("<td>");
		out.println(vo.getSinger());
		out.println("</td>");
		out.println("</tr");
	// 앨범
		out.println("<tr>");
		out.println("<td>");
		out.println(vo.getAlbum());
		out.println("</td>");
		out.println("</tr");
	// 목록으로 돌아오기
		out.println("<tr>");
		out.println("<td>");
		out.println("<a href=MusicServlet>목록</a>");
		out.println("</td>");
		out.println("</tr");	
		
		
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		
		
	}

}
