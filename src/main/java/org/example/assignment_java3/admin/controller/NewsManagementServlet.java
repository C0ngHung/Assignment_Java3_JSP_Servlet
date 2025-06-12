package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.assignment_java3.DAO.CategoryDAO;
import org.example.assignment_java3.DAO.DAOImpl.CategoryDAOImpl;
import org.example.assignment_java3.DAO.DAOImpl.NewsDAOImpl;
import org.example.assignment_java3.DAO.NewsDAO;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@WebServlet("/admin/news-management")
@MultipartConfig
public class NewsManagementServlet extends HttpServlet {

    private NewsService newsService;
    private CategoryService categoryService;

    @Override
    public void init() {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        this.categoryService = new CategoryServiceImpl(categoryDAO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy user hiện tại từ session
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String author = user.getId();
        List<News> newsList = newsService.getAllNews();
        List<Category> categoryList = categoryService.getAllCategory();
        String page = "/views/pages/admin/news-management.jsp";

        req.setAttribute("page", page);
        req.setAttribute("newsList", newsList);
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy user hiện tại từ session
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String author = user.getId();
        String action = req.getParameter("action");

        // Kiểm tra action null
        if (action == null) {
            System.err.println("Action parameter is null");
            doGet(req, resp);
            return;
        }

        // Xử lý action edit
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id == null) {
                System.err.println("ID parameter is null for edit action");
                doGet(req, resp);
                return;
            }
            News news = newsService.getNewsById(id);
            if (news == null) {
                System.err.println("News not found for ID: " + id);
                doGet(req, resp);
                return;
            }
            List<Category> categoryList = categoryService.getAllCategory();
            List<News> newsList = newsService.getAllNews();
            String page = "/views/pages/admin/news-management.jsp";

            req.setAttribute("editNews", news);
            req.setAttribute("categoryList", categoryList);
            req.setAttribute("newsList", newsList);
            req.setAttribute("page", page);
            req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
            return;
        }

        // Common fields
        String id = req.getParameter("id");
        String title = req.getParameter("title");
        Date postDate;
        try {
            String postDateStr = req.getParameter("postDate");
            if (postDateStr != null && !postDateStr.isEmpty()) {
                LocalDate localDate = LocalDate.parse(postDateStr);
                postDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            } else {
                postDate = new Date();
            }
        } catch (Exception e) {
            postDate = new Date();
            System.err.println("Error parsing postDate: " + e.getMessage());
        }

        String viewCountStr = req.getParameter("viewCount");
        String categoryId = req.getParameter("categoryId");
        String content = req.getParameter("content");
        String home = req.getParameter("home");
        boolean homeValue = false;
        if (home != null) {
            homeValue = "1".equals(home) || "true".equalsIgnoreCase(home);
        }
        int viewCount = (viewCountStr != null && !viewCountStr.isEmpty()) ? Integer.parseInt(viewCountStr) : 0;

        // Kiểm tra các tham số bắt buộc cho create/update
        if (("create".equals(action) || "update".equals(action)) && (title == null || title.isEmpty() || categoryId == null || categoryId.isEmpty() || content == null || content.isEmpty())) {
            System.err.println("Missing required parameters: title=" + title + ", categoryId=" + categoryId + ", content=" + content);
            req.setAttribute("error", "Vui lòng điền đầy đủ tiêu đề, loại tin và nội dung.");
            doGet(req, resp);
            return;
        }

        // Xử lý file ảnh
        Part imagePart = req.getPart("image");
        String imageFileName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            String uploadPath = getServletContext().getRealPath("/images");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            String fullPath = uploadPath + File.separator + fileName;
            imagePart.write(fullPath);
            imageFileName = fileName;
        }

        switch (action) {
            case "create": {
                News news = new News();
                news.setTitle(title);
                news.setPostDate(postDate);
                news.setViewCount(viewCount);
                news.setCategoryId(categoryId);
                news.setContent(content);
                news.setAuthor(author);
                news.setHome(homeValue);
                if (imageFileName != null) {
                    news.setImage(imageFileName);
                }
                newsService.createNews(news);
                req.setAttribute("success", "Tạo mới tin thành công!");
                break;
            }
            case "update": {
                if (id == null || id.isEmpty()) {
                    System.err.println("ID parameter is null or empty for update action");
                    req.setAttribute("error", "Không tìm thấy ID bài viết để cập nhật.");
                    doGet(req, resp);
                    return;
                }
                News news = newsService.getNewsById(id);
                if (news == null) {
                    System.err.println("News not found for ID: " + id);
                    req.setAttribute("error", "Không tìm thấy bài viết với ID: " + id);
                    doGet(req, resp);
                    return;
                }
                news.setTitle(title);
                news.setPostDate(postDate);
                news.setViewCount(viewCount);
                news.setCategoryId(categoryId);
                news.setContent(content);
                news.setAuthor(author);
                news.setHome(homeValue);
                if (imageFileName != null) {
                    news.setImage(imageFileName);
                }
                newsService.updateNews(news);
                req.setAttribute("success", "Cập nhật tin thức thành công");
                break;
            }
            case "delete": {
                if (id == null || id.isEmpty()) {
                    System.err.println("ID parameter is null or empty for delete action");
                    req.setAttribute("error", "Không tìm thấy ID bài viết để xóa.");
                    doGet(req, resp);
                    return;
                }
                newsService.deleteNews(id);
                req.setAttribute("success", "Xóa tin thức thành công");
                break;
            }
            case "reset": {
                // Không làm gì, chỉ reload trang
                break;
            }
            default: {
                System.err.println("Invalid action: " + action);
                req.setAttribute("error", "Hành động không hợp lệ: " + action);
                break;
            }
        }

        // Quay lại danh sách
        doGet(req, resp);
    }
}