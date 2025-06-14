package org.example.assignment_java3.dao;

import org.example.assignment_java3.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User createUser(User user);

    User getUserById(String id);

    boolean updateUser(User user);

    int deleteUser(String id);

    List<User> getAllUsers();

    Optional<User> checkLogin(String username, String password);


}
