package com.sist.dao;

import java.sql.*;
import java.util.*;

import jdk.nashorn.internal.ir.SetSplitState;

public class MyDAO {
// ����/��ü
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
// ����̹� ��ġ
	public MyDAO() {
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
	
	
// �α��� ���
	public String isLogin(String ename, int empno) {
		String result = "";
		
		try {
			// ����
			getConnection();
			
			// SQL ���� ����
			String sql = "SELECT COUNT(*) FROM emp "
						+ "WHERE ename = ?";
			ps = conn.prepareStatement(sql);
			// ?�� �� ä���
			ps.setString(1, ename);
			
			// ������ �ޱ�
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);					// count�� 0 : ����̸��� ����("����̸��� �������� �ʽ��ϴ�"�޽��� ������)		count�� 1 : ����̸��� ����(�����ȣ Ȯ�� ���� ����)
			rs.close();
			
			// ����̸� ����
			if (count == 0) {				// ����̸� ���� ����
				result = "No Name";
			}
			else {							// ����̸� ������
				sql = "SELECT empno FROM emp "
						+ "WHERE ename = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, ename);
				rs = ps.executeQuery();
				rs.next();
				int db_empno = rs.getInt(1);
				rs.close();
				
				// ��� ����
				if(empno == db_empno) {				// �Է��� ����� DB ���� ����� ���� ��  ==> �α���
					result = ename;
				}
				else {								// ����� Ʋ�� ��
					result = "No Empno";
				}
				
			}
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			// ����
			disDonnection();
		}
		
		return result;
	}
	
	
	
}
