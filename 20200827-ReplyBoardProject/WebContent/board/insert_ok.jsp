<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
    
    
<!-- 전송받은 데이터를 오라클 연결만 시켜주는 파일 -->

<%
	try{
		request.setCharacterEncoding("UTF-8");
	}
	catch(Exception e){
		
	}

	String name = request.getParameter("name");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	String pwd = request.getParameter("pwd");
	
	ReplyBoardVO vo = new ReplyBoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	ReplyBoardDAO dao = new ReplyBoardDAO();
	dao.boardInsert(vo);


	response.sendRedirect("list.jsp");
%>
