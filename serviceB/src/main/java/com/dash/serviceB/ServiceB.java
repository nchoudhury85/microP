package com.dash.serviceB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class ServiceB {

	public static void main(String[] args) {
		SpringApplication.run(ServiceB.class, args);
	}

	private final RestTemplate restTemplate = new RestTemplate();
    private final String REGISTRY_URL = "http://localhost:8080/discover?name=ServiceA";

    @GetMapping("/consume")
    public String callServiceA() {
        // 1. Discovery: Find where ServiceA is
        String serviceAAddress = restTemplate.getForObject(REGISTRY_URL, String.class);

        // 2. Communication: Call ServiceA directly
        return restTemplate.getForObject(serviceAAddress + "/hello", String.class);
    }
}
