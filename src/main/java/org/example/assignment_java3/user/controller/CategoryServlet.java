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

        // Lấy phần path sau /user/category/
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy trang");
            return;
        }

        // Lấy id category từ URL
        String categoryId = pathInfo.substring(1);

        // Lấy danh sách tin tức theo category
        List<News> newsList = newsService.getNewsByCategory(categoryId);
        req.setAttribute("newsList", newsList);

        // Gọi hàm trong base để set layout + thuộc tính chung
        setPageAndForward(req, resp, "/views/pages/user/news.jsp");
    }
}
