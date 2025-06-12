package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.common.controller.BaseNewsServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/news-detail")
public class NewsDetailServlet extends BaseNewsServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // Lấy id và categoryId trên url từ param
        String id = req.getParameter("id");
        String categoryId = req.getParameter("categoryId");

        // Gọi service để lấy thông tin tin tức
        News news = newsService.getNewsById(id);
        List<News> newsList = newsService.getNewsByCategory(categoryId);

        // Cập nhật lượt xem cho tin tức
        newsService.updateViewCount(id);

        // Cập nhật lịch sử lượt xem cho người dùng
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(30);
        updateHistoryView(session, id);

        // Khai báo trang news-detail.jsp để hiển thị dữ liệu
        String page = "/views/pages/admin/news-detail.jsp";

        // Add Lấy Attribute chung cho trang news-detail.jsp
        addCommonNewsAttributes(req);

        req.setAttribute("news", news);
        req.setAttribute("newsList", newsList);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }
}
