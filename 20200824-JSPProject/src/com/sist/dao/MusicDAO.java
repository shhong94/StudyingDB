package com.sist.dao;

import java.util.*;
import java.sql.*;

public class MusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public MusicDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
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
	
//========================================================================================================================================================================
	
	
	// 음악 200순위까지 출력
	public ArrayList<MusicVO> musicAllData(){
		ArrayList<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT mno, title, singer, album, poster "
						+ "FROM genie_music ";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setSinger(rs.getString(3));
				vo.setAlbum(rs.getString(4));
				vo.setPoster(rs.getString(5));
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
	
	/*
	 *  제목 포스터 
	 */
	
	// 영화목록 출력
	public ArrayList<MovieVO> movieAllData(int page){
		
		ArrayList<MovieVO> list = new ArrayList<MovieVO>();
		
		try {
			getConnection();
			
			int rowSize = 10;
			int start = (page*rowSize) - (rowSize - 1);
			int end = page*rowSize;
			
			String sql = "SELECT title, regdate, genre, grade, actor, director, num "
						+ "FROM(SELECT title, regdate, genre, grade, actor, director, rownum as num "
						+ "FROM (SELECT title, regdate, genre, grade, actor, director FROM daum_movie ORDER BY no)) "
						+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MovieVO vo = new MovieVO();
				vo.setTitle(rs.getString(1));
				vo.setRegdate(rs.getString(2));
				vo.setGenre(rs.getString(3));
				vo.setGrade(rs.getString(4));
				vo.setActor(rs.getString(5));
				vo.setDirector(rs.getString(6));
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
	
	// 총 페이지
	public int boardTotalPage() {
		int total = 0;
		
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*) / 10.0) FROM daum_movie";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return total;
	}
}
