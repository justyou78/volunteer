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
import volunteer.data.dao.ConnectDAOImpl;
import volunteer.data.dao.DonationDAOImpl;
import volunteer.data.dao.MemberDAO;
import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.dao.VolListDaoImpl;
import volunteer.data.dao.VolunteerDAOImpl;
import volunteer.data.method.Kakao_Restapi;
import volunteer.data.vo.DonationVO;
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
	@Autowired
	ConnectDAOImpl connectDao;
	@Autowired
	VolListDaoImpl volListDao;
	@Autowired
	DonationDAOImpl donationDao;
	
	
	//장애인이 봉사 요청시, 봉사자에게 전송되는 페이지 컨트롤러
	@RequestMapping("connect")
	public String connect(String disabled_id,Model model) {
		model.addAttribute("disabled_id",disabled_id);
		return "volunteer/connect";
	}
	
	//봉사자 메인페이지 컨트롤러
	@RequestMapping("main")
	public String main(HttpSession session, Model model, String sysdate) {
		
		//잘못된 버튼을 눌렀을 경우.
		if(session.getAttribute("auth") !=null ) {
			model.addAttribute("auth","장애인 페이지에 접근 할 수 없습니다.");
			session.removeAttribute("auth");
		}
	
		String id = (String) session.getAttribute("id");
		String imgName = null;
		//그래프의 이미지 가져오기.
		if (sysdate == null) {
			imgName = VolunteerDao.getGraph(id, (String) sdf.format(d));
		} else {
			imgName = VolunteerDao.getGraph(id, sysdate.substring(0, 4));
		}
		model.addAttribute("imgName", imgName);
		System.out.println("test-1");
		// 테이블 나타내기
		//데이터가 없을경우 현재 월 기준의 테이블생성
		if (sysdate == null) {
			sdf.applyLocalizedPattern("yyyy-MM");
			sysdate = (String) sdf.format(d);
		//날짜를 선택했을 경우 sysdate를 이용한 날짜에 맞는 테이블 생성.
		} else {
			sysdate = sysdate.substring(0, 7);
		}
		
		List<ListVO> list = VolunteerDao.getVol_list(id, sysdate);
		HashMap<String, String> hm = new HashMap<String, String>();
		//ListVO내부에 사용자의 이름을 기입할수있는 데이터가 없으므로 따로 MAP을 이용하여 추가 이름 데이터를 보내준다. 
		for (ListVO vo : list) {
			if (!hm.containsKey(vo.getVol_id())) {
				hm.put(vo.getVol_id(), memberDao.getName(vo.getVol_id()));
			}
			if (!hm.containsKey(vo.getDisabled_id())) {
				hm.put(vo.getDisabled_id(), memberDao.getName(vo.getDisabled_id()));
			}
		}
		
		System.out.println("test");
		
		//후원을 많이한 순서대로 5명의 데이터 가져오기.
		List<DonationVO> donationList = donationDao.select();
		System.out.println("test2");
		
		//봉사를 많이한 순서대로 5명의 데이터 가져오기.
		List<MemberVO> volList = memberDao.selectAllDescTime();
		System.out.println("test3");
		
		//각 데이터 view로 이동하기 위해 model에 추가한다.
		model.addAttribute("list", list);
		model.addAttribute("donationList", donationList);
		model.addAttribute("hm", hm);
		model.addAttribute("volList", volList);
		

		return "volunteer/main";

	}

	
	//연결된 봉사자가 아이디와 비밀번호를 입력한 후, 수락버튼을 눌렀을 경우.
	@RequestMapping("connect_pro")
	public String connect_pro(MemberVO vo, Model model,String disabled_id)
	{
		MemberVO memberVO =memberDao.selectAllFromEmail(vo.getEmail());
		
		
		//이메일과 패스워드가 일치하는지 확인.
		if(memberVO != null && memberVO.getEmail() != null) {
			if(memberVO.getPw().equals(vo.getPw())) {
				System.out.println(memberVO.getId());
				System.out.println(memberVO.getEmail());
				System.out.println(connectDao);
				connectDao.updateCheck_vol(memberVO.getId(),disabled_id);
			}else {
				model.addAttribute("noPw","비밀번호가 일치하지 않습니다.");
			}
		}
		else {
			model.addAttribute("noEmail","이메일이 존재하지 않습니다.");
		}
		model.addAttribute("disabled_id",disabled_id);
		return "volunteer/connect";
	}	
}
