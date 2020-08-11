package com.sist.dao;

import java.sql.*;
import java.util.*;

/*
 * 	1. ����Ŭ�� �����ϴ� ����
 *  2. SQL �����ϴ�
 *  3. ����Ŭ �ּ�
 *  
 *  1. ������(����̹� ��ġ)
 *  2. ���� �޼ҵ�
 *  3. ���� �޼ҵ�
 *  4. SQL ����/��� �޴� �޼ҵ�
 */

public class EmpDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
// ����̹� ��ġ	
	public EmpDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
// ���� �޼ҵ�
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
// ���� �޼ҵ�
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
	
// SQL ����/����ޱ� �޼ҵ�
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
