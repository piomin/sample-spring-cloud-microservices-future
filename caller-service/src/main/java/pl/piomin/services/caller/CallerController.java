package pl.piomin.services.caller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.RoundRobinLoadBalancer;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
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
	@Autowired
	LoadBalancerClientFactory clientFactory;

	@GetMapping
	public String call() {
		RoundRobinLoadBalancer lb = clientFactory.getInstance("callme-service", RoundRobinLoadBalancer.class);
		ServiceInstance instance = lb.choose().block().getServer();
		String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/callme";
		String callmeResponse = template.getForObject(url, String.class);
		LOGGER.info(": {}");
		return "I'm Caller running on port " + environment.getProperty("local.server.port")
				+ " calling-> " + callmeResponse;
	}

}