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

        // Lấy danh sách tin tức trang chủ
        List<News> newsList = newsService.getAllNewsByHome();
        req.setAttribute("newsList", newsList);

        // Dùng hàm tiện ích từ base để set page và forward
        setPageAndForward(req, resp, "/views/pages/user/news.jsp");
    }
}
