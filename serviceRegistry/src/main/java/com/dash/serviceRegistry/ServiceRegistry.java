package com.dash.serviceRegistry;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ServiceRegistry {

    // Storage for service instances: Map<ServiceName, Set<InstanceURL>>
    private final Map<String, Set<String>> registry = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        SpringApplication.run(ServiceRegistry.class, args);
    }

    @PostMapping("/register")
    public void register(@RequestParam String name, @RequestParam String url) {
        registry.computeIfAbsent(name, k -> ConcurrentHashMap.newKeySet()).add(url);
        System.out.println("Registered: " + name + " at " + url);
    }

    @GetMapping("/discover")
    public String discover(@RequestParam String name) {
        return registry.getOrDefault(name, Collections.emptySet())
                .stream().findFirst() // Basic discovery: return the first available
                .orElseThrow(() -> new RuntimeException("Service not found"));
    }
}