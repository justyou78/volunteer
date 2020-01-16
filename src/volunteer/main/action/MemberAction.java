package volunteer.main.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import volunteer.data.dao.MemberDAO;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.method.Kakao_Restapi;
import volunteer.data.method.kakao_http_client;
import volunteer.data.vo.MemberVO;

//회원가입, 로그인에 관련된 컨트롤러입니다. 
@Controller
@RequestMapping("/main_join/")
public class MemberAction {

	@Inject
	MemberDAOImpl memberDao;

	// 메인 페이지를 보여주는 컨트롤러입니다.
	@RequestMapping("main")
	public String main(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		// 로그인을 하지 않은 상태에서 URI로 접근하였을 때 SESSION에 isLogin이 false값으로 들어오게 된다.
		// 이를 이용하여 Alert메세지를 띄워주게 된다.
		if (session.getAttribute("isLogin") != null && session.getAttribute("isLogin").equals("false")) {
			model.addAttribute("isLogin", "로그인 후 이용해주세요.");
			session.removeAttribute("isLogin");
		// 로그인은 하였지만 봉사자가 장애인 화면으로 URI를 요청하였을 때  isLogin 세션에 notType이 들어가게 된다.
		// 이를 이용하여 Alert메세지를 띄워주게 된다.
		} else if (session.getAttribute("isLogin") != null && session.getAttribute("isLogin").equals("notType")) {
			model.addAttribute("isLogin", "잘못된 접근방식입니다.");
			session.removeAttribute("isLogin");
		}

		return "main_join/main";
	}

	// 추가 입력사항 페이지로 이동
	@RequestMapping("join")
	public String join(HttpSession session) {

		return "main_join/join";
	}

	// 추가 입력사항을 DB에 저장하는 컨트롤러
	@RequestMapping("join_pro")
	public String join_pro(MemberVO vo, HttpSession session, Model model)
			throws ClientProtocolException, IOException, ParseException {

		String id = (String) session.getAttribute("id");
		vo.setId(id);
		memberDao.updateInfo(vo);

		// member.json에 새로 회원가입한 사용자의 좌표 추가
		kakao_http_client khc = new kakao_http_client();
		List<String> ids = new ArrayList<String>(); // 멤버테이블에 있는 모든 아이디 가져오기
		ids.add(vo.getId());
		HashMap<String, String> hs = null; // 제이슨에 넣을 정보들
		List<HashMap<String, String>> position = new ArrayList<HashMap<String, String>>(); // 제이슨에 넣을 정보들을 보관할 리스트
		for (String str : ids) {
			vo = memberDao.selectAll(str); // 아이디 값으로 모든 정보 가져오기
			hs = khc.get(vo); // 주소로 좌표값 받기
			position.add(hs); // 리스트에 담기
		}
		khc.readToJson(position); // 리스트 member.json 파일 생성

		// 봉사자 페이지로 이동
		if (vo.getMember_type().equals("1")) {
			return "redirect:/volunteer/main.vol";
		}
		// 장애인 화면으로 이동.
		else if (vo.getMember_type().equals("2")) {
			System.out.println("disabled");
			return "redirect:/disabled/disabledMain.vol?id" + id;
		} else {
			return "redirect:/main_join/main.vol";
		}
	}

	@RequestMapping(value = "oauth", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) {
		// 카카오 rest api 객체 선언
		Kakao_Restapi kakao_restapi = new Kakao_Restapi();
		// 결과값을 node에 담아줌
		JsonNode node = kakao_restapi.getAccessToken(code);
		// 노드 안에 있는 access_token값을 꺼내 문자열로 변환
		String token = node.get("access_token").toString();
		// 세션에 담아준다.
		session.setAttribute("token", token);
		// 유저정보 가져온다.
		JsonNode node02 = kakao_restapi.getKakaoUserInfo(token);
		// 각 정보를 꺼내온다.
		JsonNode properties = node02.path("properties");
		String id = node02.path("id").asText();
		session.setAttribute("id", id);
		String name = properties.path("nickname").asText();
		String imgUrl = properties.path("profile_image").asText();
		// 이미지를 내부 서버에 저장한다.
		try {
			if (imgUrl != null && imgUrl.length() > 0) {
				URL url = new URL(imgUrl);
				// 이미지 파일명 추출
				String fileName = imgUrl.substring(imgUrl.lastIndexOf('/') + 1, imgUrl.length());
				// 이미지 확장자 추출
				String ext = imgUrl.substring(imgUrl.lastIndexOf('.') + 1, imgUrl.length());

				// url이미지 읽어온다.
				BufferedImage img = ImageIO.read(url);
				// 저장경로에 사진 집어넣는다.
				ImageIO.write(img, ext, new File(
						"C:\\Spring_An\\work\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp7\\wtpwebapps\\volunteer\\img\\userimg\\"
								+ fileName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 기본 데이터 삽입
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setName(name);
		vo.setPicture(imgUrl);
		// 아이디가 존재하지 않을 경우 insert를 통해 데이터 삽입
		if (memberDao.selectID(id) == null) {
			memberDao.insert(vo);
			// ********************************************************************
			// return "redirect:/main_join/join.vol";
			// 아이디가 존재할경우. update를 통해 데이터 삽입
		} else {
			memberDao.update(vo);
		}
		vo = memberDao.selectAll(id);
		// 추가사항을 기입한 사용자라면 각 사용자에 맞는 페이지로 이동합니다.
		// 추가사항을 기입하지 않은 사용자라면 추가사항 페이지로 이동합니다.
		if (vo.getEmail() == null || vo.getEmail().equals("")) {
			return "redirect:/main_join/join.vol";
		} else {
			if (vo.getMember_type().equals("1")) {
				return "redirect:/volunteer/main.vol";

			} else {
				return "redirect:/disabled/disabledMain.vol";
			}
		}
	}

	// 로그인 페이지로 이동합니다.
	@RequestMapping("login")
	public String login() {
		return "main_join/login";
	}

	// 로그아웃 버튼 클릭에 따른 세션 삭제.
	@RequestMapping(value = "logout", produces = "application/json")
	public String logout(HttpSession session) {

		// kakao restapi 객체 선언
		Kakao_Restapi kr = new Kakao_Restapi();
		// 노드에 로그아웃한 결과값음 담아줌 매개변수는 세션에 잇는 token을 가져와 문자열로 변환
		JsonNode node = kr.Logout(session.getAttribute("token").toString());

		// 세션 삭제.
		session.invalidate();

		return "redirect:/main_join/main.vol";
	}
}
