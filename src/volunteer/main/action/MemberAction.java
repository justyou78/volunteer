package volunteer.main.action;

import javax.inject.Inject;


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
	
	@RequestMapping("join")
	public String join() {
		return "main_join/join";
	}
	
	@RequestMapping("insert.vol")
	public void insert(MemberVO vo) {
		System.out.println("insert");
		memberDao.insertMember(vo);
	}
	
	
    @RequestMapping(value = "oauth", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
    public String kakaoLogin(@RequestParam("code") String code, Model model, HttpSession session) {
    	System.out.println("test");
        System.out.println(code);
        
        //카카오 홈페이지에서 받은 결과 코드
        System.out.println("로그인 후 결과값");
        
        //카카오 rest api 객체 선언
        Kakao_Restapi kakao_restapi = new Kakao_Restapi();
        //결과값을 node에 담아줌
        JsonNode node = kakao_restapi.getAccessToken(code);
        //결과값 출력
        System.out.println(node);
        //노드 안에 있는 access_token값을 꺼내 문자열로 변환
        String token = node.get("access_token").toString();
        //세션에 담아준다.
        
        session.setAttribute("token", token);
        
        JsonNode node02=kakao_restapi.getKakaoUserInfo(token);
        
        System.out.println(node02);
        
        JsonNode properties = node02.path("properties");
        JsonNode kakao_account = node02.path("kakao_account");
 
        String name = properties.path("nickname").asText();
        String img = kakao_account.path("profile_image").asText();
 


        
       
        
        return "main_join/main";
    }
    
    @RequestMapping(value = "logout", produces = "application/json")
    public String Logout(HttpSession session) {
        //kakao restapi 객체 선언
        Kakao_Restapi kr = new Kakao_Restapi();
        //노드에 로그아웃한 결과값음 담아줌 매개변수는 세션에 잇는 token을 가져와 문자열로 변환
        JsonNode node = kr.Logout(session.getAttribute("token").toString());
        //결과 값 출력
        System.out.println("로그인 후 반환되는 아이디 : " + node.get("id"));
        return "redirect:/";
    }    


    

    



}
