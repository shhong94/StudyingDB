package com.sist.dao;

import java.util.*;
import java.sql.*;

public class MusicDAO {
	// 오라클 연결하는 변수
	private Connection conn;
	// SQL 전송 변수
	private PreparedStatement ps;
	// 오라클 주소
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
	// 드라이버 설치 (생성자 이용)
	public MusicDAO() {
		try {
			Class.forName("oracle.jdbc.driver.Oracle.Driver");		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// 연결 메소드
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	// 해제 메소드
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
	// SQL 문장 전송/결과 저장 메소드
	public ArrayList<MusicVO> musicAllData(){
		ArrayList<MusicVO> list = new ArrayList<MusicVO>();
		
		try {
			// 오라클 연결
			getConnection();
			// SQL 문장 전송
			String sql = "SELECT mno, poster, title, singer, album FROM genie_music ORDER BY mno";
						
			ps = conn.prepareStatement(sql);			
			// 결과값 받기
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MusicVO vo = new MusicVO();	// VO형식으로 따로 따로 임시저장하고 list로 넘기기 위한 인스턴스
				vo.setMno(rs.getInt(1));
				vo.setPoster(rs.getString(2));
				vo.setTitle(rs.getString(3));
				vo.setSinger(rs.getString(4));
				vo.setAlbum(rs.getString(5));
			// ArrayList에 값 채우기
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
	// 상세보기 
	public MusicVO musicDetailData(int mno) {
		// 상세보기는 어차피 1개이기 때문에 "ArrayList<MusicVO> list"가 아니라 "MusicVO vo" 한개만 써도 됨
		MusicVO vo = new MusicVO();
		
		try {
			getConnection();
			
			String sql = "SELECT mno, title, singer, album, poster, key FROM genie_music WHERE mno =" + mno;		// SQL 내의 mno가  매개변수 mno와 같을 때 출력
			ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			// 데이터 채우기
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
