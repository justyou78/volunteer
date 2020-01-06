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
	
	@RequestMapping("connect")
	public String connect(String disabled_id,Model model) {
		model.addAttribute("disabled_id",disabled_id);
		return "volunteer/connect";
	}

	@RequestMapping("main")
	public String main(HttpSession session, Model model, String sysdate) {
		
		if(session.getAttribute("auth") !=null ) {
			model.addAttribute("auth","장애인 페이지에 접근 할 수 없습니다.");
			session.removeAttribute("auth");
		}
		System.out.println(sysdate);
		
		
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
		
		
		//후원을 많이한 순서대로 5명의 데이터 가져오기.
		List<DonationVO> donationList = donationDao.select();
		
		//봉사를 많이한 순서대로 5명의 데이터 가져오기.
		List<MemberVO> volList = memberDao.selectAllDescTime();
		
		//각 데이터 view로 이동하기 위해 model에 추가한다.
		model.addAttribute("list", list);
		model.addAttribute("donationList", donationList);
		model.addAttribute("hm", hm);
		model.addAttribute("volList", volList);
		

		return "volunteer/main";

	}

	
	
	@RequestMapping("connect_pro")
	public String connect_pro(MemberVO vo, Model model,String disabled_id)
	{
		MemberVO memberVO =memberDao.selectAllFromEmail(vo.getEmail());
		System.out.println(disabled_id);
		
		
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

	

	@RequestMapping("sponsor")
	public String sponsor() {

		return "volunteer/sponsor";
	}

	

	
	
	
}
