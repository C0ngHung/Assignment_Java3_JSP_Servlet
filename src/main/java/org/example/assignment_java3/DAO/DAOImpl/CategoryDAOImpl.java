package org.example.assignment_java3.DAO.DAOImpl;

import org.example.assignment_java3.DAO.CategoryDAO;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.utils.JdbcHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public Category createCategory(Category category) {
        if (category == null) {
            return null;
        }
        String sql = "INSERT INTO category (id, name) VALUES (?, ?)";
        int row = JdbcHelper.update(sql,
                category.getId(),
                category.getName());
        return row > 0 ? category : null;
    }

    @Override
    public Category getCategoryById(String id) {
        String sql = "SELECT * FROM category WHERE id = ?";
        return JdbcHelper.query(sql, rs -> {
            if (rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                return category;
            }
            return null;
        }, id);
    }

    @Override
    public Category updateCategory(Category category) {
        String sql = "UPDATE category SET name = ? WHERE id = ?";
        int row = JdbcHelper.update(sql,
                category.getName(),
                category.getId());
        return row > 0 ? category : null;
    }

    @Override
    public int deleteCategory(String id) {
        String sql = "DELETE FROM category WHERE id = ?";
        return JdbcHelper.update(sql, id);
    }

    @Override
    public List<Category> getAllCategory() {
        String sql = "SELECT * FROM category";
        return JdbcHelper.query(sql, rs -> {
            List<Category> categoryList = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                categoryList.add(category);
            }
            return categoryList;
        });
    }
}
