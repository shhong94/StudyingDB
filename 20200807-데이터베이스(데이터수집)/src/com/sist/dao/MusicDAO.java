package com.sist.dao;

import java.util.*;
import java.sql.*;

// ����Ŭ�� ������ ����ֱ�
public class MusicDAO {
	// �����ϴ� ����
	private Connection conn;
	// ����Ŭ�� SQL �����ϴ� ����
	private PreparedStatement ps;
	// URL
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";

	
	// ����̹� ���
	public MusicDAO () {
		try {
			Class.forName("oracle.jdbc.driver.Oracle.Driver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	// ����
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
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
			
		}
	}
	
	/*
	mno NUMBER(3),
	title VARCHAR2(300),
	singer VARCHAR2(100),
	album VARCHAR2(200),
	poster VARCHAR2(1000),
	state CHAR(6),
	idcrement NUMBER(3),
	key VARCHAR2(50)
	 */
	
	// ��� �߰�
	public void musicInsert(MusicVO vo) {	// �Ű������� MusicVO �ϳ��� ��� �����ϱ�
		try {
			getConnection();	// ����
			
//		Statement ���� �١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١�
//			String sql = "INSERT INTO genie_music VALUES(" 
//					+ vo.getMno() + ",'" + vo.getTitle() + "','" + vo.getSinger() + "','"
//					+ vo.getAlbum() + "','" + vo.getPoster() + "','" + vo.getState() + "'," + vo.getIdcrement() + ",'" + vo.getKey() + "')";
			
			
//		PreparedStatement ���� �١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١�
			String sql = "INSERT INTO genie_music VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getMno());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getSinger());
			ps.setString(4, vo.getAlbum());
			ps.setString(5, vo.getPoster());
			ps.setString(6, vo.getState());
			ps.setInt(7, vo.getIdcrement());
			ps.setString(8, vo.getKey());
			
			// SQL ����� �ޱ�
			ps.executeUpdate();		// executeQuery : ���� �޾ƿ� ��(SELECT)		executeUpdate : ���� ����, ������ �� (INSERT, UPDATE)
		} 
		
		
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();	// ����
		}
	}
}
