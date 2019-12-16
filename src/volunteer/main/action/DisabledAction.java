package volunteer.main.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteer.data.dao.BoardDAOImpl;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.method.kakao_http_client;
import volunteer.data.vo.MemberVO;

@Controller
@RequestMapping("/disabled/")
public class DisabledAction {
	
	@Autowired
	MemberDAOImpl memdao;
	
	@RequestMapping("disabledMain")
	public String disabledMain() {
		
		return "disabled/disabledMain";
	}
	
	@RequestMapping("test")
	public String test() throws ClientProtocolException, IOException, ParseException {
		kakao_http_client khc = new kakao_http_client();
		List<String> id = new ArrayList<String>(); // 멤버테이블에 있는 모든 아이디 가져오기
		id.add("test");
		MemberVO vo = null; 
		HashMap<String, String> hs = null; // 제이슨에 넣을 정보들
		List<HashMap<String, String>> position = new ArrayList<HashMap<String,String>>(); // 제이슨에 넣을 정보들을 보관할 리스트
		for(String str : id) {
			vo = memdao.selectAll(str); // 아이디 값으로 모든 정보 가져오기
			hs = khc.get(vo); // 주소로 좌표값 받기
			position.add(hs); // 리스트에 담기
		}
		khc.readToJson(position); // 리스트 json 파일 생성
		
		return "disabled/test";
	}
	
	@RequestMapping("memberInfo")
	public String memberInfo(Model model, String id) {
		System.out.println(id);
		if(id != null) {
			MemberVO vo = memdao.selectAll(id);
			model.addAttribute("vo", vo);
		}
		return "disabled/memberInfo";
	}
	
}
