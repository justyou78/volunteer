package volunteer.data.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import volunteer.data.dao.MemberDAOImpl;

public class CheckMemberTypeAOP {
	@Autowired
	MemberDAOImpl memberDao;
	
	public String isVol(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("Vol AOP 진입");
		ServletRequestAttributes sr=
				(ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes());
		HttpServletRequest request = sr.getRequest();
		
		HttpSession session=  request.getSession();
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
		else {
			request.getSession().setAttribute("isLogin", "false");
			return "redirect:/main_join/main.vol";
			
		}
		
		
	}
	public String isDisabled(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("Disabled AOP 진입");
		ServletRequestAttributes sr=
				(ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes());
		HttpServletRequest request = sr.getRequest();
		
		HttpSession session=  request.getSession();
		if(session.getAttribute("id") != null ) {
			String id = (String)session.getAttribute("id");
			String memberType = memberDao.selectAll(id).getMember_type();
			if(memberType.equals("2")) {
				return (String) jp.proceed();
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
