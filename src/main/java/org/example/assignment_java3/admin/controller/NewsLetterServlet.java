package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.dao.impl.NewsletterDAOImpl;
import org.example.assignment_java3.dao.NewsletterDAO;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.service.NewsletterService;
import org.example.assignment_java3.service.serviceImpl.NewsletterServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/newsletter")
public class NewsLetterServlet extends HttpServlet {

    private NewsletterService newsletterService;

    @Override
    public void init() {
        NewsletterDAO newsletterDAO = new NewsletterDAOImpl();
        this.newsletterService = new NewsletterServiceImpl(newsletterDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy danh sách newsletter
        List<Newsletter> newsletterList = newsletterService.findAll();
        req.setAttribute("newsletterList", newsletterList);

        // Xử lý hiển thị form sửa nếu có editEmail
        String editEmail = req.getParameter("editEmail");
        if (editEmail != null && !editEmail.isBlank()) {
            newsletterService.findByEmail(editEmail).ifPresentOrElse(
                    newsletter -> req.setAttribute("editNewsletter", newsletter),
                    () -> req.setAttribute("error", "Không tìm thấy email: " + editEmail)
            );
        }

        // Hiển thị thông báo nếu có (sau redirect)
        HttpSession session = req.getSession();
        transferFlashMessage(session, req, "success");
        transferFlashMessage(session, req, "error");

        req.setAttribute("page", "/views/pages/admin/newsletter-management.jsp");
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        String action = req.getParameter("action");
        String email = req.getParameter("email");
        String enableParam = req.getParameter("enable"); // "1" or "0"

        if (action == null) {
            session.setAttribute("error", "Không xác định hành động.");
            resp.sendRedirect(req.getContextPath() + "/admin/newsletter");
            return;
        }

        switch (action) {
            case "create" -> handleCreate(session, email);
            case "update" -> handleUpdate(session, email, enableParam);
            case "delete" -> handleDelete(session, email);
            case "reset" -> {
                // Không xử lý gì cả
            }
            default -> session.setAttribute("error", "Hành động không hợp lệ: " + action);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/newsletter");
    }

    private void handleCreate(HttpSession session, String email) {
        if (email == null || email.trim().isEmpty()) {
            session.setAttribute("error", "Email không được để trống.");
            return;
        }

        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
            session.setAttribute("error", "Email không hợp lệ.");
            return;
        }

        if (newsletterService.exists(email)) {
            session.setAttribute("error", "Email đã tồn tại.");
            return;
        }

        Newsletter newsletter = new Newsletter();
        newsletter.setEmail(email);
        newsletter.setEnable(true);
        newsletterService.insert(newsletter);
        session.setAttribute("success", "Thêm email thành công.");
    }

    private void handleUpdate(HttpSession session, String email, String enableParam) {
        if (email == null || email.trim().isEmpty()) {
            session.setAttribute("error", "Không có email để cập nhật.");
            return;
        }

        var optionalNewsletter = newsletterService.findByEmail(email);
        if (optionalNewsletter.isEmpty()) {
            session.setAttribute("error", "Không tìm thấy email: " + email);
            return;
        }

        Newsletter newsletter = optionalNewsletter.get();
        boolean enable = "1".equals(enableParam);
        newsletter.setEnable(enable);
        newsletterService.update(newsletter);
        session.setAttribute("success", "Cập nhật trạng thái thành công.");
    }

    private void handleDelete(HttpSession session, String email) {
        if (email == null || email.trim().isEmpty()) {
            session.setAttribute("error", "Không có email để xóa.");
            return;
        }

        newsletterService.delete(email);
        session.setAttribute("success", "Xóa email thành công.");
    }

    private void transferFlashMessage(HttpSession session, HttpServletRequest req, String key) {
        Object message = session.getAttribute(key);
        if (message != null) {
            req.setAttribute(key, message);
            session.removeAttribute(key);
        }
    }
}
