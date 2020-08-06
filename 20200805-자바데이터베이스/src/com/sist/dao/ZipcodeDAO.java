package com.sist.dao;

import java.sql.*;
import java.util.*;

public class ZipcodeDAO {
	private Connection conn;	// 연결 	// DB와 연결하는 객체
	private PreparedStatement ps;	// SQL 문장을 담아서 전달. 매개변수를 set 지정해줘야함
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";	// 연결할 DB 주소

	
	public ZipcodeDAO () {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// DriverManager 생성
		} catch (Exception e) {
		
		}
	}
	
	
	// 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");	// 생성한 DriverManager로 DB와 연결
		} catch (Exception e) {
			
		}
	}
	
	
	// 닫기
	public void disConnection() {
		try {
			if(ps != null) {	// 전달할 SQL문장이 없을시 ps 종료
				ps.close();
			}
			if (conn != null) {	//	DB연결 객체가 없을시 연결 종료
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
				ps = conn.prepareStatement(sql);	// conn 객체 이용하여 문장 전잘
				ps.setString(1, dong);	// 매개변수 지정
				
				// 실행
				ResultSet rs = ps.executeQuery();	// SQL 문장 결과에 따른 데이터가 저장된 곳
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
