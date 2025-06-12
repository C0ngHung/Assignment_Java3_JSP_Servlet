package org.example.assignment_java3.admin.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseNewsServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/index")
public class IndexAdminServlet extends BaseNewsServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Lấy danh sách tin tức


        List<News> newsList = newsService.getAllNewsByHome();
        String page = "/views/pages/admin/news.jsp";

        // lay attribute chung cho trang news.jsp
        addCommonNewsAttributes(req);

        req.setAttribute("newsList", newsList);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }
}
