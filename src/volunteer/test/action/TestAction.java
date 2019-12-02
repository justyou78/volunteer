package volunteer.test.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test/")
public class TestAction {
	
	
	@RequestMapping("main")
	public String test() {
		
		return "main";
	}
}
