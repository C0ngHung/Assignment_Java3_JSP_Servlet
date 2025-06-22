package org.example.assignment_java3.user.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.common.controller.BaseUserServlet;
import org.example.assignment_java3.entity.News;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/news-detail")
public class NewsDetailServlet extends BaseUserServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy id và categoryId trên URL từ param
        String id = req.getParameter("id");
        String categoryId = req.getParameter("categoryId");

        if (id == null || categoryId == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu tham số ID hoặc categoryId");
            return;
        }

        try {
            // Gọi service để lấy thông tin tin tức chi tiết
            News news = newsService.findById(id).orElse(null);

            if (news == null) {
                req.setAttribute("error", "Không tìm thấy tin tức phù hợp.");
                setPageAndForward(req, resp, "/views/pages/user/news-detail.jsp");
                return;
            }

            // Gọi danh sách tin liên quan theo category
            List<News> newsList = newsService.getNewsByCategory(categoryId);

            // Cập nhật lượt xem cho tin tức
            newsService.updateViewCount(id);

            // Cập nhật lịch sử lượt xem cho người dùng
            HttpSession session = req.getSession();
            session.setMaxInactiveInterval(30); // set timeout 30s
            updateHistoryView(session, id);

            // Truyền dữ liệu cho JSP
            req.setAttribute("news", news);
            req.setAttribute("newsList", newsList);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Đã xảy ra lỗi khi hiển thị chi tiết tin tức.");
        }

        // Dùng hàm từ base để set layout + thuộc tính chung
        setPageAndForward(req, resp, "/views/pages/user/news-detail.jsp");
    }
}
