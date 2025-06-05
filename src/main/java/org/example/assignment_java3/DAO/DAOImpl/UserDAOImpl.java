package org.example.assignment_java3.DAO.DAOImpl;

import org.example.assignment_java3.DAO.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final String SQL_INSERT_USER = "INSERT INTO users (id, password, fullName, birthday, gender, mobile, email, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_UPDATE_USER = "UPDATE users SET password = ?, fullName = ?, birthday = ?, gender = ?, mobile = ?, email = ?, role = ? WHERE id = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM users";


    private User mapUserFromResultSetToUser(ResultSet rs) {
        try {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User createUser(User user) {
        if (user == null) {
            return null;
        }
        int row = JdbcHelper.update(SQL_INSERT_USER,
                user.getId(),
                user.getPassword(),
                user.getFullName(),
                user.getBirthday(),
                user.isGender(),
                user.getMobile(),
                user.getEmail(),
                user.isRole());
        return row > 0 ? user : null;
    }

    @Override
    public User getUserById(String id) {
        return JdbcHelper.query(SQL_GET_USER_BY_ID, rs -> {
            if (rs.next()) {
                return mapUserFromResultSetToUser(rs);
            }
            return null;
        }, id);
    }

    @Override
    public User updateUser(User user) {
        if (user == null) {
            return null;
        }
        int row = JdbcHelper.update(SQL_UPDATE_USER,
                user.getPassword(),
                user.getFullName(),
                user.getBirthday(),
                user.isGender(),
                user.getMobile(),
                user.getEmail(),
                user.isRole(),
                user.getId());
        return row > 0 ? user : null;
    }

    @Override
    public int deleteUser(String id) {
        return JdbcHelper.update(SQL_DELETE_USER, id);
    }

    @Override
    public List<User> getAllUsers() {
        return JdbcHelper.query(SQL_GET_ALL_USERS, rs -> {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                userList.add(mapUserFromResultSetToUser(rs));
            }
            return userList;
        });
    }
}
