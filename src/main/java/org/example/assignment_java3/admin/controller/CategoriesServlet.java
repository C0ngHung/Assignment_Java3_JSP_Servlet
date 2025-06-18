package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseAdminServlet;
import org.example.assignment_java3.dao.CategoryDAO;
import org.example.assignment_java3.dao.impl.CategoryDAOImpl;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categories")
public class CategoriesServlet extends BaseAdminServlet {

    private CategoryService categoryService;

    @Override
    public void init() {
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        this.categoryService = new CategoryServiceImpl(categoryDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Category> categoryList = categoryService.getAllCategory();
        req.setAttribute("categoryList", categoryList);

        // Nếu có editId thì lấy thông tin category để hiển thị form sửa
        String editId = req.getParameter("editId");
        if (editId != null) {
            Category category = categoryService.getCategoryById(editId);
            if (category != null) {
                req.setAttribute("editCategory", category);
            } else {
                setErrorMessage(req, "Không tìm thấy danh mục có ID: " + editId);
            }
        }

        forwardToAdminLayout(req, resp, "/views/pages/admin/category-management.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            setErrorMessage(req, "Không xác định hành động.");
            doGet(req, resp);
            return;
        }

        String id = req.getParameter("id");
        String name = req.getParameter("name");

        if (("create".equals(action) || "update".equals(action)) && (name == null || name.trim().isEmpty())) {
            setErrorMessage(req, "Tên danh mục không được để trống.");
            doGet(req, resp);
            return;
        }

        switch (action) {
            case "create": {
                Category category = new Category();
                category.setName(name);
                categoryService.createCategory(category);
                setSuccessMessage(req, "Tạo danh mục thành công.");
                break;
            }

            case "update": {
                if (id == null || id.isEmpty()) {
                    setErrorMessage(req, "Không tìm thấy ID danh mục để cập nhật.");
                    doGet(req, resp);
                    return;
                }
                Category category = categoryService.getCategoryById(id);
                if (category == null) {
                    setErrorMessage(req, "Danh mục không tồn tại.");
                    doGet(req, resp);
                    return;
                }
                category.setName(name);
                categoryService.updateCategory(category);
                setSuccessMessage(req, "Cập nhật danh mục thành công.");
                break;
            }

            case "delete": {
                if (id == null || id.isEmpty()) {
                    setErrorMessage(req, "Không tìm thấy ID danh mục để xóa.");
                    doGet(req, resp);
                    return;
                }
                categoryService.deleteCategory(id);
                setSuccessMessage(req, "Xóa danh mục thành công.");
                break;
            }

            case "reset": {
                // Không làm gì
                break;
            }

            default: {
                setErrorMessage(req, "Hành động không hợp lệ: " + action);
                break;
            }
        }

        doGet(req, resp);
    }
}
