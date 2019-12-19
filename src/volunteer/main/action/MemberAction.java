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

@Controller
@RequestMapping("/main_join/")
public class MemberAction {
	
	@Inject
	MemberDAOImpl memberDao;

	
	//시작 화면
	@RequestMapping("main")
	public String main(HttpServletRequest request,Model model) {
		
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("isLogin"));
		//로그인을 하지 않은 상태에서 각 '봉사자' '장애인'버튼을 클릭 했을시 alert발생을 위하여 attribute를 설정한다.
		if(session.getAttribute("isLogin") != null &&session.getAttribute("isLogin").equals("false")) {
			System.out.println("진입");
			
				model.addAttribute("isLogin","로그인 후 이용해주세요.");
				session.removeAttribute("isLogin");
		}
		
		return "main_join/main";
	}
	
	//추가 입력사항 이동 
	@RequestMapping("join")
	public String join(HttpSession session) {
	
		return "main_join/join";
	}
	
	
	//추가 입력사항 DB에 저장하는 Controller
	@RequestMapping("join_pro")
	public String join_pro(MemberVO vo, HttpSession session, Model model) throws ClientProtocolException, IOException, ParseException {
		
		System.out.println(vo.getMember_type()+"멤버타입");
		String id =(String)session.getAttribute("id");
		vo.setId(id);
		memberDao.updateInfo(vo);
		
		// member.json에 좌표 추가
		kakao_http_client khc = new kakao_http_client();
		List<String> ids = new ArrayList<String>(); // 멤버테이블에 있는 모든 아이디 가져오기
		ids.add(vo.getId());
		HashMap<String, String> hs = null; // 제이슨에 넣을 정보들
		List<HashMap<String, String>> position = new ArrayList<HashMap<String,String>>(); // 제이슨에 넣을 정보들을 보관할 리스트
		for(String str : ids) {
			vo = memberDao.selectAll(str); // 아이디 값으로 모든 정보 가져오기
			hs = khc.get(vo); // 주소로 좌표값 받기
			position.add(hs); // 리스트에 담기
		}
		khc.readToJson(position); // 리스트 member.json 파일 생성
		
		if(vo.getMember_type().equals("1")) {
			return "redirect:/volunteer/main.vol";
		}
		else if (vo.getMember_type().equals("2")) {
			//장애인 화면으로 이동.
			System.out.println("disabled");
			return "redirect:/disabled/disabledMain.vol?id"+id;
		}
		else {
			return "redirect:/main_join/main.vol";
		}
		
	}

	@RequestMapping(value = "oauth", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) {

		
		
		
		
		// 카카오 rest api 객체 선언
		Kakao_Restapi kakao_restapi = new Kakao_Restapi();
		// 결과값을 node에 담아줌
		JsonNode node = kakao_restapi.getAccessToken(code);
		// 결과값 출력
		System.out.println(node);
		// 노드 안에 있는 access_token값을 꺼내 문자열로 변환
		String token = node.get("access_token").toString();
		// 세션에 담아준다.
		session.setAttribute("token", token);
		// 유저정보 가져온다.
		JsonNode node02 = kakao_restapi.getKakaoUserInfo(token);

		System.out.println(node02);

		// 각 정보를 꺼내온다.
		JsonNode properties = node02.path("properties");
		String id = node02.path("id").asText();
		session.setAttribute("id", id);

		String name = properties.path("nickname").asText();
		String imgUrl = properties.path("profile_image").asText();
		System.out.println(id + "아이디");
		System.out.println(name + "이름");
		System.out.println(imgUrl);

		// 이미지를 내부 서버에 저장한다.
		try {
			if(imgUrl != null && imgUrl.length() > 0) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		// 기본 데이터 삽입
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setName(name);
		vo.setPicture(imgUrl);
		//아이디가 존재할 경우
		if (memberDao.selectID(id) == null) {
			memberDao.insert(vo);
			System.out.println("insert");
		//아이디가 존재하지 않을 경우.
		} else {
			memberDao.update(vo);
			System.out.println("update");
		}
		
		
		return "redirect:/main_join/main.vol";
	}
	
	@RequestMapping(value = "oauth02", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
	public String kakaoLogin02(@RequestParam("code") String code, Model model, HttpSession session, String getId) {

		
		// 카카오 rest api 객체 선언
		Kakao_Restapi kakao_restapi = new Kakao_Restapi();
		// 결과값을 node에 담아줌
		JsonNode node = kakao_restapi.getAccessToken(code);
		// 결과값 출력
		System.out.println(node);
		// 노드 안에 있는 access_token값을 꺼내 문자열로 변환
		String token = node.get("access_token").toString();
		// 세션에 담아준다.
		session.setAttribute("token", token);
		// 유저정보 가져온다.
		JsonNode node02 = kakao_restapi.getKakaoUserInfo(token);

		System.out.println(node02);

		// 각 정보를 꺼내온다.
		JsonNode properties = node02.path("properties");
		String id = node02.path("id").asText();
		session.setAttribute("id", id);

		String name = properties.path("nickname").asText();
		String imgUrl = properties.path("profile_image").asText();
		System.out.println(id + "아이디");
		System.out.println(name + "이름");
		System.out.println(imgUrl);

		// 이미지를 내부 서버에 저장한다.
		try {
			if(imgUrl != null && imgUrl.length() > 0) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		// 기본 데이터 삽입
		MemberVO vo = new MemberVO();
		vo.setId(id);
		vo.setName(name);
		vo.setPicture(imgUrl);
		//아이디가 존재할 경우
		if (memberDao.selectID(id) == null) {
			memberDao.insert(vo);
			System.out.println("insert");
		//아이디가 존재하지 않을 경우.
		} else {
			memberDao.update(vo);
			System.out.println("update");
		}
		
		session.removeAttribute("invalid");
		
		
		return "redirect:/volunteer/connect.vol?id="+getId;
	}
	
	  
	//카카오 로그인 logout
	@RequestMapping(value = "logout", produces = "application/json")
	public String logout(HttpSession session) {
		// kakao restapi 객체 선언
		Kakao_Restapi kr = new Kakao_Restapi();
		// 노드에 로그아웃한 결과값음 담아줌 매개변수는 세션에 잇는 token을 가져와 문자열로 변환
		JsonNode node = kr.Logout(session.getAttribute("token").toString());
		// 결과 값 출력
		System.out.println("로그인 후 반환되는 아이디 : " + node.get("id"));
		return "redirect:/";
	}
	
	

}
