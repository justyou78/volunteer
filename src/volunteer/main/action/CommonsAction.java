package volunteer.main.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.dao.VolunteerDAOImpl;
import volunteer.data.method.Kakao_Restapi;
import volunteer.data.method.kakao_http_client;
import volunteer.data.vo.MemberVO;

@Controller
@RequestMapping("/commons/")
public class CommonsAction {
	
	@Autowired
	MemberDAOImpl memdao;
	@Autowired
	Kakao_Restapi kakao_restapi;
	@Autowired
	VolunteerDAOImpl VolunteerDao;
	
	
	//지도에 각 유저의 상세정보를 보여주는 Controller
	@RequestMapping("memberInfo")
	public String memberInfo(Model model, String id) {
	
		if(id != null) {
			String gender = null;
			MemberVO vo = memdao.selectAll(id);
			if(vo.getPicture() == null) {
				if(vo.getGender() == 1) {
					vo.setPicture("../img/bono.png");
				}else {
					vo.setPicture("../img/poro.png");
				}
			}
			if(vo.getGender() == 1) {
	               gender = "남자";
	            }else {
	               gender = "여자";
	            }
	         model.addAttribute("vo", vo);
	         model.addAttribute("gender", gender);
		}
		return "commons/memberInfo";
	}
	
	@RequestMapping("change_info")
	public String change_info(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		MemberVO vo = memdao.selectAll(id);
		model.addAttribute("memberVO", vo);
		return "commons/change_info";
	}
	
	@RequestMapping("change_info_Pro")
	public String change_info_Pro(HttpServletRequest request, Model model, MemberVO vo, HttpSession session,
			String isgender) throws ClientProtocolException, IOException, org.json.simple.parser.ParseException {
		
		System.out.println("진입");
		String id = (String) session.getAttribute("id");
		vo.setId(id);
		System.out.println(isgender);
		if (isgender.equals("남")) {
			vo.setGender(1);
		} else {
			vo.setGender(2);
		}
		System.out.println(vo.getAge());
		System.out.println(vo.getAddress());
		System.out.println(vo.getId());
		System.out.println(vo.getVol_name());
		System.out.println(vo.getRegist_number());
		System.out.println(vo.getMember_type());
		
		
		memdao.updateInfo(vo);

		// member.json에 좌표 추가
				kakao_http_client khc = new kakao_http_client();
				List<String> ids = new ArrayList<String>(); // 멤버테이블에 있는 모든 아이디 가져오기
				ids.add(vo.getId());
				HashMap<String, String> hs = null; // 제이슨에 넣을 정보들
				List<HashMap<String, String>> position = new ArrayList<HashMap<String,String>>(); // 제이슨에 넣을 정보들을 보관할 리스트
				for(String str : ids) {
					vo = memdao.selectAll(str); // 아이디 값으로 모든 정보 가져오기
					hs = khc.get(vo); // 주소로 좌표값 받기
					position.add(hs); // 리스트에 담기
				}
		khc.readToJsonModify(position,id);

		
		model.addAttribute("changeInfo", true);
		
		
	
		return "redirect:/main_join/main.vol";		
		

	}
	
	@RequestMapping("kakaoPay")
	public String kakaoPay(HttpSession httpSession,String money) {
		
		System.out.println(money +"돈");
		Kakao_Restapi kakao_restapi = new Kakao_Restapi();
		JsonNode node = kakao_restapi.kakaoPayReady(httpSession, money);
		System.out.println(node);

		// JsonNode properties = node02.path("properties");
		String url = (String) node.path("next_redirect_pc_url").asText();
		String tid = (String) node.path("tid").asText();
		
		httpSession.setAttribute("tid", tid);

		return "redirect:" + url;

	}
	
	@RequestMapping("sponsor")
	public String sponsor() {

		return "commons/sponsor";
	}
	
	@RequestMapping("kakaoPaySuccess")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) throws ParseException {
		System.out.println(pg_token+"이게 토큰");
		JsonNode infoNode=kakao_restapi.kakaoPayInfo(pg_token, session, (String)session.getAttribute("tid"));
		System.out.println(infoNode);
		VolunteerDao.insert_donation(infoNode,session);
		
		model.addAttribute("moneySuccess","success");
		
		
		return "main_join/main";

	}
	@RequestMapping("kakaoPayCancel")
	public String kakaoPayCancel(Model model) {
		model.addAttribute("moneySuccess","cancel");
		return "main_join/main";

	}
	@RequestMapping("kakaoPayFail")
	public String kakaoPayFail(Model model) {
		model.addAttribute("moneySuccess","fail");
		
		return "main_join/main";
		
	}
}
