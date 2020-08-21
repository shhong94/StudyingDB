package com.sist.view;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sist.dao.MovieDAO;

import java.util.*;



@WebServlet("/MovieDelete")
public class MovieDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("no");		// 댓글번호
		String mno = request.getParameter("mno");	// 영화번호
		
		MovieDAO dao = new MovieDAO();
		dao.replyDelete(Integer.parseInt(no));
		
		response.sendRedirect("MovieDetail?no=" + mno);		// 삭제가 수행되고 영화번호에 해당되는 MovieDetail로 다시 돌아오기
	}

}
