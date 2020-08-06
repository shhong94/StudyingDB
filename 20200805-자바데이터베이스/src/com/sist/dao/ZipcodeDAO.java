package com.sist.dao;

import java.sql.*;
import java.util.*;

public class ZipcodeDAO {
	private Connection conn;	// ���� 	// DB�� �����ϴ� ��ü
	private PreparedStatement ps;	// SQL ������ ��Ƽ� ����. �Ű������� set �����������
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";	// ������ DB �ּ�

	
	public ZipcodeDAO () {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// DriverManager ����
		} catch (Exception e) {
		
		}
	}
	
	
	// ����
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");	// ������ DriverManager�� DB�� ����
		} catch (Exception e) {
			
		}
	}
	
	
	// �ݱ�
	public void disConnection() {
		try {
			if(ps != null) {	// ������ SQL������ ������ ps ����
				ps.close();
			}
			if (conn != null) {	//	DB���� ��ü�� ������ ���� ����
				conn.close();
			}
		} catch (Exception e) {
			
		}
	}
		
		
	// �����ȣ ã��
		public ArrayList<ZipcodeVO> postfind(String dong){
			ArrayList<ZipcodeVO> list = new ArrayList<ZipcodeVO>();
			
			try {
				// ����
				getConnection();
				
				// SQL ���� ����
				String sql = "SELECT * FROM zipcode WHERE dong LIKE '%'||?||'%'";
				ps = conn.prepareStatement(sql);	// conn ��ü �̿��Ͽ� ���� ����
				ps.setString(1, dong);	// �Ű����� ����
				
				// ����
				ResultSet rs = ps.executeQuery();	// SQL ���� ����� ���� �����Ͱ� ����� ��
				while(rs.next()) {
					ZipcodeVO vo = new ZipcodeVO();	// new�� ���� �޸𸮸� ���������� �Ҵ��ؾ� �����ϱ� ����
					vo.setZipcode(rs.getString(1));
					vo.setSido(rs.getString(2));
					vo.setGugun(rs.getString(3));
					vo.setDong(rs.getString(4));
					vo.setBunji(rs.getString(5));
					list.add(vo);
				}
 			} 
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
			finally {
				// ����
				disConnection();
			}
			
			
			return list;
		}

}
