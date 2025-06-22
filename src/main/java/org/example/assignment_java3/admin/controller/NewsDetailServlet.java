package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseAdminServlet;
import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.dao.impl.NewsDAOImpl;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/news-detail")
public class NewsDetailServlet extends BaseAdminServlet {

    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        this.newsService = new NewsServiceImpl(new NewsDAOImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy id và categoryId từ URL
        String id = req.getParameter("id");
        String categoryId = req.getParameter("categoryId");

        // Lấy bài viết chi tiết
        News news = newsService.findById(id).orElse(null);

        // Lấy danh sách bài viết cùng chuyên mục
        List<News> newsList = newsService.getNewsByCategory(categoryId);

        // Cập nhật lượt xem bài viết
        newsService.updateViewCount(id);

        // Đặt thuộc tính để hiển thị trên trang JSP
        req.setAttribute("news", news);
        req.setAttribute("newsList", newsList);

        // Forward đến layout admin
        forwardToAdminLayout(req, resp, "/views/pages/admin/news-detail.jsp");
    }
}
