package pl.piomin.services.caller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/caller")
public class CallerController {

    @Autowired
    Environment environment;
    @Autowired
    RestTemplate template;

    @GetMapping
    public String call() {
        String callmeResponse = template.getForObject("http://callme-service/callme", String.class);
        return "I'm Caller running on port " + environment.getProperty("local.server.port")
                + " calling-> " + callmeResponse;
    }

}
