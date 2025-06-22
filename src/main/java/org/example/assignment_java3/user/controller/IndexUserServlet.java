package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseUserServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/index")
public class IndexUserServlet extends BaseUserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Lấy danh sách tin tức hiển thị ở trang chủ
            List<News> newsList = newsService.getAllNewsByHome();

            if (newsList == null || newsList.isEmpty()) {
                req.setAttribute("message", "Chưa có tin tức nào được hiển thị ở trang chủ.");
            } else {
                req.setAttribute("newsList", newsList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi khi tải tin tức trang chủ.");
        }

        // Chuyển tiếp sang trang giao diện người dùng
        setPageAndForward(req, resp, "/views/pages/user/news.jsp");
    }
}
