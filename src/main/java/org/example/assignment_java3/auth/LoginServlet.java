package org.example.assignment_java3.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.assignment_java3.dao.impl.UserDAOImpl;
import org.example.assignment_java3.dao.UserDAO;
import org.example.assignment_java3.entity.User;
import org.example.assignment_java3.service.UserService;
import org.example.assignment_java3.service.serviceImpl.UserServiceImpl;

import java.io.IOException;
import java.util.Base64;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        UserDAO userDAO = new UserDAOImpl();
        this.userService = new UserServiceImpl(userDAO);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    String encoded = cookie.getValue();
                    byte[] bytes = Base64.getDecoder().decode(encoded);
                    String[] userInfo = new String(bytes).split(",");
                    req.setAttribute("username", userInfo[0]);
                    req.setAttribute("password", userInfo[1]);
                    break;
                }
            }
        }
        req.getRequestDispatcher("/views/pages/auth/login.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        String action = req.getParameter("action");
        if ("guest".equals(action)) {
            resp.sendRedirect(req.getContextPath() + "/user/index");
            return;
        }

        var optionalUser = userService.checkLogin(username, password);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            req.setAttribute("message", "Đăng nhập thành công!");

            // lưu thông tin user vào session
            req.getSession().setAttribute("user", user);

            // Lưu role vào session để phân quyền nav
            String role = user.isRole() ? "admin" : "reporter";
            req.getSession().setAttribute("currentUserRole", role);

            // Xử lý remember me
            if (remember != null) {
                byte[] bytes = (username + "," + password).getBytes();
                String userInfo = Base64.getEncoder().encodeToString(bytes);
                Cookie cookie = new Cookie("user", userInfo);
                cookie.setMaxAge(30 * 24 * 60 * 60); // hiệu lực 30 ngày
                cookie.setPath("/"); // hiệu lực toàn ứng dụng

                // Gửi về trình duyệt
                resp.addCookie(cookie); // cookie
            } else { // xoa Cookie nếu không tích vào remember me
                Cookie[] cookies = req.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("user")) {
                            cookie.setMaxAge(0); // xóa cookie
                            cookie.setPath("/");
                            resp.addCookie(cookie);
                            break;
                        }
                    }
                }
            }
            // Phân quyền phóng viên và admin khi đăng nhập
            if (user.isRole()) {
                resp.sendRedirect(req.getContextPath() + "/admin/index");
            } else {
                resp.sendRedirect(req.getContextPath() + "/reporter/index");
            }
            return;
        } else {
            req.setAttribute("message", "Tài khoản hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("/views/pages/auth/login.jsp").forward(req, resp);
        }
    }
}
