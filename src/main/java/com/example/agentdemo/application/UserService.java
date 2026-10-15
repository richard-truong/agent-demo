package com.example.agentdemo.application;

import com.example.agentdemo.domain.User;
import com.example.agentdemo.domain.UserRepository;
import com.example.agentdemo.domain.UserUseCase;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** Application service: the inbound port implemented on top of the outbound port. */
@Service
public class UserService implements UserUseCase {

    private final UserRepository repository;

    UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User create(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return repository.update(id, user);
    }

    @Override
    public boolean delete(Long id) {
        return repository.delete(id);
    }
}
