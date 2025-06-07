package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseNewsServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/sport")
public class SportServlet extends BaseNewsServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        // Lay danh sach tin tuc
        List<News> newsList = newsService.getNewsByCategory("cat3");
        // Khai bao trang news.jsp
        String page = "/views/pages/user/news.jsp";
        // Add Attribute chung cho trang news.jsp
        addCommonNewsAttributes(req);

        req.setAttribute("page", page);
        req.setAttribute("newsList", newsList);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
