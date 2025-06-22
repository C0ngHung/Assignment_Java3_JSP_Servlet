package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.dao.impl.NewsDAOImpl;
import org.example.assignment_java3.dao.impl.UserDAOImpl;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.UserService;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;
import org.example.assignment_java3.service.serviceImpl.UserServiceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/admin/profile")
public class UserManagementServlet extends HttpServlet {

    private UserService userService;
    private NewsService newsService;

    @Override
    public void init() {
        userService = new UserServiceImpl(new UserDAOImpl());
        newsService = new NewsServiceImpl(new NewsDAOImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setAttribute("userList", userService.findAll());

        String editId = req.getParameter("editId");
        if (editId != null) {
            userService.findById(editId).ifPresentOrElse(
                    user -> req.setAttribute("editUser", user),
                    () -> req.setAttribute("error", "Không tìm thấy người dùng có ID: " + editId)
            );
        }

        req.setAttribute("page", "/views/pages/admin/user-management.jsp");
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("error", "Không xác định hành động.");
            doGet(req, resp);
            return;
        }

        switch (action) {
            case "create" -> handleCreate(req);
            case "update" -> handleUpdate(req);
            case "delete" -> handleDelete(req);
            case "reset" -> {
            } // Do nothing
            default -> req.setAttribute("error", "Hành động không hợp lệ: " + action);
        }

        doGet(req, resp);
    }

    private void handleCreate(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            req.setAttribute("error", "ID không được để trống.");
            return;
        }

        if (userService.findById(id).isPresent()) {
            req.setAttribute("error", "ID người dùng đã tồn tại.");
            return;
        }

        User user = extractUserFromRequest(req);
        if (user.getFullName() == null || user.getFullName().isBlank()) {
            req.setAttribute("error", "Họ tên không được để trống.");
            return;
        }

        if (user.getPassword() == null || user.getPassword().isBlank()) {
            user.setPassword("123");
        }

        boolean success = userService.insert(user);
        req.setAttribute(success ? "success" : "error",
                success ? "Tạo người dùng thành công." : "Tạo người dùng thất bại.");
    }

    private void handleUpdate(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            req.setAttribute("error", "Không tìm thấy ID người dùng để cập nhật.");
            return;
        }

        Optional<User> existing = userService.findById(id);
        if (existing.isEmpty()) {
            req.setAttribute("error", "Người dùng không tồn tại.");
            return;
        }

        User user = existing.get();
        User updated = extractUserFromRequest(req);

        user.setFullName(updated.getFullName());
        user.setEmail(updated.getEmail());
        user.setMobile(updated.getMobile());
        user.setGender(updated.isGender());
        user.setRole(updated.isRole());
        user.setBirthdate(updated.getBirthdate());

        if (updated.getPassword() != null && !updated.getPassword().isBlank()) {
            user.setPassword(updated.getPassword());
        }

        boolean success = userService.update(user);
        req.setAttribute(success ? "success" : "error",
                success ? "Cập nhật người dùng thành công." : "Cập nhật người dùng thất bại.");
    }

    private void handleDelete(HttpServletRequest req) {
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            req.setAttribute("error", "Không tìm thấy ID người dùng để xoá.");
            return;
        }

        // Kiểm tra xem user có đang là tác giả của bài viết nào không
        boolean isUsedAsAuthor = newsService.existsByAuthorId(id);
        if (isUsedAsAuthor) {
            req.setAttribute("error", "Không thể xoá người dùng vì đang là tác giả của một hoặc nhiều bài viết.");
            return;
        }

        boolean success = userService.delete(id);
        req.setAttribute(success ? "success" : "error",
                success ? "Xoá người dùng thành công." : "Xoá người dùng thất bại.");
    }


    private User extractUserFromRequest(HttpServletRequest req) {
        User user = new User();
        user.setId(req.getParameter("id"));
        user.setFullName(req.getParameter("fullName"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setMobile(req.getParameter("mobile"));
        user.setGender(parseBooleanParam(req.getParameter("gender"), "male"));
        user.setRole(parseBooleanParam(req.getParameter("role"), "admin"));
        user.setBirthdate(parseDateParam(req.getParameter("birthdate")));
        return user;
    }

    private boolean parseBooleanParam(String value, String expectedTrue) {
        return expectedTrue.equalsIgnoreCase(value) || "true".equalsIgnoreCase(value);
    }

    private Date parseDateParam(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(value);
        } catch (ParseException e) {
            return null;
        }
    }
}
