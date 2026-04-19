package com.dash.serviceA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class ServiceA implements CommandLineRunner {

	@Value("${server.port}")
    private int port;
	
	public static void main(String[] args) {
		SpringApplication.run(ServiceA.class, args);
	}

	@Override
    public void run(String... args) {
        // Self-registration logic
        RestTemplate restTemplate = new RestTemplate();
        String registryUrl = "http://localhost:8080/register?name=ServiceA&url=http://localhost:" + port;
        restTemplate.postForObject(registryUrl, null, String.class);
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Service A!";
    }
}
