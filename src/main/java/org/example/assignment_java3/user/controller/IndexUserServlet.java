package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    private static final String HISTORY_VIEW_SESSION_KEY = "historyView";


    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);

    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // Lấy danh sách tin tức
        List<News> newsList = newsService.getAllNewsByHome();
        List<News> listTop5ViewsCount = newsService.getTop5ViewsCount();
        List<News> listTop5NewsLatest = newsService.getTop5NewsLatest();

        // Lấy danh sách tin xem gần đây bằng session
        HttpSession session = req.getSession();
        List<String> historyView = (List<String>) session.getAttribute(HISTORY_VIEW_SESSION_KEY);

        List<News> recentlyViewedNews = null;
        if (historyView != null && !historyView.isEmpty()) {
            recentlyViewedNews = newsService.getTop5NewsViewed(historyView);
        }

        String page = "/views/pages/user/news.jsp";
        req.setAttribute("newsList", newsList);
        req.setAttribute("listTop5ViewsCount", listTop5ViewsCount);
        req.setAttribute("listTop5NewsLatest", listTop5NewsLatest);
        req.setAttribute("recentlyViewedNews", recentlyViewedNews);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
