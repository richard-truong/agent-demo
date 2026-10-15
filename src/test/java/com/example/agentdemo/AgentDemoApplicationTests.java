package com.example.agentdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// a dummy key so the context loads without a real one; nothing is called over the network here
@SpringBootTest(properties = "agent.provider.api-key=test")
class AgentDemoApplicationTests {

    @Test
    void contextLoads() {
    }

}
