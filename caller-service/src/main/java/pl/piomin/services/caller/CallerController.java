package pl.piomin.services.caller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/caller")
public class CallerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CallerController.class);

	@Autowired
	Environment environment;
	@Autowired
	RestTemplate template;

	@GetMapping
	public String call() {
		String url = "http://callme-service/callme";
		String callmeResponse = template.getForObject(url, String.class);
		LOGGER.info("Response: {}", callmeResponse);
		return "I'm Caller running on port " + environment.getProperty("local.server.port")
				+ " calling-> " + callmeResponse;
	}

	@GetMapping("/slow")
	public String slow() {
		String url = "http://callme-service/callme/slow";
		String callmeResponse = template.getForObject(url, String.class);
		LOGGER.info("Response: {}", callmeResponse);
		return "I'm Caller running on port " + environment.getProperty("local.server.port")
				+ " calling-> " + callmeResponse;
	}
}