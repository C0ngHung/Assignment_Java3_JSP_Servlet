package org.example.assignment_java3.common.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.dao.CategoryDAO;
import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.dao.impl.CategoryDAOImpl;
import org.example.assignment_java3.dao.impl.NewsDAOImpl;
import org.example.assignment_java3.dao.impl.UserDAOImpl;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.CategoryService;
import org.example.assignment_java3.service.NewsService;
import org.example.assignment_java3.service.UserService;
import org.example.assignment_java3.service.serviceImpl.CategoryServiceImpl;
import org.example.assignment_java3.service.serviceImpl.NewsServiceImpl;
import org.example.assignment_java3.service.serviceImpl.UserServiceImpl;

import java.io.IOException;

public abstract class BaseReporterServlet extends HttpServlet {

    protected NewsService newsService;
    protected CategoryService categoryService;
    protected UserService userService;

    @Override
    public void init() throws ServletException {
        NewsDAO newsDAO = new NewsDAOImpl();
        this.newsService = new NewsServiceImpl(newsDAO);
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        this.categoryService = new CategoryServiceImpl(categoryDAO);
        UserDAO userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO);
    }

    protected boolean checkReporterSession(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null || user.isRole()) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    protected void forwardToAdminLayout(HttpServletRequest req, HttpServletResponse resp, String page)
            throws ServletException, IOException {
        req.setAttribute("page", page);
        req.getRequestDispatcher("/views/layouts/admin/layoutAdmin.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (checkReporterSession(req, resp)) {
            processGet(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (checkReporterSession(req, resp)) {
            processPost(req, resp);
        }
    }

    protected abstract void processGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;

    protected abstract void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException;
}