package org.example.assignment_java3.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.assignment_java3.dao.CategoryDAO;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.service.CategoryService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDAO categoryDAO;

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Optional<Category> findById(String id) {
        return Optional.ofNullable(categoryDAO.findById(id));
    }

    @Override
    public boolean insert(Category category) {
        return categoryDAO.insert(category) > 0;
    }

    @Override
    public boolean update(Category category) {
        return categoryDAO.update(category) > 0;
    }

    @Override
    public boolean delete(String id) {
        return categoryDAO.delete(id) > 0;
    }

    @Override
    public boolean exists(String id) {
        return categoryDAO.exists(id);
    }
}
