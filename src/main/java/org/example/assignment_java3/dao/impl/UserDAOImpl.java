package org.example.assignment_java3.dao.impl;

import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO {

    private static final String SQL_INSERT = "INSERT INTO users (id, password, fullName, birthdate, gender, mobile, email, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE users SET password = ?, fullName = ?, birthdate = ?, gender = ?, mobile = ?, email = ?, role = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM users WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM users";
    private static final String SQL_EXISTS = "SELECT COUNT(*) FROM users WHERE id = ?";
    private static final String SQL_LOGIN = "SELECT * FROM users WHERE id = ? AND password = ?";

    private User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("fullName"));
        user.setBirthdate(rs.getDate("birthdate"));
        user.setGender(rs.getBoolean("gender"));
        user.setMobile(rs.getString("mobile"));
        user.setEmail(rs.getString("email"));
        user.setRole(rs.getBoolean("role"));
        return user;
    }

    // BaseDAO methods

    @Override
    public int insert(User user) {
        return JdbcHelper.update(SQL_INSERT,
                user.getId(), user.getPassword(), user.getFullName(), user.getBirthdate(),
                user.isGender(), user.getMobile(), user.getEmail(), user.isRole());
    }

    @Override
    public User findById(String id) {
        return JdbcHelper.query(SQL_SELECT_BY_ID, rs -> rs.next() ? map(rs) : null, id);
    }

    @Override
    public int update(User user) {
        return JdbcHelper.update(SQL_UPDATE,
                user.getPassword(), user.getFullName(), user.getBirthdate(),
                user.isGender(), user.getMobile(), user.getEmail(), user.isRole(), user.getId());
    }

    @Override
    public int delete(String id) {
        return JdbcHelper.update(SQL_DELETE, id);
    }

    @Override
    public List<User> findAll() {
        return JdbcHelper.query(SQL_SELECT_ALL, rs -> {
            List<User> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }

    @Override
    public boolean exists(String id) {
        return JdbcHelper.query(SQL_EXISTS, rs -> rs.next() && rs.getInt(1) > 0, id);
    }

    // Custom method

    @Override
    public Optional<User> checkLogin(String username, String password) {
        return Optional.ofNullable(JdbcHelper.query(SQL_LOGIN, rs -> {
            if (rs.next()) return map(rs);
            return null;
        }, username, password));
    }
}
