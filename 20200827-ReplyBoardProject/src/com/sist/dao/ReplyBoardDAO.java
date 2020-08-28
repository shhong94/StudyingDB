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
	
	// ��� ��� ==============================================================================================================================================
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
	
	// �Խñ� �� ���� Ȯ�� ==============================================================================================================================================
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
	
	// ���� ���� ==============================================================================================================================================
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
	
	// �󼼺��� + ���� ==============================================================================================================================================
	public ReplyBoardVO boardDetail(int no, int type) {
		ReplyBoardVO vo = new ReplyBoardVO();
		
		try {
			getConnection();
			
			String sql = "";
			if (type == 1) {															// type�� 1�̸� ��ȸ������+�󼼺��� (�󼼺���)
				sql = "UPDATE replyBoard SET hit = hit + 1 WHERE no = ?";				// type�� 1�� �ƴϸ� �󼼺��⸸ (����)
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				ps.executeUpdate();
			}
		
			
			sql = "SELECT no, regdate, name, hit, subject, content FROM replyBoard WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setRegdate(rs.getDate(2));
			vo.setName(rs.getString(3));
			vo.setHit(rs.getInt(4));
			vo.setSubject(rs.getString(5));
			vo.setContent(rs.getString(6));
			rs.close();
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	
	
	// �亯�ޱ� ==============================================================================================================================================
	public void boardReplyInsert(int root, ReplyBoardVO vo) {
		try {
			getConnection();
			
			// 1. �Ѹ����� group_id, group_step, group_tab ��������
			String sql = "SELECT group_id, group_step, group_tab "
					+ "FROM replyboard WHERE "
					+ "no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int gi = rs.getInt(1);
			int gs = rs.getInt(2);
			int gt = rs.getInt(3);
			rs.close();
			
			// 2. ���� : �Ѹ����� ���� �亯�� �������� ��� group_step�� ������ ������ �ڹٲ�
			sql = "UPDATE replyBoard SET group_step = group_step + 1 WHERE group_id=? AND group_step > ?";	// �Ѹ��� ���� �亯�۵��� group_step�� 1�� ���̱�
			ps = conn.prepareStatement(sql);																// group_step > ? : �Ѹ����� ����
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			
			// 3. INSERT
			sql = "INSERT INTO replyBoard(no, name, subject, content, pwd, group_id, group_step, group_tab, root) " + 
			"VALUES(rb_no_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?)";		// �� ��(�亯 �ƴ�) �ۼ��� ������ �׷�id�� ���� �����ؾߵ�
																											// �亯�� �Խõ� ������ �׷�id�� �ߺ��Ǿ��ϱ� ������ ������ ����ϸ� �ȵ�
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, root);
			ps.executeUpdate();
			
			// �Ѹ����� depth + 1
			sql = "UPDATE replyboard SET depth = depth + 1 WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	// �����ϱ� ==============================================================================================================================================
	public boolean boardUpdate(ReplyBoardVO vo) {
		boolean bCheck = false;
		try {
			getConnection();
			
			// db ���� ��й�ȣ ��������
			String sql = "SELECT pwd FROM replyBoard WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {
				bCheck = true;
				sql = "UPDATE replyboard SET name = ?, subject = ?, content = ? WHERE no = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				ps.executeUpdate();
				
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return bCheck;
	}
	
	// �˻��ϱ� ==============================================================================================================================================
	public ArrayList<ReplyBoardVO> boardFindData(String fs, String ss){
		ArrayList<ReplyBoardVO> list = new ArrayList<ReplyBoardVO>();
		
		try {
			getConnection();
			
			String sql="SELECT no, subject, name, regdate, hit FROM replyBoard "
					+ "WHERE " + fs + " LIKE '%" + ss + "%'";					// ps.setString(1, fs)��� �ۼ��ϸ� �ȵ�. fs�� setString �������� �ް� �Ǹ� �÷� ���ʿ� ����ǥ�� ���� (�� : 'name', 'subject')
																				// fs : �÷� 		ss : ã�´ܾ�
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyBoardVO vo = new ReplyBoardVO();
				vo.setNo(rs.getInt(1));
				vo.setSubject(rs.getString(2));
				vo.setName(rs.getString(3));
				vo.setRegdate(rs.getDate(4));
				vo.setHit(rs.getInt(5));
				list.add(vo);				
			}
			rs.close();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return list;
	}
	
	
	
	
	
}
