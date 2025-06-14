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
    public void init() throws ServletException {
        NewsletterDAO newsletterDAO = new NewsletterDAOImpl();
        this.newsletterService = new NewsletterServiceImpl(newsletterDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy danh sách newsletter
        List<Newsletter> newsletterList = newsletterService.getAllNewsletter();
        req.setAttribute("newsletterList", newsletterList);

        // Nếu có editEmail thì gán vào form
        String editEmail = req.getParameter("editEmail");
        System.out.println(editEmail);
        if (editEmail != null) {
            Newsletter newsletter = newsletterService.getNewsletterEmail(editEmail);
            if (newsletter != null) {
                req.setAttribute("editNewsletter", newsletter);
            } else {
                req.setAttribute("error", "Không tìm thấy email: " + editEmail);
            }
        }

        // Hiển thị thông báo nếu có (sau redirect)
        HttpSession session = req.getSession();
        String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        if (success != null) {
            req.setAttribute("success", success);
            session.removeAttribute("success");
        }
        if (error != null) {
            req.setAttribute("error", error);
            session.removeAttribute("error");
        }

        req.setAttribute("page", "/views/pages/admin/newsletter.jsp");
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
            case "create": {
                if (email == null || email.trim().isEmpty()) {
                    session.setAttribute("error", "Email không được để trống.");
                    break;
                }

                // Kiểm tra định dạng email
                if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
                    session.setAttribute("error", "Email không hợp lệ.");
                    break;
                }

                Newsletter existing = newsletterService.getNewsletterEmail(email);
                if (existing != null) {
                    session.setAttribute("error", "Email này đã tồn tại.");
                    break;
                }

                Newsletter newNewsletter = new Newsletter();
                newNewsletter.setEmail(email);
                newNewsletter.setEnable(true); // mặc định enable
                newsletterService.createNewsletter(newNewsletter);
                session.setAttribute("success", "Thêm email thành công.");
                break;
            }

            case "update": {
                if (email == null || email.trim().isEmpty()) {
                    session.setAttribute("error", "Không có email để cập nhật.");
                    break;
                }

                Newsletter existingNewsletter = newsletterService.getNewsletterEmail(email);
                if (existingNewsletter == null) {
                    session.setAttribute("error", "Không tìm thấy email để cập nhật.");
                    break;
                }

                boolean enable = "1".equals(enableParam);
                existingNewsletter.setEnable(enable);
                newsletterService.updateNewsletter(existingNewsletter);
                session.setAttribute("success", "Cập nhật trạng thái thành công.");
                break;
            }

            case "delete": {
                if (email == null || email.trim().isEmpty()) {
                    session.setAttribute("error", "Không có email để xóa.");
                    break;
                }

                newsletterService.deleteNewsletter(email);
                session.setAttribute("success", "Xoá email thành công.");
                break;
            }

            case "reset": {
                // Không xử lý gì cả
                break;
            }

            default: {
                session.setAttribute("error", "Hành động không hợp lệ: " + action);
                break;
            }
        }

        // Redirect sau xử lý POST
        resp.sendRedirect(req.getContextPath() + "/admin/newsletter");
    }
}
