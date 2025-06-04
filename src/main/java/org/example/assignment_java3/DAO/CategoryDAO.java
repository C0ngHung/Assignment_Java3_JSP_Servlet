package org.example.assignment_java3.DAO;

import org.example.assignment_java3.entity.Category;

import java.util.List;

public interface CategoryDAO {
    Category createCategory(Category category);

    Category getCategoryById(int id);

    Category updateCategory(Category category);

    int deleteCategory(String id);

    List<Category> getAllCategory();
}
