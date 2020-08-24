package com.sist.dao;

import java.util.*;
import java.sql.*;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public BoardDAO() {
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
	
	
	
	// 게시판 목록 출력
	public ArrayList<BoardVO> boardAllData(){
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT no, name, subject, regdate, hit FROM jsp_board";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setName(rs.getString(2));
				vo.setSubject(rs.getString(3));
				vo.setRegdate(rs.getString(4));
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
	
	
}
