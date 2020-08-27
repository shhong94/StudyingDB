package com.sist.dao;

import java.util.*;
import java.sql.*;

public class ReplyBoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public ReplyBoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(url, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
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
	
	/*
	 * 1. ��� ���
	 * 2. �󼼺���
	 * 3. ���� ���
	 * 4. �亯�ϱ�		��������
	 * 5. �����ϱ�
	 * 6. �����ϱ�		��������
	 * 7. ã��		LIKE, REGEXP_LIKE
	 */
	
	// ��� ���
	public ArrayList<ReplyBoardVO> boardListData(int page){
		ArrayList<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		
		try {
			getConnection();
			
			int rowSize = 10;
			int start = (rowSize*page) - (rowSize - 1);
			int end = rowSize*page;
			
			String sql="SELECT no, subject, name, regdate, hit, group_tab, num "
					+ "FROM (SELECT no, subject, name, regdate, hit, group_tab, rownum as num "
					+ "FROM (SELECT no, subject, name, regdate, hit, group_tab "
					+ "FROM replyboard ORDER BY group_id DESC, group_step ASC)) "	// group_id DESC, group_step ASC : �׷��� �ֽż�����, �׷� �������� ������������
					+ "WHERE num BETWEEN ? AND ?";									 
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				vo.setGroup_tab(rs.getInt(6));
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
	
	// �Խñ� �� ���� Ȯ��
	public int boardRowCount() {
		int count = 0;
		try {
			getConnection();
			
			String sql = "SELECT COUNT(*) FROM replyBoard";
			ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			count = rs.getInt(1);
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return count;
	}
	
	// ���� ����
	public void boardInsert(ReplyBoardVO vo) {
		try {
			getConnection();
			
			String sql ="INSERT INTO replyBoard(no, name, subject, content, pwd, group_id) " + 
					"VALUES(rb_no_seq.nextval, ?, ?, ?, ?, (SELECT NVL(MAX(group_id)+1, 1) FROM replyBoard))";		// �� ��(�亯 �ƴ�) �ۼ��� ������ �׷�id�� ���� �����ؾߵ�
																													// �亯�� �Խõ� ������ �׷�id�� �ߺ��Ǿ��ϱ� ������ ������ ����ϸ� �ȵ�
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	
	
	
	
	
	
	
}
