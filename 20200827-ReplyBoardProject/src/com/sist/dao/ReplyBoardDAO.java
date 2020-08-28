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
	
	// 목록 출력 ==============================================================================================================================================
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
	
	// 게시글 총 개수 확인 ==============================================================================================================================================
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
	
	// 새글 쓰기 ==============================================================================================================================================
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
	
	// 상세보기 + 수정 ==============================================================================================================================================
	public ReplyBoardVO boardDetail(int no, int type) {
		ReplyBoardVO vo = new ReplyBoardVO();
		
		try {
			getConnection();
			
			String sql = "";
			if (type == 1) {															// type이 1이면 조회수증가+상세보기 (상세보기)
				sql = "UPDATE replyBoard SET hit = hit + 1 WHERE no = ?";				// type이 1이 아니면 상세보기만 (수정)
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		
			
			sql = "SELECT no, regdate, name, hit, subject, content FROM replyBoard WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setRegdate(rs.getDate(2));
			vo.setName(rs.getString(3));
			vo.setHit(rs.getInt(4));
			vo.setSubject(rs.getString(5));
			vo.setContent(rs.getString(6));
			rs.close();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	
	
	// 답변달기 ==============================================================================================================================================
	public void boardReplyInsert(int root, ReplyBoardVO vo) {
		try {
			getConnection();
			
			// 1. 뿌리글의 group_id, group_step, group_tab 가져오기
			String sql = "SELECT group_id, group_step, group_tab "
					+ "FROM replyboard WHERE "
					+ "no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi = rs.getInt(1);
			int gs = rs.getInt(2);
			int gt = rs.getInt(3);
			rs.close();
			
			// 2. 문제 : 뿌리글이 같은 답변이 여러개일 경우 group_step이 꼬여서 순서가 뒤바뀜
			sql = "UPDATE replyBoard SET group_step = group_step + 1 WHERE group_id=? AND group_step > ?";	// 뿌리글 외의 답변글들의 group_step을 1씩 높이기
			ps = conn.prepareStatement(sql);																// group_step > ? : 뿌리글은 제외
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			
			// 3. INSERT
			sql = "INSERT INTO replyBoard(no, name, subject, content, pwd, group_id, group_step, group_tab, root) " + 
			"VALUES(rb_no_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";		// 새 글(답변 아님) 작성할 때마다 그룹id가 새로 증가해야됨
																											// 답변이 게시될 때마다 그룹id가 중복되야하기 때문에 시퀀스 사용하면 안됨
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, root);
			ps.executeUpdate();
			
			// 뿌리글의 depth + 1
			sql = "UPDATE replyboard SET depth = depth + 1 WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	// 수정하기 ==============================================================================================================================================
	public boolean boardUpdate(ReplyBoardVO vo) {
		boolean bCheck = false;
		try {
			getConnection();
			
			// db 내의 비밀번호 가져오기
			String sql = "SELECT pwd FROM replyBoard WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {
				bCheck = true;
				sql = "UPDATE replyboard SET name = ?, subject = ?, content = ? WHERE no = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				ps.executeUpdate();
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return bCheck;
	}
	
	// 검색하기 ==============================================================================================================================================
	public ArrayList<ReplyBoardVO> boardFindData(String fs, String ss){
		ArrayList<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		
		try {
			getConnection();
			
			String sql="SELECT no, subject, name, regdate, hit FROM replyBoard "
					+ "WHERE " + fs + " LIKE '%" + ss + "%'";					// ps.setString(1, fs)라고 작성하면 안됨. fs를 setString 형식으로 받게 되면 컬럼 양쪽에 따옴표가 붙음 (예 : 'name', 'subject')
																				// fs : 컬럼 		ss : 찾는단어
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);				
			}
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return list;
	}
	
	
	
	
	
}
