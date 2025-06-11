package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseNewsServlet;
import org.example.assignment_java3.entity.Category;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/index")
public class IndexUserServlet extends BaseNewsServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // Lấy danh sách tin tức
        List<News> newsList = newsService.getAllNewsByHome();
        String page = "/views/pages/user/news.jsp";
        // Lấy Attribute chung cho trang news.jsp
        addCommonNewsAttributes(req);
        // Lấy tất cả danh sách loại tin
        List<Category> categoryList = categoryService.getAllCategory();

        req.setAttribute("page", page);
        req.setAttribute("newsList", newsList);
        req.setAttribute("categoryList", categoryList);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
