package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/top5-seen")
public class Top5SeenServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        String page = "/views/pages/user/top5-seen.jsp";
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
