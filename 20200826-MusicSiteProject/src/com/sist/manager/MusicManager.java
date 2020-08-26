package com.sist.manager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.MusicDAO;

public class MusicManager {
	
	public void musicAllData() {
		MusicDAO dao = new MusicDAO();
		
		try {
			int k = 1;
			for(int i = 1; i <= 5; i++) {
				Document doc = Jsoup.connect("https://www.genie.co.kr/genre/L0207?genreCode=L0207&pg=" + i).get();
				Elements title = doc.select("td.info a.title");
				Elements singer = doc.select("td.info a.artist");
				Elements album = doc.select("td.info a.albumtitle");
				Elements poster = doc.select("a.cover img");
				
				for(int j = 0; j < title.size(); j++) {
					try {
						MusicVO vo = new MusicVO();
						System.out.println("��ȣ : " + k++);
						System.out.println("cateno : 1");
						System.out.println("���� : " + title.get(j).text());
						System.out.println("������ : " + singer.get(j).text());
						System.out.println("�ٹ� : " + album.get(j).text());
						System.out.println("������ : " + poster.get(j).attr("src"));
						System.out.println("===================================================");
						
						vo.setCateno(10);
						vo.setTitle(title.get(j).text());
						vo.setSinger(singer.get(j).text());
						vo.setAlbum(album.get(j).text());
						vo.setPoster(poster.get(j).attr("src"));
						
						dao.musicInsert(vo);
						Thread.sleep(100);
					}
					catch(Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("END!!!!!");
		
		
	}
	
	
	
	public static void main(String[] args) {
		MusicManager m = new MusicManager();
		m.musicAllData();

	}

}
