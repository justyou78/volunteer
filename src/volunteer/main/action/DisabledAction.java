package volunteer.main.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

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

	// 장애인 메인화면
	@RequestMapping("disabledMain")
	public String disabledMain(HttpSession session, Model model) throws ClientProtocolException, IOException {

		// 봉사자 버튼을 클릭하였을 경우.
		if (session.getAttribute("auth") != null) {
			model.addAttribute("auth", "봉사자 페이지에 접근 할 수 없습니다.");
			session.removeAttribute("auth");
		}

		String id = (String) session.getAttribute("id");
		MemberVO vo = memberDao.selectAll(id);
		kakao_http_client khc = new kakao_http_client();
		//좌표와 관련된 내용을 가져온다
		HashMap<String, String> hs = khc.get(vo);
		//자신의 좌표를 모델에 추가하는 과정.
		double x = Double.parseDouble(hs.get("x").replaceAll("\"", ""));
		double y = Double.parseDouble(hs.get("y").replaceAll("\"", ""));
		model.addAttribute("x", x);
		model.addAttribute("y", y);

		return "disabled/disabledMain";
	}

	// 주변 봉사자에게 메세지를 보내는 Ajax Controller
	@RequestMapping("sendMessage")
	@ResponseBody
	public String sendMessage(HttpServletRequest request, HttpSession session, Model model, String vol_time) {
		// '구'를 기준으로 모든 사람에게 메세지를 보낸다.
		String st = coolsms_api.sendSMS(session, vol_time);
		// '성공메세지'를 띄워주기 위한 과정
		if (st.equals("success")) {
			request.setAttribute("message", "success");
			return "success";
		} else {
			request.setAttribute("message", "fail");
			return "fail";
		}

	}

	// 봉사자의 봉사신청이 들어와서 장애인 화면에 List를 띄워주는 Ajax Controller
	@ResponseBody
	@RequestMapping(value = "getConnect", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Object getConnect(HttpSession session) {
		
		String id = (String) session.getAttribute("id");
		// 요청이 수락된 모든 데이터를 가져옵니다.
		List<ConnectVO> list = connectDao.selectAll(id);
		List<MemberVO> returnList = new ArrayList<MemberVO>();
		//데잍가 없을 경우.
		if (list == null || list.size() == 0) {
			return null;
		}
		
		for (ConnectVO vo : list) {
			//리턴 리스트에 수락한 봉사자의 정보를 추가합니다.
			returnList.add(memberDao.selectAll(vo.getVol_id()));
			//정보를 추가한 후, 연결된 데이터를 삭제해줍니다.
			connectDao.deleteConnect(vo.getVol_id(), id);

		}
		JSONArray jarray = new JSONArray();
		//필요한 데이터만 제이슨 데이터로 추가합니다.
		for (MemberVO vo : returnList) {
			JSONObject data = new JSONObject();
			data.put("name", vo.getName());
			data.put("callnumber", vo.getCallnumber());
			data.put("address", vo.getAddress());
			data.put("age", vo.getAge());
			data.put("gender", vo.getGender());
			data.put("id", vo.getId());
			data.put("picture", vo.getPicture());
			jarray.add(data);
		}
		JSONObject resultObject = new JSONObject();
		resultObject.put("member", jarray);

		//결과오프젝트를 반환합니다.
		return resultObject;
	}
	//요청취소 버튼을 클릭하였을 경우 Connect 테이블에 생성되었던 자신의 리스트를 삭제합니다.
	@RequestMapping("delete_connect")
	@ResponseBody
	public String delete_connect(HttpSession session) {
		String id = (String) session.getAttribute("id");
		connectDao.deleteConnect(id);
		return "success";
	}
	
	// 장애인이 List에서 한명의 봉사자를 선택하여 메세지를 전송하는 Ajax Controller
	@RequestMapping("resultMessage")
	@ResponseBody
	public String resultMessage(@RequestBody String id, HttpSession session)
			throws ClientProtocolException, IOException {
		int idx = id.indexOf('=') + 1;
		id = id.substring(idx, id.length());
		String myId = (String)session.getAttribute("id");
		//선택한 아이디의 사용자에게 문자메세지를 보내는 메서트 호ㅜㄹ합니다
		String st = coolsms_api.sendSMSOne(id.toString(),myId);
		return "success";

	}

	// 봉사가 완료된 후, Database에 내용을 저장하는 Ajax Controller
	@RequestMapping("insert_vol")
	@ResponseBody
	public String insert_vol(@RequestBody ListVO vo, HttpSession session) {
		// 처음 Disabeld_id값으로 별점을 챙겨온다
		// listvo에 point변수가 없어서 어쩔수 없이 이렇게 가져온다;
		
		//별점
		String give_star = vo.getDisabled_id();
		//별점 업데이트
		memberDao.updatePoint(give_star, vo.getVol_id());
		String id = (String) session.getAttribute("id");
		vo.setDisabled_id(id);
		
		//봉사 리스트 테이블에 데이터 삽입.
		volListDao.insert_vol_list(vo);
		
		//멤버 테이블에 봉사시간 수정하기.
		memberDao.updateVolTime(vo);

		return "성공";

	}

	// 지도에 유저의 간략한 정보를 보여주는 Controller
	@RequestMapping("brief")
	public String brief(Model model, String id) {
		if (id != null) {
			MemberVO vo = memdao.selectAll(id);
			String gender = "";
			//이미지가 없을경우 기존에 설정되어있는 이미지를 나타낸다.
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
		return "disabled/brief";
	}

}
