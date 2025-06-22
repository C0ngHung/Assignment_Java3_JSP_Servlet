package org.example.assignment_java3.dao;

import org.example.assignment_java3.common.dao.BaseDAO;
import org.example.assignment_java3.entity.User;

import java.util.Optional;

/**
 * Giao diện DAO cho bảng User.
 * Kế thừa BaseDAO để dùng sẵn các thao tác CRUD cơ bản.
 */
public interface UserDAO extends BaseDAO<User, String> {

    /**
     * Kiểm tra đăng nhập bằng username và password.
     *
     * @param username tên đăng nhập
     * @param password mật khẩu
     * @return Optional chứa User nếu đúng thông tin, rỗng nếu sai
     */
    Optional<User> checkLogin(String username, String password);
}
