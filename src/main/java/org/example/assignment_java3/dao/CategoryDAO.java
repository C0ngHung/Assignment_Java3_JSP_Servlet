package org.example.assignment_java3.dao;

import org.example.assignment_java3.entity.Category;

import java.util.List;

public interface CategoryDAO {

    Category createCategory(Category category);

    Category getCategoryById(String id);

    Category updateCategory(Category category);

    int deleteCategory(String id);

    List<Category> getAllCategory();
}
