package org.example.assignment_java3.common.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.dao.impl.UserDAOImpl;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;
import org.example.assignment_java3.service.serviceImpl.UserServiceImpl;

import java.io.IOException;

public abstract class BaseAdminServlet extends HttpServlet {

    private UserService userService;

    /**
     * Trả về user đang đăng nhập hiện tại từ session.
     */
    protected User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("user");
    }

    /**
     * Forward trang vào layout admin và nhúng nội dung trang con.
     */
    protected void forwardToAdminLayout(HttpServletRequest req, HttpServletResponse resp, String page)
            throws ServletException, IOException {
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    /**
     * Tạo và trả về UserService (lazy load).
     */
    protected UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl(new UserDAOImpl());
        }
        return userService;
    }

    /**
     * Đặt thông báo thành công hoặc lỗi.
     */
    protected void setSuccessMessage(HttpServletRequest req, String message) {
        req.setAttribute("success", message);
    }

    protected void setErrorMessage(HttpServletRequest req, String message) {
        req.setAttribute("error", message);
    }
}
