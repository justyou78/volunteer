package volunteer.main.action;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import volunteer.data.dao.MemberDAOImpl;
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


}
