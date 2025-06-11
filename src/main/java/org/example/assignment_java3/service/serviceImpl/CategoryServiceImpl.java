package org.example.assignment_java3.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.assignment_java3.DAO.CategoryDAO;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.service.CategoryService;

import java.util.List;

@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    @Override
    public Category createCategory(Category category) {
        return null;
    }

    @Override
    public Category getCategoryById(String id) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public int deleteCategory(String id) {
        return 0;
    }

    @Override
    public List<Category> getAllCategory() {
        try {
            return categoryDAO.getAllCategory();
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy danh sách loại tin: " + e.getMessage());
            return null;
        }
    }
}
