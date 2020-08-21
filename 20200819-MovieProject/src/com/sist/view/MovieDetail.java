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
		
		String no = request.getParameter("no");							// MovieMain���� ���� no(��ȭ��ȣ)�� ��ȭ ������ ��� �غ� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		
		MovieDAO dao = new MovieDAO();
		MovieVO vo = dao.movieDetailData(Integer.parseInt(no));			// no(��ȭ��ȣ)�� �ش��ϴ� ������ ��� �޼ҵ� �����Ͽ� MovieVO vo ��ü�� ��� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		
		out.println("<html>");
		out.println("<head>");
		out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css\">");
		out.println("</head>");
		
		out.println("<body>");
		out.println("<div class=container>");
		out.println("<center>");
		out.println("<h1>��ȭ ������</h1>");
		out.println("<div class=row>");
		out.println("<div class=col-sm-8>");			// col-sm-8		12���� ���� ȭ�鿵�� �߿��� ��ȭ�󼼺��Ⱑ 8���� ������
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
		out.println("<td width=70%>���� : "+vo.getDirector()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>�⿬ : "+vo.getActor()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>�帣 : "+vo.getGenre()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>�帣 : "+vo.getGrade()+"</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td width=70%>��Ÿ : "+vo.getRegdate()+"</td>");
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
		
		// ============================================================================================================================================ ��� ��� ��� ��� ��� ��� ��� ��� ��� ���
		
		
		out.println("<div class=col-sm-4>");					// col-sm-4		12���� ���� ȭ�鿵�� �߿��� ���ȭ���� 4���� ������
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		
		out.println("<form method=post action=MovieDetail>");
		out.println("<input type=hidden name=mno value="+no+">");			// no(��ȭ����)�� ���缭 MovieDetail�� doPost �޼ҵ�� ���� (��� ���忡�� ��ȭ��ȣ�� mno�ϱ� name�� mno��� ����) �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		out.println("<input type=text size=25 class=input-sm name=msg>");	// msg�� MovieDetail�� doPost �޼ҵ�� ���� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		out.println("<input type=submit class=\"btn btn-sm btn-primary\" value=��۾���>");
		out.println("</form>");
		
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		
		ArrayList<ReplyVO> rList = dao.movieReplyData(Integer.parseInt(no));
		
		//===============
		out.println("<table class=table>");
		out.println("<tr>");
		out.println("<td>");
		
		HttpSession session = request.getSession();						// ���� ����
		String id = (String)session.getAttribute("id");					// �α��� ���������� ����� id������ ��������
		
		for(ReplyVO rvo : rList) {
			out.println("</table class=table>");
			out.println("<tr>");
			out.println("<td class=test-left>");
			out.println(rvo.getId()+"("+rvo.getDbday()+")");
			out.println("</td>");
			out.println("</tr>");
			
			out.println("<tr>");
			out.println("<td class=test-right>");
			
			if(id.equals(rvo.getId())){														// ���� id�� rList�� id�� ���� ���� ����������ư ��Ÿ����
				out.println("<a href=# class=\"btn btn-xs btn-primary\">����</a>");
				out.println("<a href=MovieDelete?no="+rvo.getNo()+"&mno="+vo.getNo()+" class=\"btn btn-xs btn-danger\">����</a>");		// MovieDelete �������� rvo�� no(��۹�ȣ), vo�� no(��ȭ��ȣ) �ѱ��
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
		
		String mno = request.getParameter("mno");	// ��ȭ����(��� ���忡�� ��ȭ��ȣ�� mno�ϱ�) mno �ޱ� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		String msg = request.getParameter("msg");	// msg �ޱ� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	
		
		HttpSession session = request.getSession();						// ���� ����
		String id = (String)session.getAttribute("id");					// �α��� ���������� ����� id������ ��������
		
		ReplyVO vo = new ReplyVO();
		
		vo.setMno(Integer.parseInt(mno));
		vo.setMsg(msg);
		vo.setId(id);							// ���̵�, ����, ��ȭ��ȣ�� ReplyVO vo�� ��Ƽ� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		
		// DAO ����
		MovieDAO dao = new MovieDAO();				// movieReplyInsert �޼ҵ� �����ؼ� ��� DB�� ���� �ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		dao.movieReplyInsert(vo);
		
		
		// ȭ�� �̵�
		response.sendRedirect("MovieDetail?no=" + mno);
		
	}

}
