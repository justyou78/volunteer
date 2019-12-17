package volunteer.main.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.simple.parser.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import volunteer.data.dao.ConnectDAOImpl;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.method.kakao_http_client;
import volunteer.data.method.Coolsms_Restapi;
import volunteer.data.vo.MemberVO;

@Controller
@RequestMapping("/disabled/")
public class DisabledAction {
	@Autowired
	Coolsms_Restapi coolsms_api;
	
	@Autowired
	MemberDAOImpl memberDao;
	@Autowired
	ConnectDAOImpl connectDao;
	
	
	@Autowired
	MemberDAOImpl memdao;
	
	@RequestMapping("disabledMain")
	public String disabledMain(HttpSession session,Model model) {
		
		if(session.getAttribute("auth") !=null ) {
			System.out.println("진입");
			model.addAttribute("auth","봉사자만 접근 할 수 있습니다.");
			session.removeAttribute("auth");
		}
		
		return "disabled/disabledMain";
	}
	
	@RequestMapping("test")
	public String test(){
		return "disabled/test";
	}
	
	@RequestMapping("sendMessage")
	public String sendMessage(HttpSession session, Model model, String vol_time) {
		String st =coolsms_api.sendSMS(session,vol_time);
		System.out.println("받은 값" +st);
		if(st.equals("success")) {
			System.out.println("성공");
			model.addAttribute("message","success");
		}
		else {
			model.addAttribute("message","fail");
		}
		return "disabled/disabledMain";
	}
	@RequestMapping("getConnect")
	@ResponseBody
	public void getConnect(HttpSession session)
	{
		String id = (String)session.getAttribute("id");
		
		
		
		 
	}
	@RequestMapping("connect")
	public String connect(HttpServletRequest request, String connect,Model model,HttpSession session, String disabled_id) {
//		String path =	(String)request.getContextPath();
//		String path2 =	(String)request.getPathInfo();
//		System.out.println(path);
//		System.out.println(path2);
//		
//		
//		String id =(String)session.getAttribute("id");
//		//로그인을 하지 않고 수락을 눌렀을경우 즉, 외부에서 주소로 접속하여 버튼을 클릭한 경우.
//		if(id ==null || id.equals("")) {
//			session.setAttribute("invalid", "nologin");
//			return "redirect:/volunteer/connect.vol?id="+disabled_id;
//			
//		}
//		else {
//			
//			MemberVO vo=memberDao.selectAll(id);
//			String email = vo.getEmail();
//			//추가사항을 기입하지않고 그냥 로그인만 했을 경우.
//			if(email ==null || email.equals("")) {
//				
//				session.setAttribute("invalid", "nojoin");
//				return "redirect:/volunteer/connect.vol?id="+disabled_id;
//				
//				
//			}
//			
//		}
//		System.out.println(connect);
//		if(connect.equals("success")) {
//			model.addAttribute("message","connect");
//		}
//		else {
//			model.addAttribute("message","fail2");
//		}
		
		
		
		return "redirect:/";
		
	}
	
	@RequestMapping("memberInfo")
	public String memberInfo(Model model, String id) {
		if(id != null) {
			MemberVO vo = memdao.selectAll(id);
			if(vo.getPicture() == null) {
				if(vo.getGender() == 1) {
					vo.setPicture("../img/bono.png");
				}else {
					vo.setPicture("../img/poro.png");
				}
			}
			model.addAttribute("vo", vo);
		}
		return "disabled/memberInfo";
	}
	
}
