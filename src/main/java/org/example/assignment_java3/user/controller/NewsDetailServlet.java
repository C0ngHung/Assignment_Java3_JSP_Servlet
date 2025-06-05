package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.DAO.DAOImpl.NewsDAOImpl;
import org.example.assignment_java3.DAO.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/news-detail")
public class NewsDetailServlet extends HttpServlet {

    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String id = req.getParameter("id");
        String categoryId = req.getParameter("categoryId");
        News news = newsService.getNewsById(id);
        List<News> newsList = newsService.getNewsByCategory(categoryId);
        newsService.updateViewCount(id);
        String page = "/views/pages/user/news-detail.jsp";
        req.setAttribute("news", news);
        req.setAttribute("newsList", newsList);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
