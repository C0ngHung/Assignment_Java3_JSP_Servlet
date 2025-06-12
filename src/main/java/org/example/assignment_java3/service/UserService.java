package org.example.assignment_java3.service;

import org.example.assignment_java3.entity.User;

import java.util.Optional;

public interface UserService {

    User createUser (User user);

    boolean updateUser (User user);

    User getUserById (String id);

    int deleteUser (String id);

    Optional<User> checkLogin(String username, String password);

}
