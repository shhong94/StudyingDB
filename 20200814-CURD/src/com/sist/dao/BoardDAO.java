package com.sist.dao;

import java.util.*;
import java.sql.*;

public class BoardDAO {
	// 연결하는 객체, sql전송 객체, 오라클 주소
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
	
	// 드라이버 설치 (생성자)
	public BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	// 연결 메소드
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	// 해제 메소드
	public void disConnection() {
		try {
			if (ps != null) {
				ps.close();
			}
			if(conn != null) {
				conn.close();
			}
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/*
	 * 	기능
	 * 	1. 목록 (게시판) 	SELECT
	 * 	2. 내용보기		SELECT + WHERE
	 * 	3. 글쓰기			INSERT
	 * 	4. 글수정			UPDATE
	 * 	5. 글삭제			DELETE
	 * 	6. 찾기			SELECT + LIKE
	 */
	
	
	// 1. 목록 (게시판)
	public ArrayList<BoardVO> boardListData(){
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT no, subject, name, regdate, hit "
							+ "FROM freeboard "
							+ "ORDER BY no DESC";				// 최신 게시물을 가장 위로 정렬
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
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
	
	
	
	// 2. 내용 보기 SELECT + WHERE
	public BoardVO boardDetail(int no) {		// "~~~~~~?no=" + vo.getNo() 통해서 받은 no를 파라미터로 삼는다
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			
		// ① 조회수 증가
			String sql = "UPDATE freeboard SET "		// 내용보기 수행 시에 조회수를 증가시키기 위해 UPDATE 실시
						+"hit = hit + 1 "
						+"WHERE no=?";					// 조건 : no가 파라미터 no일 때
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();							// 조회수 증가 실행
			
		// ② 게시글 내용 가져오기
			sql = "SELECT no, name, subject, content, regdate, hit "
				+"FROM freeboard "
				+"WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			
			rs.close();
			
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	
	
	
	
	
	// 3. 글쓰기 INSERT
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO freeboard (no, name, subject, content, pwd) "
						+"VAlUES((SELECT NVL(MAX(no)+1, 1) FROM freeboard), ?, ?, ?, ?)";		// NVL(MAX(no)+1, 1) : 게시글을 INSERT하면 그 게시글번호를 최대 번호+1로 지정하고, 게시물이 없으면 1에서 시작
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			ps.executeUpdate();		// 추가, 수정, 삭제는 executeQuery가 아니라 executeUpdate (커밋 자동수행)
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
