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
	public ArrayList<MovieVO> movieAllData(){
		ArrayList<MovieVO> list = new ArrayList<MovieVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT title, poster, regdate "
						+ "FROM daum_movie "
						+ "WHERE ceteno=1";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MovieVO vo = new MovieVO();
				vo.setTitle(rs.getString(1));
				vo.setPoster(rs.getString(2));
				vo.setRegdate(rs.getString(3));
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
