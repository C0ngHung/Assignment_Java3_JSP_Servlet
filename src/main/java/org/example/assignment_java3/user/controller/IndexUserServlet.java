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

@WebServlet("/user/index")
public class IndexUserServlet extends HttpServlet {

    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String page = "/views/pages/user/news.jsp";
        List<News> newsList = newsService.getAllNewsByHome();
        List<News> listTop5ViewsCount = newsService.getTop5ViewsCount();
        List<News> listTop5NewsLatest = newsService.getTop5NewsLatest();
        req.setAttribute("newsList", newsList);
        req.setAttribute("listTop5ViewsCount", listTop5ViewsCount);
        req.setAttribute("listTop5NewsLatest", listTop5NewsLatest);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
