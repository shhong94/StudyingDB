package com.sist.dao;

import java.sql.*;
import java.util.*;

import jdk.nashorn.internal.ir.SetSplitState;

public class MyDAO {
// 변수/객체
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
// 드라이버 설치
	public MyDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
// 연결 메소드	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
// 해제 메소드
	public void disDonnection() {
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
	
	
// 로그인 기능
	public String isLogin(String ename, int empno) {
		String result = "";
		
		try {
			// 연결
			getConnection();
			
			// SQL 문장 전송
			String sql = "SELECT COUNT(*) FROM emp "
						+ "WHERE ename = ?";
			ps = conn.prepareStatement(sql);
			// ?에 값 채우기
			ps.setString(1, ename);
			
			// 데이터 받기
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);					// count가 0 : 사원이름이 없음("사원이름이 존재하지 않습니다"메시지 날리기)		count가 1 : 사원이름이 있음(사원번호 확인 절차 ㄱㄱ)
			rs.close();
			
			// 사원이름 조건
			if (count == 0) {				// 사원이름 존재 안함
				result = "No Name";
			}
			else {							// 사원이름 존재함
				sql = "SELECT empno FROM emp "
						+ "WHERE ename = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ename);
				rs = ps.executeQuery();
				rs.next();
				int db_empno = rs.getInt(1);
				rs.close();
				
				// 사번 조건
				if(empno == db_empno) {				// 입력한 사번과 DB 상의 사번이 같을 때  ==> 로그인
					result = ename;
				}
				else {								// 사번이 틀릴 때
					result = "No Empno";
				}
				
			}
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			// 해제
			disDonnection();
		}
		
		return result;
	}
	
	
	
}
