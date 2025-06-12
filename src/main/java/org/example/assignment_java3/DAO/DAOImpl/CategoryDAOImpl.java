package org.example.assignment_java3.DAO.DAOImpl;

import org.example.assignment_java3.DAO.CategoryDAO;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    private static final String SQL_INSERT_CATEGORY = "INSERT INTO categories (name) VALUES (?)";
    private static final String SQL_GET_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id = ?";
    private static final String SQL_UPDATE_CATEGORY = "UPDATE categories SET name = ? WHERE id = ?";
    private static final String SQL_DELETE_CATEGORY = "DELETE FROM categories WHERE id = ?";
    private static final String SQL_GET_ALL_CATEGORY = "SELECT * FROM categories";

    private Category mapCategoryFromResultSetToCategory(ResultSet rs) {
        try {
            Category category = new Category();
            category.setId(rs.getString("id"));
            category.setName(rs.getString("name"));
            return category;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Category createCategory(Category category) {
        if (category == null) {
            return null;
        }
        int row = JdbcHelper.update(SQL_INSERT_CATEGORY,
                category.getName());
        return row > 0 ? category : null;
    }

    @Override
    public Category getCategoryById(String id) {
        return JdbcHelper.query(SQL_GET_CATEGORY_BY_ID, rs -> {
            if (rs.next()) {
                return mapCategoryFromResultSetToCategory(rs);
            }
            return null;
        }, id);
    }

    @Override
    public Category updateCategory(Category category) {
        if (category == null) {
            return null;
        }
        int row = JdbcHelper.update(SQL_UPDATE_CATEGORY,
                category.getName(),
                category.getId());
        return row > 0 ? category : null;
    }

    @Override
    public int deleteCategory(String id) {
        return JdbcHelper.update(SQL_DELETE_CATEGORY, id);
    }

    @Override
    public List<Category> getAllCategory() {
        return JdbcHelper.query(SQL_GET_ALL_CATEGORY, rs -> {
            List<Category> categoryList = new ArrayList<>();
            while (rs.next()) {
                categoryList.add(mapCategoryFromResultSetToCategory(rs));
            }
            return categoryList;
        });
    }
}
