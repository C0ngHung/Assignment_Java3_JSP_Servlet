package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.common.controller.BaseReporterServlet;
import org.example.assignment_java3.entity.User;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/reporter/profile")
public class ReporterProfileServlet extends BaseReporterServlet {

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy user từ session (đã kiểm tra trong BaseReporterServlet)
        User reporter = (User) req.getSession().getAttribute("user");
        String page = "/views/pages/reporter/profile.jsp";

        req.setAttribute("user", reporter);
        forwardToAdminLayout(req, resp, page);
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy user từ session
        HttpSession session = req.getSession();
        User reporter = (User) session.getAttribute("user");

        // Lấy thông tin từ form
        String fullName = req.getParameter("fullName");
        String birthdateStr = req.getParameter("birthdate");
        String genderStr = req.getParameter("gender");
        String mobile = req.getParameter("mobile");
        String email = req.getParameter("email");

        // Cập nhật ngày sinh
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date birthdate = sdf.parse(birthdateStr);
            reporter.setBirthdate(birthdate);
        } catch (ParseException e) {
            req.setAttribute("error", "Ngày sinh không hợp lệ.");
            req.setAttribute("user", reporter);
            forwardToAdminLayout(req, resp, "/views/pages/reporter/profile.jsp");
            return;
        }

        // Cập nhật các trường cơ bản
        reporter.setFullName(fullName);
        reporter.setGender("Nam".equals(genderStr));
        reporter.setMobile(mobile);
        reporter.setEmail(email);

        // Xử lý cập nhật mật khẩu nếu có
        if (!updatePasswordIfNeeded(req, reporter)) {
            req.setAttribute("user", reporter);
            forwardToAdminLayout(req, resp, "/views/pages/reporter/profile.jsp");
            return;
        }

        // Gọi service để cập nhật
        boolean updated = userService.update(reporter);

        if (updated) {
            session.setAttribute("user", reporter); // Cập nhật lại session
            req.setAttribute("success", "Cập nhật thành công.");
        } else {
            req.setAttribute("error", "Có lỗi xảy ra khi cập nhật.");
        }

        req.setAttribute("user", reporter);
        forwardToAdminLayout(req, resp, "/views/pages/reporter/profile.jsp");
    }

    private boolean updatePasswordIfNeeded(HttpServletRequest req, User reporter) {
        String currentPassword = req.getParameter("currentPassword");
        String newPassword = req.getParameter("newPassword");
        String confirmPassword = req.getParameter("confirmPassword");

        if (currentPassword != null && !currentPassword.isEmpty()
                && newPassword != null && !newPassword.isEmpty()) {

            // So sánh mật khẩu hiện tại
            if (!reporter.getPassword().equals(currentPassword)) {
                req.setAttribute("error", "Mật khẩu hiện tại không đúng.");
                return false;
            }

            // Kiểm tra mật khẩu mới khớp nhau
            if (!newPassword.equals(confirmPassword)) {
                req.setAttribute("error", "Mật khẩu mới và xác nhận không khớp.");
                return false;
            }

            reporter.setPassword(newPassword); // Gán mật khẩu mới
        }
        return true;
    }
}
