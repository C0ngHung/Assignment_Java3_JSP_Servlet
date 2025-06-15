package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.assignment_java3.common.controller.BaseReporterServlet;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.utils.ImageUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@WebServlet("/reporter/news-management")
@MultipartConfig
public class NewsManagementServlet extends BaseReporterServlet {

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Lấy user hiện tại từ session
        User user = (User) req.getSession().getAttribute("user");
        String author = user.getId();
        List<News> newsList = newsService.getNewsByAuthor(author);
        List<Category> categoryList = categoryService.getAllCategory();
        String page = "/views/pages/reporter/news-management.jsp";

        req.setAttribute("newsList", newsList);
        req.setAttribute("categoryList", categoryList);
        forwardToAdminLayout(req, resp, page);
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Lấy user hiện tại từ session
        User user = (User) req.getSession().getAttribute("user");
        String author = user.getId();
        String action = req.getParameter("action");

        // Kiểm tra action null
        if (action == null) {
            System.err.println("Action parameter is null");
            processGet(req, resp);
            return;
        }

        // Xử lý action edit
        if ("edit".equals(action)) {
            String id = req.getParameter("id");
            if (id == null) {
                System.err.println("ID parameter is null for edit action");
                processGet(req, resp);
                return;
            }
            News news = newsService.getNewsById(id);
            if (news == null) {
                System.err.println("News not found for ID: " + id);
                processGet(req, resp);
                return;
            }
            List<Category> categoryList = categoryService.getAllCategory();
            List<News> newsList = newsService.getNewsByAuthor(author);
            String page = "/views/pages/reporter/news-management.jsp";

            req.setAttribute("editNews", news);
            req.setAttribute("categoryList", categoryList);
            req.setAttribute("newsList", newsList);
            forwardToAdminLayout(req, resp, page);
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
        int viewCount = (viewCountStr != null && !viewCountStr.isEmpty()) ? Integer.parseInt(viewCountStr) : 0;

        // Kiểm tra các tham số bắt buộc cho create/update
        if (("create".equals(action) || "update".equals(action)) && (title == null || title.isEmpty() || categoryId == null || categoryId.isEmpty() || content == null || content.isEmpty())) {
            System.err.println("Missing required parameters: title=" + title + ", categoryId=" + categoryId + ", content=" + content);
            req.setAttribute("error", "Vui lòng điền đầy đủ tiêu đề, loại tin và nội dung.");
            processGet(req, resp);
            return;
        }

        // Xử lý file ảnh
        Part imagePart = req.getPart("image");
        String imageFileName = ImageUtil.save(imagePart, getServletContext());

        switch (action) {
            case "create": {
                News news = new News();
                news.setTitle(title);
                news.setPostDate(postDate);
                news.setViewCount(viewCount);
                news.setCategoryId(categoryId);
                news.setContent(content);
                news.setAuthor(author);
                if (imageFileName != null) {
                    news.setImage(imageFileName);
                }
                newsService.createNews(news);
                break;
            }
            case "update": {
                if (id == null || id.isEmpty()) {
                    System.err.println("ID parameter is null or empty for update action");
                    req.setAttribute("error", "Không tìm thấy ID bài viết để cập nhật.");
                    processGet(req, resp);
                    return;
                }
                News news = newsService.getNewsById(id);
                if (news == null) {
                    System.err.println("News not found for ID: " + id);
                    req.setAttribute("error", "Không tìm thấy bài viết với ID: " + id);
                    processGet(req, resp);
                    return;
                }
                news.setTitle(title);
                news.setPostDate(postDate);
                news.setViewCount(viewCount);
                news.setCategoryId(categoryId);
                news.setContent(content);
                news.setAuthor(author);
                if (imageFileName != null) {
                    news.setImage(imageFileName);
                }
                newsService.updateNews(news);
                break;
            }
            case "delete": {
                if (id == null || id.isEmpty()) {
                    System.err.println("ID parameter is null or empty for delete action");
                    req.setAttribute("error", "Không tìm thấy ID bài viết để xóa.");
                    processGet(req, resp);
                    return;
                }
                newsService.deleteNews(id);
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
        processGet(req, resp);
    }
}