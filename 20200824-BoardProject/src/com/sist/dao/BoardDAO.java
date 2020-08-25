package com.sist.dao;

import java.util.*;
import java.sql.*;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String url = "jdbc:oracle:thin:@localhost:1521:XE";
	
	public BoardDAO() {
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
	
	
	
	// �Խ��� ��� ���
	public ArrayList<BoardVO> boardAllData(int page){							// page ������ �ޱ�	�ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ�
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();
		
		try {
			getConnection();
			
			int rowSize = 10;
			int start = (page * rowSize) - (rowSize - 1);						// ����¡ : BETWEEN start AND end	�ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ�
			int end = (page * rowSize);
			
			String sql = "SELECT no, subject, name, regdate, hit, num "
						+ "FROM (SELECT no, subject, name, regdate, hit, rownum as num "
						+ "FROM (SELECT no, subject, name, regdate, hit "
						+ "FROM jsp_board ORDER BY no DESC)) "
						+ "WHERE num BETWEEN ? AND ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
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
	
	
	// �� ������	�ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ١ڡ�
	public int boardTotalPage() {
		int total = 0;
		
		try {
			getConnection();
			
			String sql = "SELECT CEIL(COUNT(*)/10.0) FROM jsp_board";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return total;
	}
	
	// �Խñ� �󼼺���
	public BoardVO boardDetail(int no) {
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			
			// ��ȸ�� ����
			String sql = "UPDATE jsp_board SET "
						+ "hit = hit + 1 "
						+ "WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
			// �󼼺���
			sql = "SELECT no, regdate, name, hit, subject, content "
						+ "FROM jsp_board "
						+ "WHERE no = ?";
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
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	
	
	// �۾���
	public void boardInsert(BoardVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO jsp_board VALUES(jb_no_seq.nextval, ?, ?, ?, ?, SYSDATE, 0)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getPwd());
			ps.executeUpdate();
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	// ���� �� ���� �ҷ�����
	public BoardVO boardUpdateData(int no) {
		BoardVO vo = new BoardVO();
		
		try {
			getConnection();
			
			
			// �󼼺���
			String sql = "SELECT no, regdate, name, hit, subject, content "
						+ "FROM jsp_board "
						+ "WHERE no = ?";
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
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return vo;
	}
	
	
	// �� ����
	public boolean boardUpdate(BoardVO vo) {
		boolean bCheck = false;
		try {
			getConnection();
			
			// ��й�ȣ ��ġ ���� Ȯ��
			String sql = "SELECT pwd FROM jsp_board WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString(1);
			rs.close();
			
			if(db_pwd.equals(vo.getPwd())) {		// ����� ���� ��
				bCheck = true;
				// ���� ����
				sql = "UPDATE jsp_board SET name=?, subject=?, content=? WHERE no=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, vo.getName());
				ps.setString(2, vo.getSubject());
				ps.setString(3, vo.getContent());
				ps.setInt(4, vo.getNo());
				ps.executeUpdate();
			}
			else {									// �ٸ� ��
				bCheck = false;
			}
		
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		return bCheck;
	}
	
}
