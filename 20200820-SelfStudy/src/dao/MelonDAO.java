package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

import dao.MelonVO;

public class MelonDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public MelonDAO() {
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
	
	
	// 멜론 데이터 저장하기
	public void melonInsert(MelonVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO melon VALUES(melon_mno_seq.nextval, ?, ?, ?, ?, ?)";
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
	
	// 메뉴에 장르 출력하기 
	public ArrayList<String> melonGenreMenu() {
		ArrayList<String> list = new ArrayList<String>();
		
		try {
			getConnection();
			
			String sql = "SELECT genre FROM melon_genre ORDER BY no";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String genre = rs.getString(1);
				list.add(genre);
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
	
	
	// 목록 출력하기
	public ArrayList<MelonVO> melonAllData(int page, int cateno){
		ArrayList<MelonVO> list = new ArrayList<MelonVO>();
		
		try {
			getConnection();
			
			int rowSize = 10;
			int start = (page*rowSize) - (rowSize - 1);
			int end = page*rowSize;
			
			String sql ="SELECT mno, title, poster, singer, album,(RANK() OVER(ORDER BY mno)), num "
					+ "FROM (SELECT mno, title, poster, singer, album, rownum as num "
					+ "FROM (SELECT mno, title, poster, singer, album FROM melon WHERE cateno=? ORDER BY mno)) "		// ORDER BY 뒤에 WHERE cateno=?
					+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cateno);
			ps.setInt(2, start);
			ps.setInt(3, end);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				MelonVO vo = new MelonVO();
				vo.setMno(rs.getInt(1));
				vo.setTitle(rs.getString(2));
				vo.setPoster(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
				vo.setRank(rs.getInt(6));
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
