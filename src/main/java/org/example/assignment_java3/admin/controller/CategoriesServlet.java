package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.DAO.CategoryDAO;
import org.example.assignment_java3.DAO.DAOImpl.CategoryDAOImpl;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categories")
public class CategoriesServlet extends HttpServlet {

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
        String page = "/views/pages/admin/category-management.jsp";

        req.setAttribute("categoryList", categoryList);
        req.setAttribute("page", page);

        // Nếu có editId thì lấy thông tin category để hiển thị form sửa
        String editId = req.getParameter("editId");
        if (editId != null) {
            Category category = categoryService.getCategoryById(editId);
            if (category != null) {
                req.setAttribute("editCategory", category);
            } else {
                req.setAttribute("error", "Không tìm thấy danh mục có ID: " + editId);
            }
        }


        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if (action == null) {
            System.err.println("Action is null");
            doGet(req, resp);
            return;
        }

        String id = req.getParameter("id");
        String name = req.getParameter("name");

        // Validate name cho create/update
        if (("create".equals(action) || "update".equals(action)) && (name == null || name.trim().isEmpty())) {
            req.setAttribute("error", "Tên danh mục không được để trống.");
            doGet(req, resp);
            return;
        }

        switch (action) {
            case "create": {
                Category category = new Category();
                category.setName(name);
                categoryService.createCategory(category);
                req.setAttribute("success", "Tạo danh mục thành công.");
                break;
            }

            case "update": {
                if (id == null || id.isEmpty()) {
                    req.setAttribute("error", "Không tìm thấy ID danh mục để cập nhật.");
                    doGet(req, resp);
                    return;
                }
                Category category = categoryService.getCategoryById(id);
                if (category == null) {
                    req.setAttribute("error", "Danh mục không tồn tại.");
                    doGet(req, resp);
                    return;
                }
                category.setName(name);
                categoryService.updateCategory(category);
                req.setAttribute("success", "Cập nhật danh mục thành công.");
                break;
            }

            case "delete": {
                if (id == null || id.isEmpty()) {
                    req.setAttribute("error", "Không tìm thấy ID danh mục để xóa.");
                    doGet(req, resp);
                    return;
                }
                categoryService.deleteCategory(id);
                req.setAttribute("success", "Xóa danh mục thành công.");
                break;
            }

            case "reset": {
                // Không làm gì, chỉ load lại trang
                break;
            }

            default: {
                req.setAttribute("error", "Hành động không hợp lệ: " + action);
                break;
            }
        }

        // Trở về danh sách danh mục
        doGet(req, resp);
    }
}
