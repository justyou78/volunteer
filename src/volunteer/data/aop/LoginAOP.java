package volunteer.data.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.vo.MemberVO;

public class LoginAOP {
		@Autowired
		MemberDAOImpl memberDao;
	
		public String around(ProceedingJoinPoint jp) throws Throwable
		{
			System.out.println("aop진입");
			//컨트롤러가 아닌 class에서 서블릿 리퀘스트를 가져오는 방법.
			ServletRequestAttributes sr=
					(ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes());
			HttpServletRequest request = sr.getRequest();
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			//로그인 여부 확인하기.
			if(session !=null) {
				
				if(id ==null || id.equals("")) {
					request.getSession().setAttribute("isLogin", "false");
					return "redirect:/main_join/main.vol";
				}
			}
			
			
			
			MemberVO vo =  memberDao.selectAll(id);
			if(vo.getEmail()== null || vo.getEmail().equals("")){
				return (String) jp.proceed();
			}
			else {
				String st = request.getParameter("page");
				if(vo.getMember_type().equals("1")) {
				
					if(!st.equals((String)vo.getMember_type())) {
						System.out.println("불일치");
						session.setAttribute("auth", "no");
					}
					return "redirect:/volunteer/main.vol";
					
				}
				else {
					//장애인 화면으로 이동
					if(!st.equals((String)vo.getMember_type())) {
						System.out.println("불일치");
						session.setAttribute("auth", "no.");
						
					}
					return "redirect:/disabled/disabledMain.vol?id="+id;
				}
				
				
			}
			
		}
}
