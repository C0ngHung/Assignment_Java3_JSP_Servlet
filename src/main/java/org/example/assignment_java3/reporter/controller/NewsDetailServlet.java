package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseReporterServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/reporter/news-detail")
public class NewsDetailServlet extends BaseReporterServlet {

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Lấy id và categoryId từ tham số URL
            String id = req.getParameter("id");
            String categoryId = req.getParameter("categoryId");

            // Kiểm tra dữ liệu đầu vào
            if (id == null || id.isBlank() || categoryId == null || categoryId.isBlank()) {
                req.setAttribute("error", "Thiếu thông tin cần thiết để xem chi tiết tin tức.");
                forwardToAdminLayout(req, resp, "/views/pages/error/error.jsp");
                return;
            }

            // Gọi service để lấy thông tin tin tức
            News news = newsService.findById(id).orElse(null);

            if (news == null) {
                req.setAttribute("error", "Không tìm thấy tin tức phù hợp.");
                forwardToAdminLayout(req, resp, "/views/pages/error/error.jsp");
                return;
            }
            List<News> newsList = newsService.getNewsByCategory(categoryId);

            // Cập nhật lượt xem cho tin tức
            newsService.updateViewCount(id);

            // Thiết lập trang JSP để hiển thị
            String page = "/views/pages/admin/news-detail.jsp";

            // Thiết lập các thuộc tính và forward đến layout
            req.setAttribute("news", news);
            req.setAttribute("newsList", newsList);
            forwardToAdminLayout(req, resp, page);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi khi hiển thị chi tiết tin tức.");
            forwardToAdminLayout(req, resp, "/views/pages/error/error.jsp");
        }
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Cho phép xử lý POST như GET để tiện sử dụng
        processGet(req, resp);
    }
}
