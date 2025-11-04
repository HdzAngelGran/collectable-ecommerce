package org.arkn37.service;

import org.arkn37.dto.Filter;
import org.arkn37.dto.UserRequest;
import org.arkn37.model.User;
import org.arkn37.repository.UserRepository;

import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findUserByFilter(Filter filter) {
        return userRepository.findByFilter(filter.getSize(), filter.getPage());
    }

    public User findUserById(UUID uuid) {
        return userRepository.findById(uuid);
    }

    public User createUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setUuid(UUID.randomUUID());
        newUser.setName(userRequest.getName());
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(userRequest.getPassword());
        newUser.setEnabled(true);

        return userRepository.save(newUser);
    }

    public void updateUser(UUID uuid, UserRequest userRequest) {
        User user = userRepository.findById(uuid);
        if (userRequest.getName() != null)
            user.setName(userRequest.getName());
        if (userRequest.getUsername() != null)
            user.setUsername(userRequest.getUsername());
        if (userRequest.getPassword() != null)
            user.setPassword(userRequest.getPassword());

        userRepository.update(user);
    }

    public void deleteUser(UUID uuid) {
        User user = userRepository.findById(uuid);
        user.setEnabled(false);
        userRepository.update(user);
    }

    public boolean userExist(UUID uuid) {
        return userRepository.existsById(uuid);
    }

}
