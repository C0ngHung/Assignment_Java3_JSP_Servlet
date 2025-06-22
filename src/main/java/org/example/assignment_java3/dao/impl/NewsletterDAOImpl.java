package org.example.assignment_java3.dao.impl;

import org.example.assignment_java3.dao.NewsletterDAO;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NewsletterDAOImpl implements NewsletterDAO {

    private static final String SQL_INSERT = "INSERT INTO newsletter (email, enable) VALUES (?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM newsletter WHERE email = ?";
    private static final String SQL_UPDATE = "UPDATE newsletter SET enable = ? WHERE email = ?";
    private static final String SQL_DELETE = "DELETE FROM newsletter WHERE email = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM newsletter";
    private static final String SQL_EXISTS = "SELECT COUNT(*) FROM newsletter WHERE email = ?";
    private static final String SQL_ENABLED = "SELECT * FROM newsletter WHERE enable = 1";

    private Newsletter map(ResultSet rs) throws SQLException {
        Newsletter n = new Newsletter();
        n.setEmail(rs.getString("email"));
        n.setEnable(rs.getBoolean("enable"));
        return n;
    }

    // BaseDAO methods

    @Override
    public int insert(Newsletter newsletter) {
        return JdbcHelper.update(SQL_INSERT, newsletter.getEmail(), newsletter.isEnable());
    }

    @Override
    public Newsletter findById(String email) {
        return JdbcHelper.query(SQL_SELECT_BY_ID, rs -> rs.next() ? map(rs) : null, email);
    }

    @Override
    public int update(Newsletter newsletter) {
        return JdbcHelper.update(SQL_UPDATE, newsletter.isEnable(), newsletter.getEmail());
    }

    @Override
    public int delete(String email) {
        return JdbcHelper.update(SQL_DELETE, email);
    }

    @Override
    public List<Newsletter> findAll() {
        return JdbcHelper.query(SQL_SELECT_ALL, rs -> {
            List<Newsletter> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }

    @Override
    public boolean exists(String email) {
        return JdbcHelper.query(SQL_EXISTS, rs -> rs.next() && rs.getInt(1) > 0, email);
    }

    // Custom methods

    @Override
    public Newsletter findByEmail(String email) {
        return findById(email); // có thể dùng lại
    }

    @Override
    public boolean isEmailExists(String email) {
        return exists(email); // tái sử dụng luôn exists()
    }

    @Override
    public List<Newsletter> findAllEnabled() {
        return JdbcHelper.query(SQL_ENABLED, rs -> {
            List<Newsletter> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }
}
