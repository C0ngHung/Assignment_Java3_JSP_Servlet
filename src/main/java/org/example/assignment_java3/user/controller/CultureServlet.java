package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/culture")
public class CultureServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {

        String page = "/views/pages/user/culture.jsp";
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
