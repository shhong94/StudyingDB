package com.sist.dao;

import java.sql.*;
import java.util.*;

public class ZipcodeDAO {
	private Connection conn;	// 연결
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

	
	public ZipcodeDAO () {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
		
		}
	}
	
	
	// 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			
		}
	}
	
	
	// 닫기
	public void disConnection() {
		try {
			if(ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			
		}
	}
		
		
	// 우편번호 찾기
		public ArrayList<ZipcodeVO> postfind(String dong){
			ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
			
			try {
				// 연결
				getConnection();
				
				// SQL 문장 전송
				String sql = "SELECT * FROM zipcode WHERE dong LIKE '%'||?||'%'";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dong);
				
				// 실행
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					ZipcodeVO vo = new ZipcodeVO();	// new를 통해 메모리를 개별적으로 할당해야 관리하기 편함
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
					list.add(vo);
				}
 			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				// 끊기
				disConnection();
			}
			
			
			return list;
		}

}
