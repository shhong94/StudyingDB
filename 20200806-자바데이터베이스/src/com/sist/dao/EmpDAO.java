package com.sist.dao;

import java.util.*;
import java.sql.*;

// DB 연결해주는 클래스 
public class EmpDAO {
	private Connection conn;	// 오라클과 연결 (Socket)
	private PreparedStatement ps;	// 송신, 수신 (BufferedReader, OutputStream이 내장되어 있는 클래스)
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";	// thin : 연결만 담당하는 무료 드라이버
	
	private static EmpDAO dao;
	
// 드라이버 등록
	public EmpDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
// 싱글턴 패턴 (메모리 공간을 하나만 사용) (Connection이 여러개 생성되면 부하가 걸림)
	public static EmpDAO newInstance() {
		if (dao == null) {			// dao가 null이면 생성
			dao = new EmpDAO();
		}
		return dao;					// null이 아니면 기존에 있던 것 쓴다		====> 한 번만 생성
	}
	
	
// 연결
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, "hr", "happy");
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
			System.out.println(e.getMessage());
		}
	}


// conn, ps의 게터 메소드
	public Connection getConn() {
		return conn;
	}

	public PreparedStatement getPs() {
		return ps;
	}
	
	

	
	
// ======================================================================================================================== 모든 DB 공통 사항
}
