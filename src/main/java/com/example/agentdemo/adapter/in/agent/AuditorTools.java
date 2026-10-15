package com.example.agentdemo.adapter.in.agent;

import com.example.agentdemo.domain.UserApi;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * Driving adapter for the Auditor agent: read-only on purpose.
 * It cannot change data even if the model asks — that difference in permissions is the demo.
 * Names carry a {@code verify_} prefix so the two agents' tool sets never collide.
 */
@Component
public class AuditorTools {

    private final UserApi api;

    AuditorTools(UserApi api) {
        this.api = api;
    }

    @Tool("Read all users for verification. Never modifies anything.")
    String verify_list_users() {
        return ToolResults.safe(() -> String.valueOf(api.findAll()));
    }

    @Tool("Read one user by id for verification. Never modifies anything.")
    String verify_get_user(@P("user id") long id) {
        return ToolResults.safe(() -> api.findById(id).map(String::valueOf).orElse("ERROR: no user with id=" + id));
    }
}
