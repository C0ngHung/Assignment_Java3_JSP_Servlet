package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseUserServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/category/*")
public class CategoryServlet extends BaseUserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy phần thông tin đường dẫn từ request
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.length() <= 1) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy trang.");
            return;
        }

        // Cắt bỏ dấu "/" đầu tiên
        String categoryId = pathInfo.substring(1);

        try {
            // Lấy danh sách tin tức theo category
            List<News> newsList = newsService.getNewsByCategory(categoryId);

            if (newsList == null || newsList.isEmpty()) {
                req.setAttribute("message", "Không có tin tức nào trong danh mục này.");
            } else {
                req.setAttribute("newsList", newsList);
            }

        } catch (Exception e) {
            e.printStackTrace(); // hoặc log lỗi bằng Logger
            req.setAttribute("error", "Đã xảy ra lỗi khi tải tin tức theo danh mục.");
        }

        // Hiển thị trang danh sách tin
        setPageAndForward(req, resp, "/views/pages/user/news.jsp");
    }
}
