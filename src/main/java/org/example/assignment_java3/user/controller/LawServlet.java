package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseNewsServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/law")
public class LawServlet extends BaseNewsServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        // Lấy danh sách tin tức
        List<News> newsList = newsService.getNewsByCategory("cat2");
        String page = "/views/pages/user/news.jsp";
        // Lấy Attribute chung cho trang news.jsp
        addCommonNewsAttributes(req);

        req.setAttribute("page", page);
        req.setAttribute("newsList", newsList);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
