package org.example.assignment_java3.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userDAO.findById(id));
    }

    @Override
    public boolean insert(User user) {
        return userDAO.insert(user) > 0;
    }

    @Override
    public boolean update(User user) {
        return userDAO.update(user) > 0;
    }

    @Override
    public boolean delete(String id) {
        return userDAO.delete(id) > 0;
    }

    @Override
    public boolean exists(String id) {
        return userDAO.findById(id) != null;
    }

    @Override
    public Optional<User> checkLogin(String username, String password) {
        return userDAO.checkLogin(username, password);
    }
}
