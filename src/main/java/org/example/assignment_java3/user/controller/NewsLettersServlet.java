package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.dao.NewsletterDAO;
import org.example.assignment_java3.dao.impl.NewsletterDAOImpl;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.service.NewsletterService;
import org.example.assignment_java3.service.serviceImpl.NewsletterServiceImpl;

import java.io.IOException;

@WebServlet("/user/newsletter")
public class NewsLettersServlet extends HttpServlet {

    private NewsletterService newsletterService;

    @Override
    public void init() {
        NewsletterDAO newsletterDAO = new NewsletterDAOImpl();
        this.newsletterService = new NewsletterServiceImpl(newsletterDAO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy email từ form
        String email = req.getParameter("email");

        try {
            // Kiểm tra dữ liệu hợp lệ
            if (email == null || email.trim().isEmpty()) {
                req.getSession().setAttribute("message", "Vui lòng nhập email.");
            } else if (newsletterService.isEmailExists(email)) {
                req.getSession().setAttribute("message", "Email đã đăng ký nhận tin rồi.");
            } else {
                // Tạo bản ghi mới
                Newsletter newsletter = new Newsletter();
                newsletter.setEmail(email);
                newsletter.setEnable(true);

                boolean success = newsletterService.insert(newsletter);
                if (success) {
                    req.getSession().setAttribute("message", "Đăng ký nhận tin thành công.");
                } else {
                    req.getSession().setAttribute("message", "Đăng ký thất bại. Vui lòng thử lại.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("message", "Đã xảy ra lỗi khi đăng ký nhận tin.");
        }

        // Lấy đường dẫn trang trước (referer) để redirect lại
        String referer = req.getHeader("Referer");
        if (referer != null) {
            resp.sendRedirect(referer);
        } else {
            // Nếu không có, fallback về layout chính
            req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
        }
    }
}
