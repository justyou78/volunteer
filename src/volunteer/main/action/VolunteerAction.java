package volunteer.main.action;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.java.Log;
import volunteer.data.dao.MemberDAO;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.dao.VolunteerDAOImpl;
import volunteer.data.method.Kakao_Restapi;
import volunteer.data.vo.ListVO;
import volunteer.data.vo.MemberVO;

@Controller
@RequestMapping("/volunteer/")
public class VolunteerAction {
	@Autowired
	VolunteerDAOImpl VolunteerDao;
	@Autowired
	MemberDAOImpl memberDao;

	@Autowired
	Date d;
	@Autowired
	SimpleDateFormat sdf;
	@Autowired
	Kakao_Restapi kakao_restapi;
	
	@RequestMapping("connect")
	public String connect() {
		return "volunteer/connect";
	}

	@RequestMapping("main")
	public String main(HttpSession session, Model model, String sysdate) {
		// 그래프 그리기.
		if(session.getAttribute("auth") !=null ) {
			model.addAttribute("auth","장애인 회원만 접근 할 수 있습니다.");
			session.removeAttribute("auth");
		}
		
		
		String id = (String) session.getAttribute("id");
		String imgName = null;
		if (sysdate == null) {

			imgName = VolunteerDao.getGraph(id, (String) sdf.format(d));
		} else {
			System.out.println(sysdate.length() - 7);
			imgName = VolunteerDao.getGraph(id, sysdate.substring(0, 4));
		}
		model.addAttribute("imgName", imgName);

		// 테이블 나타내기
		if (sysdate == null) {

			sdf.applyLocalizedPattern("yyyy-MM");
			sysdate = (String) sdf.format(d);
		} else {
			sysdate = sysdate.substring(0, 7);
		}
		List<ListVO> list = VolunteerDao.getVol_list(id, sysdate);
		HashMap<String, String> hm = new HashMap<String, String>();
		for (ListVO vo : list) {
			if (!hm.containsKey(vo.getVol_id())) {
				hm.put(vo.getVol_id(), memberDao.getName(vo.getVol_id()));
			}
			if (!hm.containsKey(vo.getDisabled_id())) {
				hm.put(vo.getDisabled_id(), memberDao.getName(vo.getDisabled_id()));
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("hm", hm);

		return "volunteer/main";

	}

	@RequestMapping("change_info")
	public String change_info(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		MemberVO vo = memberDao.selectAll(id);
		model.addAttribute("memberVO", vo);
		return "volunteer/change_info";
	}

	@RequestMapping("change_info_Pro")
	public String change_info_Pro(HttpServletRequest request, Model model, MemberVO vo, HttpSession session,
			String isgender) throws UnsupportedEncodingException {
		
		request.setCharacterEncoding("UTF-8");
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

		memberDao.updateInfo(vo);

		model.addAttribute("changeInfo", true);

		return "redirect:/volunteer/main.vol";

	}

	@RequestMapping("sponsor")
	public String sponsor() {

		return "volunteer/sponsor";
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

	@RequestMapping("kakaoPaySuccess")
	public String kakaoPaySuccess(@RequestParam("pg_token") String pg_token, Model model, HttpSession session) throws ParseException {
		System.out.println(pg_token);
		JsonNode infoNode=kakao_restapi.kakaoPayInfo(pg_token, session, (String)session.getAttribute("tid"));
		System.out.println(infoNode);
		VolunteerDao.insert_donation(infoNode,session);
		
		
		
		return "redirect:/volunteer/sponsor.vol";

	}
	@RequestMapping("kakaoPayCancel")
	public String kakaoPayCancel(@RequestParam("pg_token") String pg_token, Model model) {
		System.out.println(pg_token);

		return "redirect:/volunteer/sponsor.vol";

	}
	@RequestMapping("kakaoPayFail")
	public String kakaoPayFail(@RequestParam("pg_token") String pg_token, Model model) {
		System.out.println(pg_token);
		
		return "redirect:/volunteer/sponsor.vol";
		
	}
}
