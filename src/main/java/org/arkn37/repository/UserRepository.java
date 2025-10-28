package org.arkn37.repository;

import org.arkn37.exception.NotFoundException;
import org.arkn37.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    Map<UUID, User> database = new HashMap<>();

    public User save(User user) {
        database.put(user.getUuid(), user);
        log.info("User saved to 'database': {}", user.getName());
        return user;
    }

    public User findById(UUID id) {
        User user = database.get(id);
        if (user == null || !user.isEnabled()) throw new NotFoundException("User not found");
        return user;
    }

    public List<User> findByFilter(int size, int page) {
        int totalElements = database.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        if (page >= totalPages || page < 0 || size <= 0) return Collections.emptyList();

        int startIndex = page * size;
        int endIndex = Math.min(startIndex + size, totalElements);
        return database.values().stream().toList().subList(startIndex, endIndex);
    }

    public void update(User user) {
        database.put(user.getUuid(), user);
    }

    public boolean existsById(UUID id) {
        return database.containsKey(id);
    }
}
