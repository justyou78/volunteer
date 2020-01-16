package volunteer.data.method;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import antlr.collections.Stack;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import volunteer.data.dao.ConnectDAOImpl;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.vo.MemberVO;

@Repository
public class Coolsms_Restapi {
	@Autowired
	MemberDAOImpl memberDao;
	@Autowired
	ConnectDAOImpl connectDao;
	// '구'를 기준으로 봉사자에게 메세지를 전송합니다.
	public String sendSMS(HttpSession session, String vol_time) {
		String id = (String) session.getAttribute("id");

		MemberVO vo = memberDao.selectAll(id);
		String address = vo.getAddress();
		int count = 0;
		String subAddress = "";
		
		// '구' 단위까지 내 주소 구해서 subAddress에 기입.
		for (int i = 0; i < address.length(); i++) {
			if (address.charAt(i) == ' ') {
				count++;
			}
			if (count == 2) {
				subAddress = address.substring(0, i);
				break;
			}
		}
		
		
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("address", subAddress);
		hm.put("id", id);
		//내아이디아닌 다른사람들의 주소값 가져오기
		List<MemberVO> list = memberDao.selectVolFromAddress(hm);

		
		String api_key = "NCSRNAH39QXAD7TJ";
		String api_secret = "7LIOUPAO3LSDW1ZSNHULWNFSCHOOQRBJ";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("from", "01037656597");
		params.put("type", "SMS");
		params.put("text", "(" + vol_time
				+ "시간)\n http://192.168.0.48:8081/volunteer/volunteer/connect.vol?disabled_id=" + id);
		params.put("app_version", "test app 1.2"); // application name and version

		System.out.println("봉사요청! (" + vol_time
				+ "시간)\n http://192.168.0.48:8081/volunteer/volunteer/connect.vol?disabled_id=" + id);

		if (list.size() == 0) {
			return "fail";
		} else {
			for (int i = 0; i < list.size(); i++) {
				params.put("to", String.valueOf(list.get(i).getCallnumber()));
				//connect 테이블에 정보 담기.
				connectDao.insert(id, list.get(i).getId());
				//메세지 전송.
//				try {
//					JSONObject obj = (JSONObject) coolsms.send(params);
//					System.out.println(obj.toString());
//				} catch (CoolsmsException e) {
//					System.out.println(e.getMessage());
//					System.out.println(e.getCode());
//				}

			}
			return "success";
		}

	}
	//장애인이 확인버튼을 클릭 후, 연결된 봉사자 한명에게 문자보내기.
	public String sendSMSOne(String id, String myId) throws ClientProtocolException, IOException {

		String api_key = "NCSRNAH39QXAD7TJ";
		String api_secret = "7LIOUPAO3LSDW1ZSNHULWNFSCHOOQRBJ";
		Message coolsms = new Message(api_key, api_secret);

		MemberVO vo = memberDao.selectAll(id);
		
		MemberVO myvo = memberDao.selectAll(myId);
		kakao_http_client kakaoClient = new kakao_http_client();
		HashMap<String, String>hm = kakaoClient.get(myvo);
		String x  = hm.get("x");
		String y = hm.get("y");
		
		
		//카카오 지도를 통해서 장애인 위치 공유하기.
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "https://map.kakao.com/link/to/"+myvo.getAddress()+","+y+","+x;
		url =url.replaceAll(" ", "");
		url = url.replaceAll("\"", "");
		
		params.put("from", "01037656597");
		params.put("type", "SMS");
		//주소 넣어야해.
		params.put("text", url+"\n연결완료");
		params.put("app_version", "test app 1.2"); // application name and version
		params.put("to", String.valueOf(vo.getCallnumber()));
		//메시지 전송.
//		try {
//			JSONObject obj = (JSONObject) coolsms.send(params);
//			System.out.println(obj.toString());
//		} catch (CoolsmsException e) {
//			System.out.println(e.getMessage());
//			System.out.println(e.getCode());
//		}
		

		return "success";
	}

}
