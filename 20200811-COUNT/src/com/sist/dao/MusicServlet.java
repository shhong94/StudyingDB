package com.sist.dao;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@WebServlet("/MusicServlet")
public class MusicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");	// "전송하는 코드의 타입을 HTML 코드로 설정 + 한글 코드"
		PrintWriter out = response.getWriter();	// 메모리에 출력된 HTML코드를 브러우저에서 읽어감
		
		// 데이터 읽기
		MusicDAO dao = new MusicDAO();
		ArrayList<MusicVO> list = dao.musicAllData();	// list 안에 200개의 데이터가 저장되어 있음
		
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>뮤직 Top200</h1>");
		// 테이블
		out.println("<table width=1200 border=1 bordercolor=black>");	
		out.println("<tr>");
		out.println("<th>순위</th>");
		out.println("<th></th>");
		out.println("<th>곡명</th>");
		out.println("<th>가수명</th>");
		out.println("<th>앨범</th>");
		out.println("</tr>");
		
		// for문 돌리기
		for(MusicVO vo: list) {
			out.println("<tr>");
			out.println("<td>"+vo.getMno()+"</td>");
			out.println("<td><img src="+vo.getPoster()+" width=30 height=30></td>");
			out.println("<td><a href=MusicDetail?mno="+vo.getMno()+">"+vo.getTitle()+"</td>");					// ? : MusicDetail로 mno 값을 보내겠다 (매개변수 느낌)
			out.println("<td>"+vo.getSinger()+"</td>");
			out.println("<td>"+vo.getAlbum()+"</td>");
			out.println("</tr>");
		}
		
		out.println("</table>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
