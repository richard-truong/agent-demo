package com.example.agentdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/** Which model provider to run on — set per Spring profile in application-*.properties. */
@ConfigurationProperties("agent.provider")
public record ProviderProperties(String type, String apiKey, String model, String baseUrl) {

    boolean gemini() {
        return "gemini".equalsIgnoreCase(type);
    }
}
