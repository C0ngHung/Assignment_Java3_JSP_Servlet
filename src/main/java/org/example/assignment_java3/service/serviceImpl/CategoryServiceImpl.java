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
        try {
            return categoryDAO.createCategory(category);
        } catch (Exception e) {
            System.out.println("Lỗi khi tạo loại tin: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Category getCategoryById(String id) {
        try {
            return categoryDAO.getCategoryById(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy loại tin: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Category updateCategory(Category category) {
        try {
            return categoryDAO.updateCategory(category);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi cap nhật loại tin: " + e.getMessage());
            return null;
        }
    }

    @Override
    public int deleteCategory(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.err.println("ID loại tin không hợp lệ khi xóa.");
            return 0;
        }

        try {
            return categoryDAO.deleteCategory(id);
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa loại tin với ID: " + id + " - " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
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
