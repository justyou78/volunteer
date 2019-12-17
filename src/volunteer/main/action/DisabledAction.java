package volunteer.main.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;

import volunteer.data.dao.ConnectDAOImpl;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.method.Coolsms_Restapi;
import volunteer.data.vo.ConnectVO;
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
	public String test(@RequestParam String str) {
		System.out.println(str);
		return "disabled/test";
	}
	@RequestMapping("sendMessage")
	@ResponseBody
	public String sendMessage(HttpServletRequest request, HttpSession session, Model model, String vol_time) {
		String st =coolsms_api.sendSMS(session,vol_time);
		System.out.println("받은 값" +st);
		if(st.equals("success")) {
			System.out.println("성공");
			request.setAttribute("message", "success");
			return "success";
		}
		else {
			request.setAttribute("message", "success");
			return "fail";
		}
		
	}
	@ResponseBody
	@RequestMapping(value = "getConnect", produces = "application/json", method = { RequestMethod.GET, RequestMethod.POST })
	public Object  getConnect(HttpSession session)
	{
		
		String id = (String)session.getAttribute("id");
		List<ConnectVO> list = connectDao.selectAll(id);
		System.out.println(list.size()+"사이즈");
		List<MemberVO> returnList = new ArrayList<MemberVO>();
		if(list == null || list.size() ==0) {
			return null;
		}
		
		System.out.println("잘왔다.1");
		for (ConnectVO vo: list) {
			
			returnList.add(memberDao.selectAll(vo.getVol_id())); 
			connectDao.deleteConnect(vo.getVol_id(), id);
			
		}
		System.out.println("잘왔다2.");
		JSONArray jarray = new JSONArray();
		for (MemberVO vo : returnList) {
			JSONObject data = new JSONObject();
			data.put("name", vo.getName());
			data.put("callnumber", vo.getCallnumber());
			data.put("address", vo.getAddress());
			data.put("age", vo.getAge());
			data.put("gender", vo.getGender());
			data.put("id",vo.getId());
			jarray.add(data);
			
			
			
			
			
		}
		JSONObject resultObject = new JSONObject();
		resultObject.put("member", jarray);
		
		System.out.println(resultObject);

		String a = "Dk";
        return resultObject;
        
		/*
		 * JSONArray mapResult = JSONArray.fromObject(returnList);
		 * model.addAttribute("mapResult", mapResult);
		 * System.out.println(test.toString()+"테스트");
		 */
		
		
		
		 
	}
	
	@RequestMapping("resultMessage")
	@ResponseBody
	public String resultMessage(@RequestBody String  id, HttpSession session) {
		int idx = id.indexOf('=')+1;
		id = id.substring(idx,id.length());
		System.out.println(id.toString()+"마지막 메세지");
		String st =coolsms_api.sendSMSOne(id.toString());
		System.out.println(st);
		
		return "success";
		
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
	
	
}
