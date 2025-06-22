package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseAdminServlet;
import org.example.assignment_java3.dao.impl.CategoryDAOImpl;
import org.example.assignment_java3.dao.impl.NewsDAOImpl;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/categories")
public class CategoriesServlet extends BaseAdminServlet {

    private CategoryService categoryService;
    private NewsService newsService;

    @Override
    public void init() {
        this.categoryService = new CategoryServiceImpl(new CategoryDAOImpl());
        this.newsService = new NewsServiceImpl(new NewsDAOImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        loadCategoryData(req);

        // Nếu có editId thì hiển thị dữ liệu lên form sửa
        String editId = req.getParameter("editId");
        if (editId != null && !editId.isBlank()) {
            categoryService.findById(editId).ifPresentOrElse(
                    category -> req.setAttribute("editCategory", category),
                    () -> setErrorMessage(req, "Không tìm thấy danh mục có ID: " + editId)
            );
        }

        forwardToAdminLayout(req, resp, "/views/pages/admin/category-management.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null || action.isBlank()) {
            setErrorMessage(req, "Không xác định hành động.");
            doGet(req, resp);
            return;
        }

        String id = req.getParameter("id");
        String name = req.getParameter("name");

        // Validate tên danh mục cho hành động create và update
        if (("create".equals(action) || "update".equals(action)) && (name == null || name.isBlank())) {
            setErrorMessage(req, "Tên danh mục không được để trống.");
            doGet(req, resp);
            return;
        }

        switch (action) {
            case "create" -> handleCreate(req, name);
            case "update" -> handleUpdate(req, id, name);
            case "delete" -> handleDelete(req, id);
            case "reset" -> {
            } // Không làm gì
            default -> setErrorMessage(req, "Hành động không hợp lệ: " + action);
        }

        doGet(req, resp);
    }

    private void loadCategoryData(HttpServletRequest req) {
        List<Category> categoryList = categoryService.findAll();
        req.setAttribute("categoryList", categoryList);
    }

    private void handleCreate(HttpServletRequest req, String name) {
        Category category = new Category();
        category.setName(name);
        categoryService.insert(category);
        setSuccessMessage(req, "Tạo danh mục thành công.");
    }

    private void handleUpdate(HttpServletRequest req, String id, String name) {
        if (id == null || id.isBlank()) {
            setErrorMessage(req, "Không tìm thấy ID danh mục để cập nhật.");
            return;
        }

        categoryService.findById(id).ifPresentOrElse(
                category -> {
                    category.setName(name);
                    categoryService.update(category);
                    setSuccessMessage(req, "Cập nhật danh mục thành công.");
                },
                () -> setErrorMessage(req, "Danh mục không tồn tại.")
        );
    }

    private void handleDelete(HttpServletRequest req, String id) {
        if (id == null || id.isBlank()) {
            setErrorMessage(req, "Không tìm thấy ID danh mục để xóa.");
            return;
        }

        // Kiểm tra xem có bài viết nào thuộc danh mục này không
        boolean isUsedInNews = newsService.existsByCategoryId(id);
        if (isUsedInNews) {
            setErrorMessage(req, "Không thể xóa danh mục vì đang có bài viết sử dụng danh mục này.");
            return;
        }

        boolean deleted = categoryService.delete(id);
        if (deleted) {
            setSuccessMessage(req, "Xóa danh mục thành công.");
        } else {
            setErrorMessage(req, "Xóa danh mục thất bại.");
        }
    }
}
