package com.sist.db;

import java.sql.*;
import java.util.*;

public class DataBaseMain {

	public static void main(String[] args) {
		try {
			// 오라클 드라이버 설치
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 드라이버 설치
			
			// 연결
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			Connection conn = DriverManager.getConnection(url, "hr", "happy");
			
		// 사용자로부터 입력받기
			Scanner scan = new Scanner(System.in);
			System.out.print("검색어 입력 : ");
			String data = scan.next();
			
			// SQL 문장 전송
			String sql = "SELECT ename FROM emp WHERE ename LIKE '%'||?||'%'";	// 		|| : 문자열 결합	&& : 자바의 Scanner
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, data); 	// 물음표에 data 삽입
			
			// 데이터 읽기
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {	// rs의 첫번째값부터 읽기 시작해서 값이 더이상 없으면 false가 되고 반복문 빠져나옴
				System.out.println(rs.getString(1));				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
