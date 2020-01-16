package volunteer.data.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import volunteer.data.dao.MemberDAOImpl;


//로그인 여부와 MemberType을 확인하기 위한 AOP
public class CheckMemberTypeAOP {
	@Autowired
	MemberDAOImpl memberDao;
	
	//봉사자인지 확인하기 위한 AOP
	public String isVol(ProceedingJoinPoint jp) throws Throwable {
		
		ServletRequestAttributes sr=
				(ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes());
		HttpServletRequest request = sr.getRequest();
		HttpSession session=  request.getSession();
		
		//아이디가 존재하는지 확인후 존재한다면  타입을 확인한 후, 페이지 이동
		if(session.getAttribute("id") != null ) {
			String id = (String)session.getAttribute("id");
			String memberType = memberDao.selectAll(id).getMember_type();
			if(memberType.equals("1")) {
				return (String) jp.proceed();
			}
			else {
				request.getSession().setAttribute("isLogin", "notType");
				return "redirect:/main_join/main.vol";
			}
			
		}
		//로그인하지 않은 상태에서 URI를 입력할 경우.
		else {
			request.getSession().setAttribute("isLogin", "false");
			return "redirect:/main_join/main.vol";
			
		}
		
		
	}
	public Object isDisabled(ProceedingJoinPoint jp) throws Throwable {
		ServletRequestAttributes sr=
				(ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes());
		HttpServletRequest request = sr.getRequest();
		
		HttpSession session=  request.getSession();
		if(session.getAttribute("id") != null ) {
			String id = (String)session.getAttribute("id");
			String memberType = memberDao.selectAll(id).getMember_type();
			if(memberType.equals("2")) {
				return jp.proceed();
			}
			else {
				request.getSession().setAttribute("isLogin", "notType");
				return "redirect:/main_join/main.vol";
			}
			
		}
		else {
			request.getSession().setAttribute("isLogin", "false");
			return "redirect:/main_join/main.vol";
			
		}
		
		
	}
}
