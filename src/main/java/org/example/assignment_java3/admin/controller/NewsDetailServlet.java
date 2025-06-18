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
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        String categoryId = req.getParameter("categoryId");

        News news = newsService.getNewsById(id);
        List<News> newsList = newsService.getNewsByCategory(categoryId);

        newsService.updateViewCount(id);

        req.setAttribute("news", news);
        req.setAttribute("newsList", newsList);

        forwardToAdminLayout(req, resp, "/views/pages/admin/news-detail.jsp");
    }
}
