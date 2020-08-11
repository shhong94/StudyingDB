package com.sist.dao;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/index")
public class index extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=EUC-KR");
		PrintWriter out = response.getWriter();
		
		// ������ �޾ƿ���
		EmpDAO dao = new EmpDAO();
		ArrayList<EmpVO> list = dao.empAllData();
		
		out.print("<html>");
		out.print("<body>");
		out.print("<center>");
		out.print("<h1>��� ����</h1>");
	// �÷���
		out.print("<table width=1500 border=1 bordercolor=black>");
		out.print("<tr>");
		out.print("<th>���</th>");
		out.print("<th>�̸�</th>");
		out.print("<th>����</th>");
		out.print("<th>���</th>");
		out.print("<th>�Ի���</th>");
		out.print("<th>�޿�</th>");
		out.print("<th>������</th>");
		out.print("<th>�μ���ȣ</th>");
		out.print("</tr>");
		
	// ������
		for(EmpVO vo : list) {
			out.print("<tr>");
			out.print("<td>"+vo.getEmpno()+"</td>");
			out.print("<td>"+vo.getEname()+"</td>");
			out.print("<td>"+vo.getJob()+"</td>");
			out.print("<td>"+vo.getMgr()+"</td>");
			out.print("<td>"+vo.getHiredate()+"</td>");
			out.print("<td>"+vo.getSal()+"</td>");
			out.print("<td>"+vo.getComm()+"</td>");
			out.print("<td>"+vo.getDeptno()+"</td>");
			out.print("</tr>");
		}
		
		
		out.print("</table>");
		
		out.print("</center>");
		out.print("</body>");
		out.print("</html>");
	}

}
