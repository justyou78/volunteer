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

	// 지도에 각 유저의 상세정보를 보여주는 Controller
	@RequestMapping("memberInfo")
	public String memberInfo(Model model, String id) {

		if (id != null) {
			String gender = null;
			MemberVO vo = memdao.selectAll(id);
			if (vo.getPicture() == null) {
				if (vo.getGender() == 1) {
					vo.setPicture("../img/bono.png");
				} else {
					vo.setPicture("../img/poro.png");
				}
			}
			if (vo.getGender() == 1) {
				gender = "남자";
			} else {
				gender = "여자";
			}
			model.addAttribute("vo", vo);
			model.addAttribute("gender", gender);
		}
		return "commons/memberInfo";
	}

	// 개인정보 수정화면으로 이동한다.
	@RequestMapping("change_info")
	public String change_info(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		MemberVO vo = memdao.selectAll(id);
		model.addAttribute("memberVO", vo);
		return "commons/change_info";
	}

	// 개인정보 수정확면에서 수정하기 버튼을 클릭하였을 경우 수정이 이루어진다.
	@RequestMapping("change_info_Pro")
	public String change_info_Pro(HttpServletRequest request, Model model, MemberVO vo, HttpSession session,
			String isgender) throws ClientProtocolException, IOException, org.json.simple.parser.ParseException {

		String id = (String) session.getAttribute("id");
		vo.setId(id);
		if (isgender.equals("남")) {
			vo.setGender(1);
		} else {
			vo.setGender(2);
		}
		// 회원정보수정
		memdao.updateInfo(vo);

		// 주소가 변경되었을경우 json데이터도 수정한다.
		// member.json에 좌표 추가
		kakao_http_client khc = new kakao_http_client();
		List<String> ids = new ArrayList<String>(); // 멤버테이블에 있는 모든 아이디 가져오기
		ids.add(vo.getId());
		HashMap<String, String> hs = null; // 제이슨에 넣을 정보들
		List<HashMap<String, String>> position = new ArrayList<HashMap<String, String>>(); // 제이슨에 넣을 정보들을 보관할 리스트
		for (String str : ids) {
			vo = memdao.selectAll(str); // 아이디 값으로 모든 정보 가져오기
			hs = khc.get(vo); // 주소로 좌표값 받기
			position.add(hs); // 리스트에 담기
		}
		khc.readToJsonModify(position, id);
		model.addAttribute("changeInfo", true);
		return "redirect:/main_join/main.vol";
	}
	
	@RequestMapping("sponsor")
	public String sponsor() {
		return "commons/sponsor";
	}


	// 카카오 페이로 후원하기 를 사용한 경우. 이루어지는 API
	@RequestMapping("kakaoPay")
	public String kakaoPay(HttpSession httpSession, String money) {
		Kakao_Restapi kakao_restapi = new Kakao_Restapi();
		JsonNode node = kakao_restapi.kakaoPayReady(httpSession, money);
		
		String url = (String) node.path("next_redirect_pc_url").asText();
		String tid = (String) node.path("tid").asText();

		httpSession.setAttribute("tid", tid);

		return "redirect:" + url;

	}

	
	// 카카오 페이가 성공하였을 경우.
	@RequestMapping("kakaoPaySuccess")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session)
			throws ParseException {
		JsonNode infoNode = kakao_restapi.kakaoPayInfo(pg_token, session, (String) session.getAttribute("tid"));
		VolunteerDao.insert_donation(infoNode, session);
		model.addAttribute("moneySuccess", "success");
		return "main_join/main";
	}
	// 카카오 페이가 취소 되었을 경우.
	@RequestMapping("kakaoPayCancel")
	public String kakaoPayCancel(Model model) {
		model.addAttribute("moneySuccess", "cancel");
		return "main_join/main";

	}
	// 카카오페이가 실패하였을 경우.
	@RequestMapping("kakaoPayFail")
	public String kakaoPayFail(Model model) {
		model.addAttribute("moneySuccess", "fail");
		return "main_join/main";

	}
}
