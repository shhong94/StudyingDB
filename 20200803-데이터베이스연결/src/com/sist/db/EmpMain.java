package com.sist.db;

//1. 오라클과 연결하는 드라이버 설치
			// 2. 오라클 연결
			// 3. 오라클에 명령문 전송
			// 4. 실행된 데이터 읽어오기
			// 5, 출력

import java.sql.*;
import java.util.*;
public class EmpMain {
	
	
	public static void main(String[] args) {
		try {
			// 1. 드라이버 설치
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. 연결
			String url = "jdbc:oracle:thin:@localhost:1521:XE";		// 오라클 주소
			Connection conn = DriverManager.getConnection(url, "hr", "happy");
			
			// 3. 명령문 전송
			String sql = "SELECT empno, ename, job, sal FROM emp "	// 전송할 문장 작성 (문장 끝에 세미콜론 찍지 말기, 공백 확인하기)
							+"WHERE empno = 7788";
			PreparedStatement ps = conn.prepareStatement(sql);		// 오라클에 전송
			
			// 4. 데이터 읽어오기 / 출력
			ResultSet rs = ps.executeQuery();				// 읽어온 데이터를 rs에 저장
			while(rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
