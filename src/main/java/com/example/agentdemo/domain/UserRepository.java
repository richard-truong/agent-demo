package com.example.agentdemo.domain;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port: how the core stores users.
 * Adapter today: {@code InMemoryUserRepository}. One line to swap for a database.
 */
public interface UserRepository {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update(Long id, User user);

    boolean delete(Long id);
}
