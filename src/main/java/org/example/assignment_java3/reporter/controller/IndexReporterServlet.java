package org.example.assignment_java3.reporter.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reporter/index")
public class IndexReporterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        String page = "/views/pages/reporter/news.jsp";
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/reporter/layoutReporter.jsp").forward(req, resp);
    }
}
