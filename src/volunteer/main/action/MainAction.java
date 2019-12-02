package volunteer.main.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main_join/")
public class MainAction {
	@RequestMapping("main")
	public String main() {
		return "main_join/main";
	}
	
	@RequestMapping("join")
	public String join() {
		return "main_join/join";
	}

}
