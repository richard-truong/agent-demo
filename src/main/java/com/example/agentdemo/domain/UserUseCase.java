package com.example.agentdemo.domain;

import java.util.List;
import java.util.Optional;

/** Inbound port: what the application can do, as seen by a driving adapter (REST, agent, CLI...). */
public interface UserUseCase {

    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    Optional<User> update(Long id, User user);

    boolean delete(Long id);
}
