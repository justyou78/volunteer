package volunteer.main.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
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
import volunteer.data.dao.VolListDaoImpl;
import volunteer.data.method.kakao_http_client;
import volunteer.data.method.Coolsms_Restapi;
import volunteer.data.vo.ConnectVO;
import volunteer.data.vo.ListVO;
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
	@Autowired
	VolListDaoImpl volListDao;
	
	
	@RequestMapping("disabledMain")
	public String disabledMain(HttpSession session,Model model) throws ClientProtocolException, IOException {
		
		if(session.getAttribute("auth") !=null ) {
			System.out.println("진입");
			model.addAttribute("auth","봉사자만 접근 할 수 있습니다.");
			session.removeAttribute("auth");
		}
		String id = (String) session.getAttribute("id");
		MemberVO vo = memberDao.selectAll(id);
	      kakao_http_client khc = new kakao_http_client();
	      HashMap<String, String> hs = khc.get(vo);
	      double x = Double.parseDouble(hs.get("x").replaceAll("\"", "")); 
	      double y = Double.parseDouble(hs.get("y").replaceAll("\"", ""));
	      
	      System.out.println("x좌표 >> " + x);
	      System.out.println("y좌표 >> " + y);
	      
	      model.addAttribute("x",x);
	      model.addAttribute("y",y);
		
		return "disabled/disabledMain";
	}
	
	@RequestMapping("test")
	public String test(){
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
	public String resultMessage(@RequestBody String  id, HttpSession session) throws ClientProtocolException, IOException {
		int idx = id.indexOf('=')+1;
		id = id.substring(idx,id.length());
		System.out.println(id.toString()+"마지막 메세지");
		
		
		String st =coolsms_api.sendSMSOne(id.toString());
		System.out.println(st);
		
		return "success";
		
	}
	
	@RequestMapping("insert_vol")
	@ResponseBody
	public String insert_vol(@RequestBody ListVO vo, HttpSession session) {
		String id=(String) session.getAttribute("id");
		vo.setDisabled_id(id);
		
		volListDao.insert_vol_list(vo);
		
		
		memberDao.updateVolTime(vo);
		return "성공";
		
		
		
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
	
	@RequestMapping("brief")
	   public String brief(Model model, String id) {
	      if(id != null) {
	         MemberVO vo = memdao.selectAll(id);
	         String gender = "";
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
	      return "disabled/brief";
	   }
	
	
	
}
