package com.example.agentdemo.adapter.in.startup;

import com.example.agentdemo.domain.User;
import com.example.agentdemo.domain.UserUseCase;
import java.util.List;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Driving adapter: another way to drive the application, this time at startup instead of over HTTP.
 * Seeds five users so the demo has something to talk about before the first prompt.
 *
 * shortcut: runs on every start, which is fine for an in-memory store; guard it on an empty
 * repository if this is ever backed by a real database.
 */
@Component
public class SeedUsers implements ApplicationRunner {

    private static final List<User> SEED = List.of(
            new User(null, "An Nguyen", "an.nguyen@example.com"),
            new User(null, "Binh Tran", "binh.tran@example.com"),
            new User(null, "Chi Le", "chi.le@example.com"),
            new User(null, "Dung Pham", "dung.pham@example.com"),
            new User(null, "Em Hoang", "em.hoang@example.com"));

    private final UserUseCase users;

    SeedUsers(UserUseCase users) {
        this.users = users;
    }

    @Override
    public void run(ApplicationArguments args) {
        SEED.forEach(users::create);
    }
}
