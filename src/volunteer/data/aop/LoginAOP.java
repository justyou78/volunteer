package volunteer.data.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import volunteer.data.dao.MemberDAOImpl;
import volunteer.data.vo.MemberVO;


//로그인 여부를 확인하기 위핸 접근권한 AOP
public class LoginAOP {
		@Autowired
		MemberDAOImpl memberDao;
		public String around(ProceedingJoinPoint jp) throws Throwable
		{
			//컨트롤러가 아닌 class에서 서블릿 리퀘스트를 가져오는 방법.
			ServletRequestAttributes sr=
					(ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes());
			HttpServletRequest request = sr.getRequest();
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			
			//로그인이 되어있지 않은 페이지 이동을 요청할 경우 다시 메인페이지로 이동시킨다.
			if(session !=null) {
				if(id ==null || id.equals("")) {
					request.getSession().setAttribute("isLogin", "false");
					//메인페이지로 이동
					return "redirect:/main_join/main.vol";
				}
			}
			
			MemberVO vo =  memberDao.selectAll(id);
			//로그인은 되어있지만 추가사항을 기입하지 않았을 경우 추가사항 기입란으로 이동시킨다.
			if(vo.getEmail()== null || vo.getEmail().equals("")){
				return (String) jp.proceed();
			}
			//장애인이 봉사자페이지를, 봉사자가 장애인 페이지를 클릭했을 때 접근을 제한하고 권한에 맞는 페이지로 이동시킨다.
			else {
				String st = request.getParameter("page");
				if(vo.getMember_type().equals("1")) {
				
					if(!st.equals((String)vo.getMember_type())) {
						session.setAttribute("auth", "no");
					}
					return "redirect:/volunteer/main.vol";
					
				}
				else {
					//장애인 화면으로 이동
					if(!st.equals((String)vo.getMember_type())) {
						session.setAttribute("auth", "no.");
						
					}
					return "redirect:/disabled/disabledMain.vol?id="+id;
				}
				
				
			}
			
		}
}
