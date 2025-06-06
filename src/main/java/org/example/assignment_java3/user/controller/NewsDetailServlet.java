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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/news-detail")
public class NewsDetailServlet extends HttpServlet {

    private NewsService newsService;
    private static final int MAX_HISTORY_SIZE = 5;
    private static final String HISTORY_VIEW_SESSION_KEY = "historyView";

    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);

    }

    private void updateHistoryView(HttpSession session, String newsId) {
        // Lưu lịch sử xem tin gần đây bằng session
        /*
        * Đầu tiên em tạo session bằng req.getSession()
        * Tiếp theo em tạo list lịch sử xem bằng list historyView = (List<String>) session.getAttribute("historyView");
        * Nếu chưa có historyView thì em tạo ra 1 ArrayList để chứa nó
        * Nếu có rồi thì em remove id đi để tránh trùng lặp và sau đó thêm lại vào đầu list
        * Ví dụ cụ thể historyView = ["news1", "news2", "news3"] nếu guest xem news2
        * thì historyView.remove("news2"); ["news1", "news3"] historyView.add(0, "news2"); kết quả historyView = ["news2", "news1", "news3"]
        * Trường hợp chưa có trong list ví dụ: historyView = ["news1", "news3"] id = "news2" thì
        * historyView.remove("news2") không thay đổi list; historyView.add(0, "news2"); kết quả historyView = ["news2", "news1", "news3"]*/
        List<String> historyView = (List<String>) session.getAttribute(HISTORY_VIEW_SESSION_KEY);
        if (historyView == null) {
            historyView = new ArrayList<>();
        }
        historyView.remove(newsId);

        historyView.add(0, newsId);

        if (historyView.size() > MAX_HISTORY_SIZE) {
            historyView.remove(historyView.size() - 1);
        }
        session.setAttribute("historyView", historyView);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        // Lấy id và categoryId trên url từ param
        String id = req.getParameter("id");
        String categoryId = req.getParameter("categoryId");

        // Gọi service để lấy thông tin tin tức
        News news = newsService.getNewsById(id);
        List<News> newsList = newsService.getNewsByCategory(categoryId);
        List<News> listTop5ViewsCount = newsService.getTop5ViewsCount();
        List<News> listTop5NewsLatest = newsService.getTop5NewsLatest();

        // Cập nhật lượt xem cho tin tức
        newsService.updateViewCount(id);

        // Cập nhật lịch sử lượt xem cho người dùng
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(30);
        updateHistoryView(session, id);

        // Lấy ra danh sách tin gần đây bằng session
        List<String> historyView = (List<String>) session.getAttribute(HISTORY_VIEW_SESSION_KEY);
        List<News> recentlyViewedNews = newsService.getTop5NewsViewed(historyView);

        String page = "/views/pages/user/news-detail.jsp";
        req.setAttribute("news", news);
        req.setAttribute("newsList", newsList);
        req.setAttribute("listTop5ViewsCount", listTop5ViewsCount);
        req.setAttribute("listTop5NewsLatest", listTop5NewsLatest);
        req.setAttribute("recentlyViewedNews", recentlyViewedNews);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
