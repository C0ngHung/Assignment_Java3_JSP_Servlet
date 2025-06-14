package org.example.assignment_java3.common.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.dao.CategoryDAO;
import org.example.assignment_java3.dao.impl.CategoryDAOImpl;
import org.example.assignment_java3.dao.impl.NewsDAOImpl;
import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;


import java.util.ArrayList;
import java.util.List;

public abstract class BaseNewsServlet extends HttpServlet {
    // Khai báo các biến
    protected NewsService newsService;
    protected CategoryService categoryService;
    private static final int MAX_HISTORY_SIZE = 5;
    protected static final String HISTORY_VIEW_SESSION_KEY = "historyView";

    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        this.categoryService = new CategoryServiceImpl(categoryDAO);
    }

    protected void addCommonNewsAttributes(HttpServletRequest req) {
        // Lấy danh sách tin xem gần đây bằng session
        HttpSession session = req.getSession();
        List<String> historyView = (List<String>) session.getAttribute(HISTORY_VIEW_SESSION_KEY);

        /**
         * Nếu danh sách tin xem gần đây trống
         * Nếu lịch sử xem khác null và không rỗng thì
         * lịch sử xem gần đây = service.getTop5NewsViewed(lịch sử xem gần đây)
         */
        List<News> recentlyViewedNews = null;
        if (historyView != null && !historyView.isEmpty()) {
            recentlyViewedNews = newsService.getTop5NewsViewed(historyView);
        }
        req.setAttribute("listTop5ViewsCount", newsService.getTop5ViewsCount());
        req.setAttribute("listTop5NewsLatest", newsService.getTop5NewsLatest());
        req.setAttribute("recentlyViewedNews", recentlyViewedNews);
    }

    protected void updateHistoryView(HttpSession session, String newsId) {
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
        session.setAttribute(HISTORY_VIEW_SESSION_KEY, historyView);
    }

}
