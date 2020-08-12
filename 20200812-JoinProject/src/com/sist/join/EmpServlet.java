package com.sist.join;

import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		EmpDAO dao = new EmpDAO();
		ArrayList<EmpVO> list = dao.empDeptJoinData();
		
		out.println("<html>");
		out.println("<body>");
		out.println("<center>");
		
		out.println("<h1>사원 정보</h>");
		out.println("<table border=1 width=700>");
		out.println("<tr>");
		out.println("<th>사번</th>");
		out.println("<th>이름</th>");
		out.println("<th>직위</th>");
		out.println("<th>입사일</th>");
		out.println("<th>급여</th>");
		out.println("<th>부서명</th>");
		out.println("<th>근무지</th>");
		out.println("</tr>");
		
		for(EmpVO vo : list) {
			out.println("<tr>");
			out.println("<th>"+vo.getEmpno()+"</th>");
			out.println("<th>"+vo.getEname()+"</th>");
			out.println("<th>"+vo.getJob()+"</th>");
			out.println("<th>"+vo.getHiredate()+"</th>");
			out.println("<th>"+vo.getSal()+"</th>");
			out.println("<th>"+vo.getDvo().getDname()+"</th>");		// ==================================================== EmpVO 내의 Dvo를 통하여 Dname, Loc 가져오기
			out.println("<th>"+vo.getDvo().getLoc()+"</th>");
			out.println("</tr>");
		}
		
		
		
		
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

}
