package com.example.agentdemo.adapter.in.agent;

import com.example.agentdemo.domain.User;
import com.example.agentdemo.domain.UserApi;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

/**
 * Driving adapter: the LLM calls the application through these methods.
 * It reaches users through the {@link UserApi} outbound port — real HTTP against the REST API.
 * Tool names use underscores only, because DeepSeek accepts just {@code ^[a-zA-Z0-9_-]+$}.
 */
@Component
public class ManagerTools {

    private final UserApi api;

    ManagerTools(UserApi api) {
        this.api = api;
    }

    @Tool("List every user.")
    String list_users() {
        return ToolResults.safe(() -> String.valueOf(api.findAll()));
    }

    @Tool("Get one user by id.")
    String get_user(@P("user id") long id) {
        return ToolResults.safe(() -> api.findById(id).map(String::valueOf).orElse("ERROR: no user with id=" + id));
    }

    @Tool("Create a new user. Returns the created user including its id.")
    String create_user(@P("user name") String name, @P("user email") String email) {
        return ToolResults.safe(() -> String.valueOf(api.create(new User(null, name, email))));
    }

    @Tool("Update the name and email of a user by id.")
    String update_user(@P("user id") long id, @P("new name") String name, @P("new email") String email) {
        return ToolResults.safe(() -> api.update(id, new User(id, name, email))
                .map(String::valueOf)
                .orElse("ERROR: no user with id=" + id));
    }

    @Tool("Delete a user by id.")
    String delete_user(@P("user id") long id) {
        return ToolResults.safe(() -> api.delete(id) ? "OK: deleted user " + id : "ERROR: no user with id=" + id);
    }
}
