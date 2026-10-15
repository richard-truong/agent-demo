package com.example.agentdemo.adapter.out;

import com.example.agentdemo.domain.User;
import com.example.agentdemo.domain.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

/** Driven adapter: keeps users in memory. Swap for a database adapter without touching the core. */
@Component
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong();

    @Override
    public List<User> findAll() {
        return store.values().stream().sorted(Comparator.comparing(User::id)).toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public User save(User user) {
        User created = new User(seq.incrementAndGet(), user.name(), user.email());
        store.put(created.id(), created);
        return created;
    }

    @Override
    public Optional<User> update(Long id, User user) {
        return findById(id).map(old -> {
            User updated = new User(id, user.name(), user.email());
            store.put(id, updated);
            return updated;
        });
    }

    @Override
    public boolean delete(Long id) {
        return store.remove(id) != null;
    }
}
