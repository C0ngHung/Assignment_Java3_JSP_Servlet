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

        try {
            // Lấy danh sách tin tức hiển thị ở trang chủ
            List<News> newsList = newsService.getAllNewsByHome();

            // Gán danh sách vào attribute để hiển thị ở JSP
            req.setAttribute("newsList", newsList);

            // Gọi phương thức từ lớp cha để forward tới layout quản trị
            forwardToAdminLayout(req, resp, "/views/pages/admin/news.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi khi tải dữ liệu trang phóng viên.");
            forwardToAdminLayout(req, resp, "/views/pages/error/error.jsp");
        }
    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processGet(req, resp);
    }
}
