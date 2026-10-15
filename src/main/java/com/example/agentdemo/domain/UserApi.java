package com.example.agentdemo.domain;

import java.util.List;
import java.util.Optional;

/**
 * Outbound port: the published user REST API, as seen by an external client.
 * The agent's tools use this port (adapter: {@code HttpUserApi}, real HTTP calls).
 *
 * Separate from {@link UserRepository} on purpose: this one mirrors a public contract
 * that can change for reasons the storage never cares about.
 */
public interface UserApi {

    List<User> findAll();

    Optional<User> findById(Long id);

    User create(User user);

    Optional<User> update(Long id, User user);

    boolean delete(Long id);
}
