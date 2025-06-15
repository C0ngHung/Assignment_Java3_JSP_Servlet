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
        String author = user.getId();

        List<News> newsList = newsService.getNewsByAuthor(author);
        List<Category> categoryList = categoryService.getAllCategory();

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
            System.err.println("Action parameter is null");
            processGet(req, resp);
            return;
        }

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

            req.setAttribute("editNews", news);
            req.setAttribute("categoryList", categoryList);
            req.setAttribute("newsList", newsList);

            forwardToAdminLayout(req, resp, "/views/pages/reporter/news-management.jsp");
            return;
        }

        // Common fields
        String id = req.getParameter("id");
        String title = req.getParameter("title");
        String viewCountStr = req.getParameter("viewCount");
        String categoryId = req.getParameter("categoryId");
        String content = req.getParameter("content");

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

        int viewCount = (viewCountStr != null && !viewCountStr.isEmpty()) ? Integer.parseInt(viewCountStr) : 0;

        if (("create".equals(action) || "update".equals(action)) &&
                (title == null || title.isEmpty() ||
                        categoryId == null || categoryId.isEmpty() ||
                        content == null || content.isEmpty())) {
            req.setAttribute("error", "Vui lòng điền đầy đủ tiêu đề, loại tin và nội dung.");
            processGet(req, resp);
            return;
        }

        // Upload image
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

                News createdNews = newsService.createNews(news);
                if (createdNews != null) {
                    String newId = createdNews.getId();
                    String detailLink = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort()
                            + req.getContextPath() + "/user/news-detail?id=" + newId
                            + "&categoryId=" + createdNews.getCategoryId();

                    List<Newsletter> subscriberEmails = newsletterService.getAllNewsletterByEnabled();
                    String from = AppConfigReader.getMailUsername();
                    String subject = "Tin mới: " + createdNews.getTitle();

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
                            """.formatted(createdNews.getTitle(), createdNews.getAuthor(),
                            createdNews.getPostDate(), createdNews.getContent(), detailLink);

                    for (Newsletter subscriber : subscriberEmails) {
                        try {
                            Mailer.send(from, subscriber.getEmail(), subject, body);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            }

            case "update": {
                if (id == null || id.isEmpty()) {
                    req.setAttribute("error", "Không tìm thấy ID bài viết để cập nhật.");
                    processGet(req, resp);
                    return;
                }
                News news = newsService.getNewsById(id);
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
                if (imageFileName != null) {
                    news.setImage(imageFileName);
                }
                newsService.updateNews(news);
                break;
            }

            case "delete": {
                if (id == null || id.isEmpty()) {
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
                req.setAttribute("error", "Hành động không hợp lệ: " + action);
                break;
            }
        }

        processGet(req, resp);
    }
}
