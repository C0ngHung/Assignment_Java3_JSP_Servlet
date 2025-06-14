package org.example.assignment_java3.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;

import java.util.Optional;

@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    @Override
    public User createUser(User user) {
        try {
            return userDAO.createUser(user);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi tạo user: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            return userDAO.updateUser(user);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi cập nhật user: " + e.getMessage());
            return false;
        }
    }

    @Override
    public User getUserById(String id) {
        try {
            return userDAO.getUserById(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy user: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int deleteUser(String id) {
        try {
            return userDAO.deleteUser(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi xóa user: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public Optional<User> checkLogin(String username, String password) {
        try {
            return userDAO.checkLogin(username, password);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy user: " + e.getMessage());
            return Optional.empty();
        }
    }


}
