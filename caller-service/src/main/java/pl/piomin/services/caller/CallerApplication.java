package pl.piomin.services.caller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CallerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CallerApplication.class, args);
	}

	@LoadBalanced
	@Bean
	RestTemplate template() {
		return new RestTemplate();
	}

}
