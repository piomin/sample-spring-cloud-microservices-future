package pl.piomin.services.callme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/callme")
public class CallmeController {

    @Autowired
    Environment environment;

    @GetMapping
    public String call() {
        return "I'm Callme running on port " + environment.getProperty("local.server.port");
    }

    @GetMapping("/slow")
    public String slow() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        return "I'm Slow Callme running on port " + environment.getProperty("local.server.port");
    }

}
