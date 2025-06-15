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
        String email = req.getParameter("email");

        if (email == null || email.trim().isEmpty()) {
            req.getSession().setAttribute("message", "Vui lòng nhập email.");
        } else if (newsletterService.isEmailExists(email)) {
            req.getSession().setAttribute("message", "Email đã đăng ký nhận tin rồi.");
        } else {
            Newsletter newsletter = new Newsletter();
            newsletter.setEmail(email);
            newsletter.setEnable(true);

            newsletterService.createNewsletter(newsletter);

            req.getSession().setAttribute("message", "Đăng ký nhận tin thành công.");
        }

        // Lấy đường dẫn trang trước (referer) để forward lại
        String referer = req.getHeader("Referer");
        if (referer != null) {
            resp.sendRedirect(referer);
        } else {
            req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
        }
    }
}
