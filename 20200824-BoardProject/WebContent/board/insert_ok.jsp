<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.sist.dao.*"%>
    
    
<!-- 전송받은 데이터를 오라클 연결만 시켜주는 파일 -->

<%
	try{											// 전송되는 데이터에 한글 포함시 디코딩 필요
		request.setCharacterEncoding("UTF-8");
	}
	catch(Exception e){}
	
	// 파라미터 받기
	String name = request.getParameter("name");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	String pwd = request.getParameter("pwd");
	
	// 데이터 모아서 DAO로 전송 준비
	BoardVO vo = new BoardVO();
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	// 오라클에 INSERT
	BoardDAO dao = new BoardDAO();
	dao.boardInsert(vo);	
	
	// 되돌아가기
	response.sendRedirect("list.jsp");
%>