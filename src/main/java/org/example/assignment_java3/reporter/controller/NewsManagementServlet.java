package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.example.assignment_java3.common.controller.BaseReporterServlet;
import org.example.assignment_java3.config.AppConfigReader;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.utils.ImageUtil;
import org.example.assignment_java3.utils.Mailer;

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

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String author = user.getId();
        List<News> newsList = newsService.getNewsByAuthor(author);
        List<Category> categoryList = categoryService.findAll();

        req.setAttribute("newsList", newsList);
        req.setAttribute("categoryList", categoryList);

        forwardToAdminLayout(req, resp, "/views/pages/reporter/news-management.jsp");
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        String author = user.getId();
        String action = req.getParameter("action");

        if (action == null) {
            req.setAttribute("error", "Không có hành động nào được gửi.");
            processGet(req, resp);
            return;
        }

        // Xử lý action "edit"
        if ("edit".equals(action)) {
            handleEdit(req, resp, author);
            return;
        }

        // Dữ liệu chung
        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String viewCountStr = req.getParameter("viewCount");
        String categoryId = req.getParameter("categoryId");
        String content = req.getParameter("content");

        // Xử lý ngày đăng
        Date postDate = parsePostDate(req.getParameter("postDate"));

        int viewCount = 0;
        try {
            viewCount = (viewCountStr != null && !viewCountStr.isEmpty()) ? Integer.parseInt(viewCountStr) : 0;
        } catch (NumberFormatException ignored) {
        }

        if (("create".equals(action) || "update".equals(action)) &&
                (title == null || categoryId == null || content == null ||
                        title.isBlank() || categoryId.isBlank() || content.isBlank())) {
            req.setAttribute("error", "Vui lòng điền đầy đủ tiêu đề, loại tin và nội dung.");
            processGet(req, resp);
            return;
        }

        // Xử lý ảnh
        Part imagePart = req.getPart("image");
        String imageFileName = ImageUtil.save(imagePart, getServletContext());

        switch (action) {
            case "create" ->
                    handleCreate(req, author, id, title, postDate, viewCount, categoryId, content, imageFileName);
            case "update" ->
                    handleUpdate(req, resp, author, id, title, postDate, viewCount, categoryId, content, imageFileName);
            case "delete" -> {
                if (id != null && !id.isBlank()) {
                    newsService.delete(id);
                    req.setAttribute("success", "Bài viết đã được xóa thành công.");
                } else {
                    req.setAttribute("error", "Không tìm thấy ID bài viết để xóa.");
                }
            }
            case "reset" -> {
                // Không làm gì cả
            }
            default -> req.setAttribute("error", "Hành động không hợp lệ: " + action);
        }

        processGet(req, resp);
    }

    private void handleEdit(HttpServletRequest req, HttpServletResponse resp, String author)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            req.setAttribute("error", "Không tìm thấy ID bài viết để sửa.");
            processGet(req, resp);
            return;
        }

        News news = newsService.findById(id).orElse(null);
        if (news == null) {
            req.setAttribute("error", "Không tìm thấy bài viết với ID: " + id);
            processGet(req, resp);
            return;
        }

        req.setAttribute("editNews", news);
        req.setAttribute("categoryList", categoryService.findAll());
        req.setAttribute("newsList", newsService.getNewsByAuthor(author));

        forwardToAdminLayout(req, resp, "/views/pages/reporter/news-management.jsp");
    }

    private void handleCreate(HttpServletRequest req, String author, String id, String title, Date postDate,
                              int viewCount, String categoryId, String content, String imageFileName) {

        // Kiểm tra ID đã tồn tại chưa
        if (id != null && newsService.exists(id)) {
            req.setAttribute("error", "ID bài viết đã tồn tại. Vui lòng chọn ID khác.");
            return;
        }

        News news = new News();
        news.setId(id);
        news.setTitle(title);
        news.setPostDate(postDate);
        news.setViewCount(viewCount);
        news.setCategoryId(categoryId);
        news.setContent(content);
        news.setAuthor(author);
        if (imageFileName != null) news.setImage(imageFileName);

        boolean createdNews = newsService.insert(news);
        if (createdNews) {
            sendNewsletter(news, req);
            req.setAttribute("success", "Bài viết mới đã được tạo thành công.");
        } else {
            req.setAttribute("error", "Không thể tạo bài viết mới. Vui lòng thử lại.");
        }
    }

    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp, String author, String id, String title,
                              Date postDate, int viewCount, String categoryId, String content, String imageFileName)
            throws ServletException, IOException {

        if (id == null || id.isBlank()) {
            req.setAttribute("error", "Không tìm thấy ID bài viết để cập nhật.");
            processGet(req, resp);
            return;
        }

        News news = newsService.findById(id).orElse(null);
        if (news == null) {
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
        if (imageFileName != null) news.setImage(imageFileName);

        newsService.update(news);
        req.setAttribute("success", "Bài viết đã được cập nhật thành công.");
    }

    private void sendNewsletter(News news, HttpServletRequest req) {
        String newId = news.getId();
        String detailLink = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                + req.getContextPath() + "/user/news-detail?id=" + newId + "&categoryId=" + news.getCategoryId();

        String from = AppConfigReader.getMailUsername();
        String subject = "Tin mới: " + news.getTitle();

        String body = """
                <div style="font-family: Arial, sans-serif; padding: 20px; background-color: #f9f9f9;">
                    <h2 style="color: #1d4ed8;">📰 Bài viết mới vừa được đăng!</h2>
                    <p><strong>Tiêu đề:</strong> %s</p>
                    <p><strong>Tác giả:</strong> %s</p>
                    <p><strong>Ngày đăng:</strong> %s</p>
                    <hr style="margin: 20px 0;">
                    <p><strong>Nội dung:</strong></p>
                    <div style="background: #fff; padding: 10px; border: 1px solid #ddd;">%s</div>
                    <p style="margin-top: 20px;">
                        👉 <a href="%s" style="color: #2563eb;">Xem chi tiết bài viết tại website</a>
                    </p>
                </div>
                """.formatted(news.getTitle(), news.getAuthor(), news.getPostDate(), news.getContent(), detailLink);

        List<Newsletter> subscribers = newsletterService.getAllNewsletterByEnabled();
        for (Newsletter subscriber : subscribers) {
            try {
                Mailer.send(from, subscriber.getEmail(), subject, body);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Date parsePostDate(String dateStr) {
        try {
            if (dateStr != null && !dateStr.isEmpty()) {
                LocalDate localDate = LocalDate.parse(dateStr);
                return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi phân tích ngày đăng: " + e.getMessage());
        }
        return new Date();
    }
}
