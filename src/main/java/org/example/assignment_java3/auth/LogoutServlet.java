package org.example.assignment_java3.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1. Xóa session hiện tại
        HttpSession session = req.getSession(false); // false = không tạo mới nếu chưa có
        if (session != null) {
            session.invalidate(); // Hủy session
        }

        // 2. Xóa cookie 'user' nếu có (ghi nhớ đăng nhập)
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    cookie.setMaxAge(0); // Đặt thời gian hủy cookie
                    cookie.setPath("/"); // Đảm bảo đúng path để ghi đè cookie
                    resp.addCookie(cookie);
                    break;
                }
            }
        }

        // 3. Chuyển về trang login
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
