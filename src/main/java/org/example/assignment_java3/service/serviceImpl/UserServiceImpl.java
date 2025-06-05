package org.example.assignment_java3.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.assignment_java3.DAO.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public User getUserById(String id) {
        try {
            return userDAO.getUserById(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy user: " + e.getMessage());
            return null;
        }
    }
}
