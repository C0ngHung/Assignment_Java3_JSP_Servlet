package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.assignment_java3.dao.CategoryDAO;
import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.dao.impl.CategoryDAOImpl;
import org.example.assignment_java3.dao.impl.NewsDAOImpl;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;
import org.example.assignment_java3.utils.ImageUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/news-management")
@MultipartConfig
public class NewsManagementServlet extends HttpServlet {

    private NewsService newsService;
    private CategoryService categoryService;

    @Override
    public void init() {
        this.newsService = new NewsServiceImpl(new NewsDAOImpl());
        this.categoryService = new CategoryServiceImpl(new CategoryDAOImpl());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<News> newsList = newsService.findAll();
        List<Category> categoryList = categoryService.findAll();

        req.setAttribute("page", "/views/pages/admin/news-management.jsp");
        req.setAttribute("newsList", newsList);
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) {
            req.setAttribute("error", "Không xác định hành động.");
            doGet(req, resp);
            return;
        }

        switch (action) {
            case "edit":
                handleEdit(req, resp);
                return;
            case "create":
                handleCreate(req, resp, user);
                break;
            case "update":
                handleUpdate(req, resp, user);
                break;
            case "delete":
                handleDelete(req, resp);
                break;
            case "reset":
                break;
            default:
                req.setAttribute("error", "Hành động không hợp lệ.");
        }

        doGet(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null) {
            req.setAttribute("error", "Thiếu ID để sửa.");
            doGet(req, resp);
            return;
        }

        Optional<News> newsOpt = newsService.findById(id);
        if (newsOpt.isEmpty()) {
            req.setAttribute("error", "Không tìm thấy tin với ID: " + id);
            doGet(req, resp);
            return;
        }

        req.setAttribute("editNews", newsOpt.get());
        req.setAttribute("newsList", newsService.findAll());
        req.setAttribute("categoryList", categoryService.findAll());
        req.setAttribute("page", "/views/pages/admin/news-management.jsp");
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    private void handleCreate(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        News news = extractNewsFromRequest(req, user);
        if (news.getTitle().isEmpty() || news.getCategoryId().isEmpty() || news.getContent().isEmpty()) {
            req.setAttribute("error", "Vui lòng điền đầy đủ thông tin.");
            return;
        }

        if (news.getId() != null && newsService.findById(news.getId()).isPresent()) {
            req.setAttribute("error", "ID đã tồn tại. Vui lòng nhập ID khác.");
            return;
        }

        newsService.insert(news);
        req.setAttribute("success", "Tạo mới tin thành công!");
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, User user) throws ServletException, IOException {
        String id = req.getParameter("id");
        Optional<News> newsOpt = newsService.findById(id);
        if (newsOpt.isEmpty()) {
            req.setAttribute("error", "Không tìm thấy bài viết với ID: " + id);
            return;
        }

        News news = newsOpt.get();
        News updatedNews = extractNewsFromRequest(req, user);

        news.setTitle(updatedNews.getTitle());
        news.setPostDate(updatedNews.getPostDate());
        news.setViewCount(updatedNews.getViewCount());
        news.setCategoryId(updatedNews.getCategoryId());
        news.setContent(updatedNews.getContent());
        news.setHome(updatedNews.isHome());
        if (updatedNews.getImage() != null) {
            news.setImage(updatedNews.getImage());
        }

        newsService.update(news);
        req.setAttribute("success", "Cập nhật tin tức thành công!");
    }

    private void handleDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            req.setAttribute("error", "Thiếu ID để xoá.");
            return;
        }
        newsService.delete(id);
        req.setAttribute("success", "Xoá bài viết thành công!");
    }

    private News extractNewsFromRequest(HttpServletRequest req, User user) throws ServletException, IOException {
        String title = req.getParameter("title");
        String postDateStr = req.getParameter("postDate");
        String viewCountStr = req.getParameter("viewCount");
        String categoryId = req.getParameter("categoryId");
        String content = req.getParameter("content");
        String home = req.getParameter("home");
        boolean homeValue = "1".equals(home) || "true".equalsIgnoreCase(home);

        int viewCount = (viewCountStr != null && !viewCountStr.isEmpty()) ? Integer.parseInt(viewCountStr) : 0;
        Date postDate;
        try {
            LocalDate localDate = LocalDate.parse(postDateStr);
            postDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            postDate = new Date();
        }

        Part imagePart = req.getPart("image");
        String imageFileName = ImageUtil.save(imagePart, getServletContext());

        News news = new News();
        news.setId(req.getParameter("id")); // optional for update
        news.setTitle(title);
        news.setPostDate(postDate);
        news.setViewCount(viewCount);
        news.setCategoryId(categoryId);
        news.setContent(content);
        news.setAuthor(user.getId());
        news.setHome(homeValue);
        if (imageFileName != null) {
            news.setImage(imageFileName);
        }

        return news;
    }
}
