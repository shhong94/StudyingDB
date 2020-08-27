package com.sist.dao;

import java.util.*;
import java.sql.*;

public class ReplyBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public ReplyBoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void disConnection() {
		try {
			if(ps != null) {
				ps.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * 1. 목록 출력
	 * 2. 상세보기
	 * 3. 새글 등록
	 * 4. 답변하기		서브쿼리
	 * 5. 수정하기
	 * 6. 삭제하기		서브쿼리
	 * 7. 찾기		LIKE, REGEXP_LIKE
	 */
	
	// 목록 출력
	public ArrayList<ReplyBoardVO> boardListData(int page){
		ArrayList<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		
		try {
			getConnection();
			
			int rowSize = 10;
			int start = (rowSize*page) - (rowSize - 1);
			int end = rowSize*page;
			
			String sql="SELECT no, subject, name, regdate, hit, group_tab, num "
					+ "FROM (SELECT no, subject, name, regdate, hit, group_tab, rownum as num "
					+ "FROM (SELECT no, subject, name, regdate, hit, group_tab "
					+ "FROM replyboard ORDER BY group_id DESC, group_step ASC)) "	// group_id DESC, group_step ASC : 그룹은 최신순으로, 그룹 내에서는 오름차순으로
					+ "WHERE num BETWEEN ? AND ?";									 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
				list.add(vo);				
			}
			rs.close();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return list;
	}
	
	// 게시글 총 개수 확인
	public int boardRowCount() {
		int count = 0;
		try {
			getConnection();
			
			String sql = "SELECT COUNT(*) FROM replyBoard";
			ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return count;
	}
	
	// 새글 쓰기
	public void boardInsert(ReplyBoardVO vo) {
		try {
			getConnection();
			
			String sql ="INSERT INTO replyBoard(no, name, subject, content, pwd, group_id) " + 
					"VALUES(rb_no_seq.nextval, ?, ?, ?, ?, (SELECT NVL(MAX(group_id)+1, 1) FROM replyBoard))";		// 새 글(답변 아님) 작성할 때마다 그룹id가 새로 증가해야됨
																													// 답변이 게시될 때마다 그룹id가 중복되야하기 때문에 시퀀스 사용하면 안됨
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	
	
	
	
	
	
	
}
