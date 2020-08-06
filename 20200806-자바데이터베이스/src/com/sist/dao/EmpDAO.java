package com.sist.dao;

import java.util.*;
import java.sql.*;

// DB �������ִ� Ŭ���� 
public class EmpDAO {
	private Connection conn;	// ����Ŭ�� ���� (Socket)
	private PreparedStatement ps;	// �۽�, ���� (BufferedReader, OutputStream�� ����Ǿ� �ִ� Ŭ����)
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";	// thin : ���Ḹ ����ϴ� ���� ����̹�
	
	private static EmpDAO dao;
	
// ����̹� ���
	public EmpDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
// �̱��� ���� (�޸� ������ �ϳ��� ���) (Connection�� ������ �����Ǹ� ���ϰ� �ɸ�)
	public static EmpDAO newInstance() {
		if (dao == null) {			// dao�� null�̸� ����
			dao = new EmpDAO();
		}
		return dao;					// null�� �ƴϸ� ������ �ִ� �� ����		====> �� ���� ����
	}
	
	
// ����
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
// ����
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


// conn, ps�� ���� �޼ҵ�
	public Connection getConn() {
		return conn;
	}

	public PreparedStatement getPs() {
		return ps;
	}
	
	

	
	
// ======================================================================================================================== ��� DB ���� ����
}
