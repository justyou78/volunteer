package volunteer.main.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import volunteer.data.dao.MemberDAO;
import volunteer.data.dao.VolunteerDAOImpl;

@Controller
@RequestMapping("/volunteer/")
public class VolunteerAction {
	@Autowired
	VolunteerDAOImpl VolunteerDao;
	@RequestMapping("main")
	public String main(HttpSession session, Model model, String sysdate ) {
		//String imgName ;
		String id = (String)session.getAttribute("id");
		String imgName = null;
		if(sysdate == null) {
			Date d  = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			imgName =VolunteerDao.getGraph(id, (String)sdf.format(d));
		}
		else {
			System.out.println(sysdate.length()-7);
			imgName=  VolunteerDao.getGraph(id,sysdate.substring(0,4));
		}
		
		
		
		
		model.addAttribute("imgName", imgName );
	
//		if(sysdate == null || sysdate.equals("")) {
//			System.out.println(sysdate+"시간");
//			imgName= VolunteerDao.getGraph((String)session.getAttribute("id"),new Date());
//				
//		}
//		else {
//			System.out.println(sysdate+"시간2");
//			imgName= VolunteerDao.getGraph((String)session.getAttribute("id"),sysdate);
//			
//		}
//		System.out.println("봉사진입");
		
		
		return "volunteer/main";
		
	}
}
