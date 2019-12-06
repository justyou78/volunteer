package volunteer.main.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.method.Kakao_Restapi;
import volunteer.data.vo.MemberVO;

@Controller
@RequestMapping("/main_join/")
public class MemberAction {
	@Inject
	MemberDAOImpl memberDao;

	@RequestMapping("main")
	public String main() {

		return "main_join/main";
	}

	@RequestMapping(value = "oauth", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) {
//		System.out.println(code);

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

		// System.out.println(node02);

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
		return "main_join/main";
	}
	@RequestMapping(value = "logout", produces = "application/json")
	public String Logout(HttpSession session) {
		// kakao restapi 객체 선언
		Kakao_Restapi kr = new Kakao_Restapi();
		// 노드에 로그아웃한 결과값음 담아줌 매개변수는 세션에 잇는 token을 가져와 문자열로 변환
		JsonNode node = kr.Logout(session.getAttribute("token").toString());
		// 결과 값 출력
		System.out.println("로그인 후 반환되는 아이디 : " + node.get("id"));
		return "redirect:/";
	}

}
