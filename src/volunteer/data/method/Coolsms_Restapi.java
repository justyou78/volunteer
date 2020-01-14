package volunteer.data.method;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

	public String sendSMS(HttpSession session, String vol_time) {
		String id = (String) session.getAttribute("id");

		MemberVO vo = memberDao.selectAll(id);
		String address = vo.getAddress();
		int count = 0;
		String subAddress = "";
		
		//동단위까지 주소 구해서 subAddress에 기입.
		for (int i = 0; i < address.length(); i++) {
			if (address.charAt(i) == ' ') {
				System.out.println("증가");
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
		System.out.println(subAddress);
		//내아이디아닌 다른사람들의 주소값 가져오기
		List<MemberVO> list = memberDao.selectVolFromAddress(hm);

		System.out.println(list.size() + "사이즈");

		String api_key = "NCSDEQ1ZPQR9XOOD";
		String api_secret = "GU5YJBIWKJ2DNMGPVOHAPYAISHXPC1OY";
		Message coolsms = new Message(api_key, api_secret);

		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("from", "01056459294");
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
				System.out.println(list.get(i).getCallnumber());

				params.put("to", String.valueOf(list.get(i).getCallnumber()));
				connectDao.insert(id, list.get(i).getId());
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

	public String sendSMSOne(String id) throws ClientProtocolException, IOException {

		String api_key = "NCSDEQ1ZPQR9XOOD";
		String api_secret = "GU5YJBIWKJ2DNMGPVOHAPYAISHXPC1OY";
		Message coolsms = new Message(api_key, api_secret);

		MemberVO vo = memberDao.selectAll(id);
		kakao_http_client kakaoClient = new kakao_http_client();
		HashMap<String, String>hm = kakaoClient.get(vo);
		String x  = hm.get("x");
		String y = hm.get("y");
		
		
		// 4 params(to, from, type, text) are mandatory. must be filled
		HashMap<String, String> params = new HashMap<String, String>();
		String url = "https://map.kakao.com/link/to/"+vo.getAddress()+","+y+","+x;
		url =url.replaceAll(" ", "");
		url = url.replaceAll("\"", "");
		System.out.println(url);
		
		params.put("from", "01056459294");
		params.put("type", "SMS");
		//주소 넣어야해.
		params.put("text", url+"\n연결완료");
		params.put("app_version", "test app 1.2"); // application name and version
		params.put("to", String.valueOf(vo.getCallnumber()));

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
