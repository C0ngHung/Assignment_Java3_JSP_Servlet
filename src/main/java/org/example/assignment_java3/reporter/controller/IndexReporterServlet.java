package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.common.controller.BaseReporterServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/reporter/index")
public class IndexReporterServlet extends BaseReporterServlet {

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<News> newsList = newsService.getAllNewsByHome();
        String page = "/views/pages/admin/news.jsp";
        req.setAttribute("newsList", newsList);
        forwardToAdminLayout(req, resp, page);
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processGet(req, resp);
    }
}