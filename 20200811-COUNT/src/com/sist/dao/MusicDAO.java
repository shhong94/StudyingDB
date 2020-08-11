package com.sist.dao;

import java.util.*;
import java.sql.*;

public class MusicDAO {
	// ����Ŭ �����ϴ� ����
	private Connection conn;
	// SQL ���� ����
	private PreparedStatement ps;
	// ����Ŭ �ּ�
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
	// ����̹� ��ġ (������ �̿�)
	public MusicDAO() {
		try {
			Class.forName("oracle.jdbc.driver.Oracle.Driver");		
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
	
//==========================================================================================================================================================
	// SQL ���� ����/��� ���� �޼ҵ�
	public ArrayList<MusicVO> musicAllData(){
		ArrayList<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			// ����Ŭ ����
			getConnection();
			// SQL ���� ����
			String sql = "SELECT mno, poster, title, singer, album FROM genie_music ORDER BY mno";
						
			ps = conn.prepareStatement(sql);			
			// ����� �ޱ�
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();	// VO�������� ���� ���� �ӽ������ϰ� list�� �ѱ�� ���� �ν��Ͻ�
				vo.setMno(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
			// ArrayList�� �� ä���
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
	
//==========================================================================================================================================================
	// �󼼺��� 
	public MusicVO musicDetailData(int mno) {
		// �󼼺���� ������ 1���̱� ������ "ArrayList<MusicVO> list"�� �ƴ϶� "MusicVO vo" �Ѱ��� �ᵵ ��
		MusicVO vo = new MusicVO();
		
		try {
			getConnection();
			
			String sql = "SELECT mno, title, singer, album, poster, key FROM genie_music WHERE mno =" + mno;		// SQL ���� mno��  �Ű����� mno�� ���� �� ���
			ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// ������ ä���
			vo.setMno(rs.getInt(1));
			vo.setTitle(rs.getString(2));
			vo.setSinger(rs.getString(3));
			vo.setAlbum(rs.getString(4));
			vo.setPoster(rs.getString(5));
			vo.setKey(rs.getString(6));
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
	

}
