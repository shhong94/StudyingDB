package com.sist.dao;

import java.sql.*;
import java.util.*;

/*
 * 	1. 오라클과 연결하는 변수
 *  2. SQL 전송하는
 *  3. 오라클 주소
 *  
 *  1. 생성자(드라이버 설치)
 *  2. 연결 메소드
 *  3. 해제 메소드
 *  4. SQL 전송/결과 받는 메소드
 */

public class EmpDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
// 드라이버 설치	
	public EmpDAO() {
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
	public void disonnection() {
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
	
// SQL 전송/결과받기 메소드
	public ArrayList<EmpVO> empAllData() {
		ArrayList<EmpVO> list = new ArrayList<EmpVO>();

		try {
			getConnection();
			
			String sql = "SELECT empno, ename, job, mgr, hiredate, sal, comm, deptno FROM emp";
			ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setMgr(rs.getInt(4));
				vo.setHiredate(rs.getDate(5));
				vo.setSal(rs.getInt(6));
				vo.setComm(rs.getInt(7));
				vo.setDeptno(rs.getInt(8));
				list.add(vo);
			}
			rs.close();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disonnection();
		}
		return list;
	}
}
