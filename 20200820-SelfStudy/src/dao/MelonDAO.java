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
	
	
}
