package pl.piomin.services.caller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/caller")
public class CallerController {

    @Autowired
    Environment environment;

    @GetMapping
    public String call() {
        return "I'm Caller running on port " + environment.getProperty("local.server.port");
    }

}
