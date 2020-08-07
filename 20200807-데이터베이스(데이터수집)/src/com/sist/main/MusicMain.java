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
					System.out.println("¼øÀ§ : " + k);
					System.out.println("³ë·¡¸í : " + title.get(j).text());
					System.out.println("°¡¼ö : " + singer.get(j).text());
					System.out.println("¾Ù¹ü : " + album.get(j).text());
					System.out.println("Æ÷½ºÅÍ : " + poster.get(j).attr("src"));
//					System.out.println("»óÅÂ : " + temp.get(j).text());
					String str = temp.get(j).text();
					String idcrement = "";
					String state = "";
					if(str.equals("À¯Áö")) {							
						idcrement = "0";							
						state = "À¯Áö";
					}												
					else if(str.equals("new")) {
						idcrement = "0";
						state = "new";
					}												// state°¡ "60ÇÏ°­"°ú °°ÀÌ ¼ýÀÚ¿Í ¹®ÀÚ°¡ ºÙ¾îÀÖ±â ¶§¹®¿¡ subString ¸ø¾¸
					else {											// µû¶ó¼­ ÇÑÂÊÀº ÇÑ±ÛÀ» Áö¿ì°í ÇÑÂÊÀº ¼ýÀÚ¸¦ Áö¿ö¾ß ±¸ºÐµÊ
						idcrement = str.replaceAll("[^0-9]", "");	// ¼ýÀÚ¸¦ Á¦¿ÜÇÏ°í ³ª¸ÓÁö´Â Áö¿ì±â
						state = str.replaceAll("[^°¡-ÆR]", "");		// ÇÑ±ÛÀ» Á¦¿ÜÇÏ°í ³ª¸ÓÁö´Â Áö¿ì±â
					}
					System.out.println("»óÅÂ : " + state);
					System.out.println("µîÆø : " + idcrement);
//					System.out.println("µ¿¿µ»ó Å° : " + youtubeKeyData(title.get(j).text()));		// À¯Æ©ºê¿¡ 2¹ø ¿¬°áÇÏ¸é ¸·Çô¼­ sysoutÀº ÁÖ¼®À¸·Î ¤·¤·
					
					System.out.println("===================================================================");
					
					
			// MusicVO¿¡ µ¥ÀÌÅÍ ³Ö±â
					MusicVO vo = new MusicVO();
					vo.setMno(k);
					vo.setTitle(title.get(j).text());
					vo.setSinger(singer.get(j).text());
					vo.setAlbum(album.get(j).text());
					vo.setPoster(poster.get(j).attr("src"));
					vo.setState(state);
					vo.setIdcrement(Integer.parseInt(idcrement));
					vo.setKey(youtubeKeyData(title.get(j).text()));
					
			// MusicDAOÀÇ musicInsert ¸Þ¼Òµå ½ÇÇàÇÏ¿© µ¥ÀÌÅÍ ÁÖÀÔ
					dao.musicInsert(vo);	
					
					Thread.sleep(100);	// ¿À¶óÅ¬ ¼­¹ö ¼Óµµ°¡ µ¥ÀÌÅÍ ÁÖÀÔÇÏ´Â ¼Óµµ¸¦ µû¶ó°¡Áö ¸øÇØ¼­ ¼Óµµ¸¦ Á¶±Ý ´ÊÃçÁÜ
					
					k++;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		youtubeKeyData("´Ù½Ã ¿©±â ¹Ù´å°¡");
	}
	
// µ¿¿µ»ó Å° °¡Á®¿À´Â ¸Þ¼Òµå	
	public static String youtubeKeyData(String title) {
		String key = "";
		try {
			Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=" + title).get();
			Pattern p = Pattern.compile("/watch\\?v=[^°¡-ÆR]+");	// Pattern : Ã£À» ¹®ÀÚ¿­ °¡Á®¿À±â			// /watch?v=[^°¡-ÆR]+ : +ÀÇ ÀÇ¹Ì´Â 
			Matcher m = p.matcher(doc.toString());
			while(m.find()) {
//				System.out.println(m.group());	=>  /watch?v=ESKfHHtiSjs","webPageType":"WEB_PAGE_TYPE_WATCH","rootVe":3832}},"watchEndpoint":{"videoId":"ESKfHHtiSjs"}},"badges":[{"metadataBadgeRenderer":{"style":"BADGE_STYLE_TYPE_SIMPLE","label":"
												//  ==================== ÇÊ¿äÇÑ ºÎºÐ
				String str = m.group();
				str = str.substring(str.indexOf("=")+1, str.indexOf("\""));
				key = str;
				break; 	// ÇÏ³ª¸¸ °Ë»öÇØ¼­ °®°í¿À±â À§ÇØ¼­ break °É±â
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return key;
	}

}
