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
					System.out.println("���� : " + k);
					System.out.println("�뷡�� : " + title.get(j).text());
					System.out.println("���� : " + singer.get(j).text());
					System.out.println("�ٹ� : " + album.get(j).text());
					System.out.println("������ : " + poster.get(j).attr("src"));
//					System.out.println("���� : " + temp.get(j).text());
					String str = temp.get(j).text();
					String idcrement = "";
					String state = "";
					if(str.equals("����")) {							
						idcrement = "0";							
						state = "����";
					}												
					else if(str.equals("new")) {
						idcrement = "0";
						state = "new";
					}												// state�� "60�ϰ�"�� ���� ���ڿ� ���ڰ� �پ��ֱ� ������ subString ����
					else {											// ���� ������ �ѱ��� ����� ������ ���ڸ� ������ ���е�
						idcrement = str.replaceAll("[^0-9]", "");	// ���ڸ� �����ϰ� �������� �����
						state = str.replaceAll("[^��-�R]", "");		// �ѱ��� �����ϰ� �������� �����
					}
					System.out.println("���� : " + state);
					System.out.println("���� : " + idcrement);
//					System.out.println("������ Ű : " + youtubeKeyData(title.get(j).text()));		// ��Ʃ�꿡 2�� �����ϸ� ������ sysout�� �ּ����� ����
					
					System.out.println("===================================================================");
					
					
			// MusicVO�� ������ �ֱ�
					MusicVO vo = new MusicVO();
					vo.setMno(k);
					vo.setTitle(title.get(j).text());
					vo.setSinger(singer.get(j).text());
					vo.setAlbum(album.get(j).text());
					vo.setPoster(poster.get(j).attr("src"));
					vo.setState(state);
					vo.setIdcrement(Integer.parseInt(idcrement));
					vo.setKey(youtubeKeyData(title.get(j).text()));
					
			// MusicDAO�� musicInsert �޼ҵ� �����Ͽ� ������ ����
					dao.musicInsert(vo);	
					
					Thread.sleep(100);	// ����Ŭ ���� �ӵ��� ������ �����ϴ� �ӵ��� ������ ���ؼ� �ӵ��� ���� ������
					
					k++;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
//		youtubeKeyData("�ٽ� ���� �ٴ尡");
	}
	
// ������ Ű �������� �޼ҵ�	
	public static String youtubeKeyData(String title) {
		String key = "";
		try {
			Document doc = Jsoup.connect("https://www.youtube.com/results?search_query=" + title).get();
			Pattern p = Pattern.compile("/watch\\?v=[^��-�R]+");	// Pattern : ã�� ���ڿ� ��������			// /watch?v=[^��-�R]+ : +�� �ǹ̴� 
			Matcher m = p.matcher(doc.toString());
			while(m.find()) {
//				System.out.println(m.group());	=>  /watch?v=ESKfHHtiSjs","webPageType":"WEB_PAGE_TYPE_WATCH","rootVe":3832}},"watchEndpoint":{"videoId":"ESKfHHtiSjs"}},"badges":[{"metadataBadgeRenderer":{"style":"BADGE_STYLE_TYPE_SIMPLE","label":"
												//  ==================== �ʿ��� �κ�
				String str = m.group();
				str = str.substring(str.indexOf("=")+1, str.indexOf("\""));
				key = str;
				break; 	// �ϳ��� �˻��ؼ� ������� ���ؼ� break �ɱ�
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return key;
	}

}
