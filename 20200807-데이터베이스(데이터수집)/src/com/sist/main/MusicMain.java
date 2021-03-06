package com.sist.main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.sist.dao.MusicDAO;
import com.sist.dao.MusicVO;

public class MusicMain {

	public static void main(String[] args) {
//		MusicDAO dao = new MusicDAO();
		
		try {
			int k = 1;
			
			for(int i = 1; i <= 4; i++) {
				Document doc = Jsoup.connect("https://www.genie.co.kr/chart/top200?ditc=D&ymd=20200807&hh=09&rtm=Y&pg=" + i).get();
				
				Elements title = doc.select("td.info a.title");
				Elements singer = doc.select("td.info a.artist");
				Elements album = doc.select("td.info a.albumtitle");
				Elements poster = doc.select("a.cover img");
				Elements temp = doc.select("span.rank");
				
				for(int j = 0; j < title.size(); j ++) {
					System.out.println("순위 : " + k);
					System.out.println("노래명 : " + title.get(j).text());
					System.out.println("가수 : " + singer.get(j).text());
					System.out.println("앨범 : " + album.get(j).text());
					System.out.println("포스터 : " + poster.get(j).attr("src"));
//					System.out.println("상태 : " + temp.get(j).text());
					String str = temp.get(j).text();
					String idcrement = "";
					String state = "";
					if(str.equals("유지")) {							
						idcrement = "0";							
						state = "유지";
					}												
					else if(str.equals("new")) {
						idcrement = "0";
						state = "new";
					}												// state가 "60하강"과 같이 숫자와 문자가 붙어있기 때문에 subString 못씀
					else {											// 따라서 한쪽은 한글을 지우고 한쪽은 숫자를 지워야 구분됨
						idcrement = str.replaceAll("[^0-9]", "");	// 숫자를 제외하고 나머지는 지우기
						state = str.replaceAll("[^가-힣]", "");		// 한글을 제외하고 나머지는 지우기
					}
					System.out.println("상태 : " + state);
					System.out.println("등폭 : " + idcrement);
//					System.out.println("동영상 키 : " + youtubeKeyData(title.get(j).text()));		// 유튜브에 2번 연결하면 막혀서 sysout은 주석으로 ㅇㅇ
					
					System.out.println("===================================================================");
					
					
			// MusicVO에 데이터 넣기
					MusicVO vo = new MusicVO();
					vo.setMno(k);
					vo.setTitle(title.get(j).text());
					vo.setSinger(singer.get(j).text());
					vo.setAlbum(album.get(j).text());
					vo.setPoster(poster.get(j).attr("src"));
					vo.setState(state);
					vo.setIdcrement(Integer.parseInt(idcrement));
					vo.setKey(youtubeKeyData(title.get(j).text()));
					
			// MusicDAO의 musicInsert 메소드 실행하여 데이터 주입
					dao.musicInsert(vo);	
					
					Thread.sleep(100);	// 오라클 서버 속도가 데이터 주입하는 속도를 따라가지 못해서 속도를 조금 늦춰줌
					
					k++;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		youtubeKeyData("다시 여기 바닷가");
	}
	
// 동영상 키 가져오는 메소드	
	public static String youtubeKeyData(String title) {
		String key = "";
		try {
			Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=" + title).get();
			Pattern p = Pattern.compile("/watch\\?v=[^가-힣]+");	// Pattern : 찾을 문자열 가져오기			// /watch?v=[^가-힣]+ : +의 의미는 
			Matcher m = p.matcher(doc.toString());
			while(m.find()) {
//				System.out.println(m.group());	=>  /watch?v=ESKfHHtiSjs","webPageType":"WEB_PAGE_TYPE_WATCH","rootVe":3832}},"watchEndpoint":{"videoId":"ESKfHHtiSjs"}},"badges":[{"metadataBadgeRenderer":{"style":"BADGE_STYLE_TYPE_SIMPLE","label":"
												//  ==================== 필요한 부분
				String str = m.group();
				str = str.substring(str.indexOf("=")+1, str.indexOf("\""));
				key = str;
				break; 	// 하나만 검색해서 갖고오기 위해서 break 걸기
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return key;
	}

}
