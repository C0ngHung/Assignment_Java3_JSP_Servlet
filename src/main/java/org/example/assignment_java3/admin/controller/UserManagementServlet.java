package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.dao.impl.UserDAOImpl;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;
import org.example.assignment_java3.service.serviceImpl.UserServiceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/profile")
public class UserManagementServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        UserDAO userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<User> userList = userService.getAllUsers();
        req.setAttribute("userList", userList);
        String page = "/views/pages/admin/user-management.jsp";

        String editId = req.getParameter("editId");
        if (editId != null) {
            User user = userService.getUserById(editId);
            if (user != null) {
                req.setAttribute("editUser", user);
            } else {
                req.setAttribute("error", "Không tìm thấy user có ID: " + editId);
            }
        }

        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Lấy user hiện tại từ session
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("error", "Không xác định hành động.");
            doGet(req, resp);
            return;
        }

        String id = req.getParameter("id");
        String fullName = req.getParameter("fullName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String genderStr = req.getParameter("gender");
        String roleStr = req.getParameter("role");
        String birthdateStr = req.getParameter("birthdate");

        boolean gender = "male".equalsIgnoreCase(genderStr) || "true".equalsIgnoreCase(genderStr);
        boolean role = "admin".equalsIgnoreCase(roleStr) || "true".equalsIgnoreCase(roleStr);

        Date birthdate = null;
        try {
            if (birthdateStr != null && !birthdateStr.isEmpty()) {
                birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdateStr);
            }
        } catch (ParseException e) {
            req.setAttribute("error", "Ngày sinh không hợp lệ.");
            doGet(req, resp);
            return;
        }

        switch (action) {
            case "create": {
                if (id == null || id.trim().isEmpty()) {
                    req.setAttribute("error", "ID không được để trống.");
                    break;
                }

                if (fullName == null || fullName.trim().isEmpty()) {
                    req.setAttribute("error", "Họ tên không được để trống.");
                    break;
                }

                // tạo đối tượng User mới
                User newUser = new User();
                newUser.setId(id);
                newUser.setFullName(fullName);
                newUser.setEmail(email);
                newUser.setMobile(mobile);
                newUser.setPassword(password != null ? password : "123");
                newUser.setGender(gender);
                newUser.setRole(role);
                newUser.setBirthdate(birthdate);

                User createdUser = userService.createUser(newUser);
                if (createdUser != null) {
                    req.setAttribute("success", "Tạo người dùng thành công.");
                } else {
                    req.setAttribute("error", "Tạo người dùng thất bại.");
                }
                break;
            }


            case "update": {
                if (id == null || id.isEmpty()) {
                    req.setAttribute("error", "Không tìm thấy ID người dùng để cập nhật.");
                    break;
                }

                User existingUser = userService.getUserById(id);
                if (existingUser == null) {
                    req.setAttribute("error", "Người dùng không tồn tại.");
                    break;
                }
                existingUser.setFullName(fullName);
                existingUser.setEmail(email);
                existingUser.setMobile(mobile);
                existingUser.setGender(gender);
                existingUser.setRole(role);
                existingUser.setBirthdate(birthdate);

                if (password != null && !password.trim().isEmpty()) {
                    existingUser.setPassword(password);
                }

                boolean updated = userService.updateUser(existingUser);
                if (updated) {
                    req.setAttribute("success", "Cập nhật người dùng thành công.");
                } else {
                    req.setAttribute("error", "Cập nhật người dùng thất bại.");
                }
                break;
            }

            case "delete": {
                if (id == null || id.isEmpty()) {
                    req.setAttribute("error", "Không tìm thấy ID người dùng để xóa.");
                    break;
                }
                int deleted = userService.deleteUser(id);
                if (deleted > 0) {
                    req.setAttribute("success", "Xóa người dùng thành công.");
                } else {
                    req.setAttribute("error", "Xóa người dùng thất bại.");
                }
                break;
            }

            case "reset": {
                // Không làm gì, chỉ reset form
                break;
            }

            default: {
                req.setAttribute("error", "Hành động không hợp lệ: " + action);
                break;
            }
        }

        doGet(req, resp);
    }
}
