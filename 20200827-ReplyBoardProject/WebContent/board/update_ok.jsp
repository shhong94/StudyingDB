<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*, com.sist.dao.*"%>
    
<%
	try{
		request.setCharacterEncoding("UTF-8");
	}catch(Exception e){
		
	}

	String no = request.getParameter("no");			// 답변하기에서는 pno(상위글번호), 수정하기에서는 no(본인글번호)
	String strPage = request.getParameter("page");
	
	String name = request.getParameter("name");
	String subject = request.getParameter("subject");
	String content = request.getParameter("content");
	String pwd = request.getParameter("pwd");
	
	ReplyBoardDAO dao = new ReplyBoardDAO();
	ReplyBoardVO vo = new ReplyBoardVO();
	
	vo.setNo(Integer.parseInt(no));
	vo.setName(name);
	vo.setSubject(subject);
	vo.setContent(content);
	vo.setPwd(pwd);
	
	boolean bCheck = dao.boardUpdate(vo);									
	if(bCheck == true){
		response.sendRedirect("detail.jsp?page=" + strPage + "&no=" + no);
	}
	else{
%>
		<script>
			alert("비밀번호가 틀립니다");
			history.back();
		</script>
<%
	}
	
%>
