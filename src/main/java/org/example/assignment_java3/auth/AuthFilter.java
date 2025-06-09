package org.example.assignment_java3.auth;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.assignment_java3.entity.User;

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/reporter/*"})
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;

        String uri = req.getRequestURI();

        if (user == null) {
            // Với tài khoản guest chưa login => Chuyển đển trang login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Nếu có session, user phần quyen admin => Chuyển vào trang admin dựa trên uri
        if (uri.startsWith(req.getContextPath() + "/admin") && !user.isRole()) {
            // Ngắn repoter cố vào trang admin
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập vào trang ADMIN");
            return;
        }

        if (uri.startsWith(req.getContextPath() + "/reporter") && user.isRole()) {
            // Ngắn admin cố vào trang reporter
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "ADMIN không thể truy cập vào trang Reporter");
            return;
        }
        // Nếu đúng quyền thì cho qua
        chain.doFilter(servletRequest, servletResponse);
    }
}
