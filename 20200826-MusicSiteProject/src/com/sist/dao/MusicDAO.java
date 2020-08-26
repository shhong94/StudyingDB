package com.sist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import com.sist.manager.MusicVO;

public class MusicDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public MusicDAO() {
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
	
	
	// 음악 데이터 저장하기
	public void musicInsert(MusicVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO music VALUES(music_mno_seq.nextval, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getCateno());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getSinger());
			ps.setString(5, vo.getAlbum());
			ps.executeUpdate();
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	// 네비게이션바 장르 메뉴 
	public ArrayList<String> musicGenreAllData(){
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			getConnection();
			
			String sql = "SELECT genre FROM music_genre ORDER BY no";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String genre = rs.getString(1);
				list.add(genre);
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
	
	// 음악 출력
	public ArrayList<MusicVO> musicAllData(int cateno, int page){
		ArrayList<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			getConnection();
			
			int rowSize = 30;
			int start = (page*rowSize) - (rowSize - 1);
			int end = (page * rowSize);
			
			String sql = "SELECT mno, title, poster, singer, album, RANK() OVER(ORDER BY mno ASC), num "
					+ "FROM (SELECT mno, title, poster, singer, album, rownum as num "
					+ "FROM (SELECT mno, title, poster, singer, album FROM music WHERE cateno=? ORDER BY mno)) "
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cateno);
			ps.setInt(2, start);
			ps.setInt(3, end);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				vo.setRank(rs.getInt(6));
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
	
	// 장르 출력
	public String musicGetGenre(int cateno) {
		String genre = "";
		
		try {
			getConnection();
			
			String sql = "SELECT genre FROM music_genre WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cateno);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			genre = rs.getString(1);
			rs.close();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return genre;
	}
	
	// 총 페이지
	public int musicTotalPage(int cateno) {
		int total = 0;
		
		try {
			getConnection();
			
			String sql = "SELECT CEIL(COUNT(*) / 10.0) FROM music WHERE cateno=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cateno);
			
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
