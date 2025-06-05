package org.example.assignment_java3.DAO.DAOImpl;

import org.example.assignment_java3.DAO.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.utils.JdbcHelper;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User getUserById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return JdbcHelper.query(sql, rs -> {
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setPassword(rs.getString("password"));
                user.setFullName(rs.getString("fullName"));
                user.setBirthday(rs.getDate("birthday"));
                user.setGender(rs.getBoolean("gender"));
                user.setMobile(rs.getString("mobile"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getBoolean("role"));
                return user;
            }
            return null;
        }, id);
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public User deleteUser(String id) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return List.of();
    }
}
