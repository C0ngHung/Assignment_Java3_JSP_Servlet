package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.example.assignment_java3.DAO.DAOImpl.UserDAOImpl;
import org.example.assignment_java3.DAO.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;
import org.example.assignment_java3.service.serviceImpl.UserServiceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/reporter/profile")
public class ReporterProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() {
        UserDAO userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User reporter = (User) session.getAttribute("user");

        if (reporter == null || reporter.isRole()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String fullName = req.getParameter("fullName");
        String birthdateStr = req.getParameter("birthdate");
        String genderStr = req.getParameter("gender");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate = sdf.parse(birthdateStr);
            reporter.setBirthdate(birthdate);
        } catch (ParseException e) {
            req.setAttribute("error", "Ngày sinh không hợp lệ.");
            req.setAttribute("user", reporter);
            req.setAttribute("page", "/views/pages/reporter/profile.jsp");
            req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
            return;
        }

        reporter.setFullName(fullName);
        reporter.setGender("Nam".equals(genderStr));
        reporter.setMobile(mobile);
        reporter.setEmail(email);

        if (currentPassword != null && !currentPassword.isEmpty()
                && newPassword != null && !newPassword.isEmpty()) {

            if (!reporter.getPassword().equals(currentPassword)) {
                req.setAttribute("error", "Mật khẩu hiện tại không đúng.");
                req.setAttribute("user", reporter);
                req.setAttribute("page", "/views/pages/reporter/profile.jsp");
                req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
                return;
            }

            // Kiểm tra mật khẩu mới và xác nhận khớp
            if (!newPassword.equals(confirmPassword)) {
                req.setAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
                req.setAttribute("user", reporter);
                req.setAttribute("page", "/views/pages/reporter/profile.jsp");
                req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
                return;
            }

            reporter.setPassword(newPassword);
        }

        boolean updated = userService.updateUser(reporter);

        if (updated) {
            session.setAttribute("user", reporter);
            req.setAttribute("success", "Cập nhật thành công.");
        } else {
            req.setAttribute("error", "Có lỗi xảy ra khi cập nhật.");
        }

        req.setAttribute("user", reporter);
        req.setAttribute("page", "/views/pages/reporter/profile.jsp");
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        User reporter = (User) session.getAttribute("user");

        if (reporter == null || reporter.isRole()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("user", reporter);
        req.setAttribute("page", "/views/pages/reporter/profile.jsp");
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }
}
