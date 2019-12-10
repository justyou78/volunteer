package volunteer.main.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/disabled/")
public class DisabledAction {
	
	@RequestMapping("disabledMain")
	public String disabledMain() {
		
		return "disabled/disabledMain";
	}
	
	@RequestMapping("test")
	public String test(@RequestParam String str) {
		System.out.println(str);
		return "disabled/test";
	}
	
	
}
