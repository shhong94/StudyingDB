package com.sist.manager;

/*
 * ◆ 웹개발 공부 순서
 * 	1. 데이터베이스의 테이블 제작 방법
 * 	2. DML 연습(SELECT, INSERT, UPDATE, DELETE)
 *  3. 자바에서 제어 (SQL문장을 오라클로 전송)
 *  4. 화면에 출력 (HTML, CSS)
 *  5. 자바스크립트 배우기
 *  
 * ◆ 웹개발에 필요한 언어
 * 	1. 오라클
 *  2. 자바
 *  3. HTML
 *  4. CSS / 자바스크립트
 */



public class NewsVO {
    private String title;
    private String poster;
    private String link;
    private String content;
    private String author;
    
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPoster() {
		return poster;
	}
	public void setPoster(String poster) {
		this.poster = poster;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
    
}
