package org.example.assignment_java3.dao.impl;

import org.example.assignment_java3.dao.CategoryDAO;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private static final String SQL_INSERT = "INSERT INTO categories (name) VALUES (?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM categories WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE categories SET name = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM categories WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM categories";
    private static final String SQL_EXISTS = "SELECT COUNT(*) FROM categories WHERE id = ?";

    private Category map(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getString("id"));
        category.setName(rs.getString("name"));
        return category;
    }

    @Override
    public List<Category> findAll() {
        return JdbcHelper.query(SQL_SELECT_ALL, rs -> {
            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                list.add(map(rs));
            }
            return list;
        });
    }

    @Override
    public Category findById(String id) {
        return JdbcHelper.query(SQL_SELECT_BY_ID, rs -> {
            if (rs.next()) {
                return map(rs);
            }
            return null;
        }, id);
    }

    @Override
    public int insert(Category category) {
        return JdbcHelper.update(SQL_INSERT, category.getName());
    }

    @Override
    public int update(Category category) {
        return JdbcHelper.update(SQL_UPDATE, category.getName(), category.getId());
    }

    @Override
    public int delete(String id) {
        return JdbcHelper.update(SQL_DELETE, id);
    }

    @Override
    public boolean exists(String id) {
        return JdbcHelper.query(SQL_EXISTS, rs -> {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        }, id);
    }

    // (Optional) Nếu bạn muốn có thêm phương thức mở rộng:
    @Override
    public List<Category> searchByName(String keyword) {
        String sql = "SELECT * FROM categories WHERE name LIKE ?";
        return JdbcHelper.query(sql, rs -> {
            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                list.add(map(rs));
            }
            return list;
        }, "%" + keyword + "%");
    }
}
