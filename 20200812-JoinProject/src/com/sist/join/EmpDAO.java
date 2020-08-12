package com.sist.join;

import java.util.*;
import java.sql.*;

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
	
	
	public ArrayList<EmpVO> empDeptJoinData(){
		ArrayList<EmpVO> list = new ArrayList<EmpVO>();
		try {
			getConnection();
			
			String sql = "SELECT empno, ename, job, hiredate, sal, dname, loc "
						+ "FROM emp JOIN dept ON emp.deptno = dept.deptno";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				EmpVO vo = new EmpVO();
				vo.setEmpno(rs.getInt(1));
				vo.setEname(rs.getString(2));
				vo.setJob(rs.getString(3));
				vo.setHiredate(rs.getDate(4));
				vo.setSal(rs.getInt(5));
				vo.getDvo().setDname(rs.getString(6));	// ========================================================== EmpVO 안의 DeptVO dvo 를 통해서 데이터 삽입
				vo.getDvo().setLoc(rs.getString(7));
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
