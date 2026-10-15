package com.example.agentdemo.adapter.out;

import com.example.agentdemo.domain.User;
import com.example.agentdemo.domain.UserApi;
import java.util.List;
import java.util.Optional;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

/**
 * Driven adapter: talks to the published REST API over real HTTP.
 * A 404 becomes an empty Optional; anything else propagates to the caller (the agent adapter
 * turns it into text the model can read).
 */
@Component
public class HttpUserApi implements UserApi {

    private final RestClient http;

    HttpUserApi(RestClient apiRestClient) {
        this.http = apiRestClient;
    }

    @Override
    public List<User> findAll() {
        return http.get().uri("/api/users").retrieve().body(new ParameterizedTypeReference<>() {
        });
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return Optional.ofNullable(http.get().uri("/api/users/{id}", id).retrieve().body(User.class));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public User create(User user) {
        return http.post()
                .uri("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new UserPayload(user.name(), user.email()))
                .retrieve()
                .body(User.class);
    }

    @Override
    public Optional<User> update(Long id, User user) {
        try {
            return Optional.ofNullable(http.put()
                    .uri("/api/users/{id}", id)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new UserPayload(user.name(), user.email()))
                    .retrieve()
                    .body(User.class));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            http.delete().uri("/api/users/{id}", id).retrieve().toBodilessEntity();
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }

    /** The API assigns ids, so clients never send one. */
    private record UserPayload(String name, String email) {
    }
}
