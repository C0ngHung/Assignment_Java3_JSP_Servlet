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

@WebServlet("/admin/index")
public class IndexAdminServlet extends BaseAdminServlet {

    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy danh sách tin tức gắn "home = true"
        List<News> newsList = newsService.getAllNewsByHome();
        req.setAttribute("newsList", newsList);

        forwardToAdminLayout(req, resp, "/views/pages/admin/news.jsp");
    }
}
