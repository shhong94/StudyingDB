package com.sist.dao;

import java.util.*;
import java.sql.*;

public class BoardDAO {
	// �����ϴ� ��ü, sql���� ��ü, ����Ŭ �ּ�
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
	
	// ����̹� ��ġ (������)
	public BoardDAO() {
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
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	// ���� �޼ҵ�
	public void disConnection() {
		try {
			if (ps != null) {
				ps.close();
			}
			if(conn != null) {
				conn.close();
			}
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	/*
	 * 	���
	 * 	1. ��� (�Խ���) 	SELECT
	 * 	2. ���뺸��		SELECT + WHERE
	 * 	3. �۾���			INSERT
	 * 	4. �ۼ���			UPDATE
	 * 	5. �ۻ���			DELETE
	 * 	6. ã��			SELECT + LIKE
	 */
	
	
	// 1. ��� (�Խ���)
	public ArrayList<BoardVO> boardListData(){
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT no, subject, name, regdate, hit "
							+ "FROM freeboard "
							+ "ORDER BY no DESC";				// �ֽ� �Խù��� ���� ���� ����
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);
			}
			rs.close();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		
		return list;
	}
	
	
	
	// 2. ���� ���� SELECT + WHERE
	public BoardVO boardDetail(int no) {		// "~~~~~~?no=" + vo.getNo() ���ؼ� ���� no�� �Ķ���ͷ� ��´�
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			
		// �� ��ȸ�� ����
			String sql = "UPDATE freeboard SET "		// ���뺸�� ���� �ÿ� ��ȸ���� ������Ű�� ���� UPDATE �ǽ�
						+"hit = hit + 1 "
						+"WHERE no=?";					// ���� : no�� �Ķ���� no�� ��
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();							// ��ȸ�� ���� ����
			
		// �� �Խñ� ���� ��������
			sql = "SELECT no, name, subject, content, regdate, hit "
				+"FROM freeboard "
				+"WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			vo.setNo(rs.getInt(1));
			vo.setName(rs.getString(2));
			vo.setSubject(rs.getString(3));
			vo.setContent(rs.getString(4));
			vo.setRegdate(rs.getDate(5));
			vo.setHit(rs.getInt(6));
			
			rs.close();
			
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	
	
	
	
	
	// 3. �۾��� INSERT
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO freeboard (no, name, subject, content, pwd) "
						+"VAlUES((SELECT NVL(MAX(no)+1, 1) FROM freeboard), ?, ?, ?, ?)";		// NVL(MAX(no)+1, 1) : �Խñ��� INSERT�ϸ� �� �Խñ۹�ȣ�� �ִ� ��ȣ+1�� �����ϰ�, �Խù��� ������ 1���� ����
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			
			ps.executeUpdate();		// �߰�, ����, ������ executeQuery�� �ƴ϶� executeUpdate (Ŀ�� �ڵ�����)
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	
	
	
	
	
	
	
	
}
