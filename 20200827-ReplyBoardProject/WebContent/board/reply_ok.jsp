<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
    
<%
	try{
		request.setCharacterEncoding("UTF-8");
	}catch(Exception e){
		
	}

	String pno = request.getParameter("pno");			// 답변글의 번호(no)가 아니라, 뿌리글의 번호(pno)
	String strPage = request.getParameter("page");
	
	String name = request.getParameter("name");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	String pwd = request.getParameter("pwd");
	
	ReplyBoardDAO dao = new ReplyBoardDAO();
	ReplyBoardVO vo = new ReplyBoardVO();
	
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	dao.boardReplyInsert(Integer.parseInt(pno), vo);
	
	
	response.sendRedirect("list.jsp?page=" + strPage);
%>
