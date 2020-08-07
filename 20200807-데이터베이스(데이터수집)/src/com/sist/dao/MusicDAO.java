package com.sist.dao;

import java.util.*;
import java.sql.*;

// 오라클에 데이터 집어넣기
public class MusicDAO {
	// 연결하는 변수
	private Connection conn;
	// 오라클로 SQL 전송하는 변수
	private PreparedStatement ps;
	// URL
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

	
	// 드라이버 등록
	public MusicDAO () {
		try {
			Class.forName("oracle.jdbc.driver.Oracle.Driver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 해제
	public void disConnection() {
		try {
			if(ps != null) {
				ps.close();
			}
			if(conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	/*
	mno NUMBER(3),
	title VARCHAR2(300),
	singer VARCHAR2(100),
	album VARCHAR2(200),
	poster VARCHAR2(1000),
	state CHAR(6),
	idcrement NUMBER(3),
	key VARCHAR2(50)
	 */
	
	// 기능 추가
	public void musicInsert(MusicVO vo) {	// 매개변수로 MusicVO 하나로 묶어서 전달하기
		try {
			getConnection();	// 연결
			
//		Statement 형식 ☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★
//			String sql = "INSERT INTO genie_music VALUES(" 
//					+ vo.getMno() + ",'" + vo.getTitle() + "','" + vo.getSinger() + "','"
//					+ vo.getAlbum() + "','" + vo.getPoster() + "','" + vo.getState() + "'," + vo.getIdcrement() + ",'" + vo.getKey() + "')";
			
			
//		PreparedStatement 형식 ☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★
			String sql = "INSERT INTO genie_music VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getMno());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getSinger());
			ps.setString(4, vo.getAlbum());
			ps.setString(5, vo.getPoster());
			ps.setString(6, vo.getState());
			ps.setInt(7, vo.getIdcrement());
			ps.setString(8, vo.getKey());
			
			// SQL 결과값 받기
			ps.executeUpdate();		// executeQuery : 값을 받아올 때(SELECT)		executeUpdate : 값을 주입, 수정할 때 (INSERT, UPDATE)
		} 
		
		
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();	// 해제
		}
	}
}
