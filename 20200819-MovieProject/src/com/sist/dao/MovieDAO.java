package com.sist.dao;

import java.sql.*;
import java.util.*;

import com.sist.manager.MovieVO;
import com.sist.manager.NewsVO;
import com.sist.recipe.ChefVO;
import com.sist.recipe.RecipeVO;

public class MovieDAO {

	// 연결하는 객체, sql전송 객체, 오라클 주소
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
	
	
	
	// 드라이버 설치 (생성자)
	public MovieDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	// 연결 메소드
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "hr", "happy");
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	
	// 해제 메소드
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
	 *  기능
	 *  1. 저장
	 */
	
	// DB에 영화정보 저장하는 메소드
	public void movieInsert(MovieVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO daum_movie VALUES("
						+"(SELECT NVL(MAX(no)+1, 1) FROM daum_movie), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getCateno());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getPoster());
			ps.setString(4, vo.getRegdate());
			ps.setString(5, vo.getGenre());
			ps.setString(6, vo.getGrade());
			ps.setString(7, vo.getActor());
			ps.setString(8, vo.getScore());
			ps.setString(9, vo.getDirector());
			ps.setString(10, vo.getStory());
			ps.setString(11, vo.getKey());
			
			ps.executeUpdate();
			
			
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	// DB에 뉴스정보 저장하는 메소드
	public void newsInsert(NewsVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO daum_news VALUES(?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getPoster());
			ps.setString(3, vo.getLink());
			ps.setString(4, vo.getContent());
			ps.setString(5, vo.getAuthor());
			
			ps.executeUpdate();
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	
	// 영화목록 출력하는 메소드
	public ArrayList<MovieVO> movieListData(int cno){
		ArrayList<MovieVO> list = new ArrayList<MovieVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT poster, title, no FROM daum_movie WHERE cateno = ? ORDER BY no";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, cno);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				MovieVO vo = new MovieVO();
				vo.setPoster(rs.getString(1));
				vo.setTitle(rs.getString(2));
				vo.setNo(rs.getInt(3));
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
	
	
	// 뉴스 목록 출력하는 메소드
	public ArrayList<NewsVO> newsListData() {
		ArrayList<NewsVO> list = new ArrayList<NewsVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT title, poster, link, content, author FROM daum_news";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				NewsVO vo = new NewsVO();
				vo.setTitle(rs.getString(1));
				vo.setPoster(rs.getString(2));
				vo.setLink(rs.getString(3));
				vo.setContent(rs.getString(4));
				vo.setAuthor(rs.getString(5));
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
	
	
	// 영화 상세보기 메소드
	public MovieVO movieDetailData(int no){
		MovieVO vo = new MovieVO();
		try {
			getConnection();
			
			String sql = "SELECT * FROM daum_movie WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			vo.setNo(rs.getInt(1));
			vo.setCateno(rs.getInt(2));
			vo.setTitle(rs.getString(3));
			vo.setPoster(rs.getString(4));
			vo.setRegdate(rs.getString(5));
			vo.setGenre(rs.getString(6));
			vo.setGrade(rs.getString(7));
			vo.setActor(rs.getString(8));
			vo.setScore(rs.getString(9));
			vo.setDirector(rs.getString(10));
			vo.setStory(rs.getString(11));
			vo.setKey(rs.getString(12));
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
	
	
	// 댓글 관련 (영화 상세페이지) INSERT UPDATE DELETE ========================================================================================================================
	
	// 댓글 목록 출력
	public ArrayList<ReplyVO> movieReplyData(int mno){
		ArrayList<ReplyVO> list = new ArrayList<ReplyVO>();
		
		try {
			getConnection();
			
			String sql = "SELECT no, mno, id, msg, to_char(regdate, 'YYYY-MM-DD HH24:MI:SS') FROM daum_reply WHERE mno = ? ORDER BY no DESC";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, mno);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ReplyVO vo = new ReplyVO();
				vo.setNo(rs.getInt(1));
				vo.setMno(rs.getInt(2));
				vo.setId(rs.getString(3));
				vo.setMsg(rs.getString(4));
				vo.setDbday(rs.getString(5));
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
	
	
	// DB에 댓글정보 저장
	public void movieReplyInsert(ReplyVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO daum_reply VALUES( (SELECT NVL(MAX(no)+1, 1) FROM daum_reply), ?, ?, ?, SYSDATE) ";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, vo.getMno());
			ps.setString(2, vo.getId());
			ps.setString(3, vo.getMsg());
			ps.executeUpdate();
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	// 로그인
	/*
	 * 	경우의 수
	 * 	1. 아이디가 없을 때
	 * 	2. 아이디는 있는데 비번이 틀릴 때
	 * 	3. 아이디와 비번이 둘다 맞을 때
	 */
	public String isLogin(String id, String pwd) {
		String result = "";
		
		try {
			getConnection();
			
			String sql = "SELECT COUNT(*) FROM member WHERE id = ?";	// 아이디가 존재?
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			int count = rs.getInt(1);									// count가 0이면 아이디없음, 1이면 아이디있음
			rs.close();
			
			if (count == 0) {													// 1. 아이디가 없음
				result = "NOID";
			}
			else {																// 2. 아이디가 있음
				sql = "SELECT pwd FROM member WHERE id = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				
				rs = ps.executeQuery();
				rs.next();
				String db_pwd = rs.getString(1);
				rs.close();
				
				if (db_pwd.equals(pwd)) {												// 2-1 아이디가 있고 비번도 맞음
					result = "OK";
				}
				else {																	// 2-2. 아이디가 있는데 비번은 틀림
					result = "NOPWD";
				}
				
			}
			
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
		
		return result;
	}
	
	
	// 댓글 삭제
	public void replyDelete(int no) {
		try {
			getConnection();
			String sql = "DELETE FROM daum_reply WHERE no = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ps.executeUpdate();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// 레시피 저장 ============================================================================================================================================================================== 레시피 저장
//			 ==============================================================================================================================================================================
//			 ==============================================================================================================================================================================
	public void recipeInsert(RecipeVO vo) {
		try {
			getConnection();
			
			String sql = "INSERT INTO recipe VALUES( (SELECT NVL(MAX(no)+1, 1) FROM recipe) , ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getTitle());
			ps.setString(2, vo.getPoster());
			ps.setString(3, vo.getChef());
			ps.setString(4, vo.getLink());
			
			
			ps.executeUpdate();
			
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
	public void chefInsert(ChefVO vo) {
		try {
			getConnection();
			String sql = "INSERT INTO chef VALUES(?, ?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, vo.getPoster());
			ps.setString(2, vo.getChef());
			ps.setString(3, vo.getMem_cont1());
			ps.setString(4, vo.getMem_cont3());
			ps.setString(5, vo.getMem_cont7());
			ps.setString(6, vo.getMem_cont2());
			ps.executeUpdate();
		} 
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		finally {
			disConnection();
		}
	}
	
		
		
		
}
