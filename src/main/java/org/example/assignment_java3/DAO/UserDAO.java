package org.example.assignment_java3.DAO;

import org.example.assignment_java3.entity.User;

import java.util.List;

public interface UserDAO {
    User createUser(User user);

    User getUserById(int id);

    User updateUser(User user);

    int deleteUser(String id);

    List<User> getAllUser();
}
