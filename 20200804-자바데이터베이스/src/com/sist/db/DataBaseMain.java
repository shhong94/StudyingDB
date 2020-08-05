package com.sist.db;

import java.sql.*;
import java.util.*;

public class DataBaseMain {

	public static void main(String[] args) {
		try {
			// ����Ŭ ����̹� ��ġ
			Class.forName("oracle.jdbc.driver.OracleDriver");	// ����̹� ��ġ
			
			// ����
			String url = "jdbc:oracle:thin:@localhost:1521:XE";
			Connection conn = DriverManager.getConnection(url, "hr", "happy");
			
		// ����ڷκ��� �Է¹ޱ�
			Scanner scan = new Scanner(System.in);
			System.out.print("�˻��� �Է� : ");
			String data = scan.next();
			
			// SQL ���� ����
			String sql = "SELECT ename FROM emp WHERE ename LIKE '%'||?||'%'";	// 		|| : ���ڿ� ����	&& : �ڹ��� Scanner
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, data); 	// ����ǥ�� data ����
			
			// ������ �б�
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {	// rs�� ù��°������ �б� �����ؼ� ���� ���̻� ������ false�� �ǰ� �ݺ��� ��������
				System.out.println(rs.getString(1));				
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
