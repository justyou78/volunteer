package volunteer.data.component;


import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public class Component {
	
	@PostConstruct
	public String getLocalhost() {
		return "http://192.168.0.48:8081";
	}
	
	

	
}
