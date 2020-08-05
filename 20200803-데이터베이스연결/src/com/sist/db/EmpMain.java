package com.sist.db;

//1. ����Ŭ�� �����ϴ� ����̹� ��ġ
			// 2. ����Ŭ ����
			// 3. ����Ŭ�� ��ɹ� ����
			// 4. ����� ������ �о����
			// 5, ���

import java.sql.*;
import java.util.*;
public class EmpMain {
	
	
	public static void main(String[] args) {
		try {
			// 1. ����̹� ��ġ
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. ����
			String url = "jdbc:oracle:thin:@localhost:1521:XE";		// ����Ŭ �ּ�
			Connection conn = DriverManager.getConnection(url, "hr", "happy");
			
			// 3. ��ɹ� ����
			String sql = "SELECT empno, ename, job, sal FROM emp "	// ������ ���� �ۼ� (���� ���� �����ݷ� ���� ����, ���� Ȯ���ϱ�)
							+"WHERE empno = 7788";
			PreparedStatement ps = conn.prepareStatement(sql);		// ����Ŭ�� ����
			
			// 4. ������ �о���� / ���
			ResultSet rs = ps.executeQuery();				// �о�� �����͸� rs�� ����
			while(rs.next()) {
				System.out.println(rs.getInt(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4));
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
