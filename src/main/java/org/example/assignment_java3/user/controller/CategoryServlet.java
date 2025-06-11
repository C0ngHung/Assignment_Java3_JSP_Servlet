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

@WebServlet("/user/category/*")
public class CategoryServlet extends BaseNewsServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException {
        // Lấy phần path sau user/category
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy trang");
            return;
        }

        String categoryId = pathInfo.substring(1); // Lấy id category tìm kiếm
        List<News> newsList = newsService.getNewsByCategory(categoryId); // Lấy danh sách tin tốc theo category tìm kiếm
        // Lấy tất cả danh sách loại tin
        List<Category> categoryList = categoryService.getAllCategory();

        String page = "/views/pages/user/news.jsp"; // Trang hien thi danh sach tin tốc theo category tìm kiếm
        // Lấy Attribute chung cho trang news.jsp
        addCommonNewsAttributes(req);

        req.setAttribute("page", page); // Trang hien thi danh sach tin tốc theo category tìm kiếm
        req.setAttribute("newsList", newsList); // Danh sách tin tốc theo category tìm kiếm
        req.setAttribute("categoryList", categoryList); // Danh sách loại tin
        req.getRequestDispatcher("/views/layouts/user/layoutUser.jsp").forward(req, resp);
    }
}
