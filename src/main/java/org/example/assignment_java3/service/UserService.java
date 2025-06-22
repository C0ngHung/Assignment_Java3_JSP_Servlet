package org.example.assignment_java3.service;

import org.example.assignment_java3.common.service.BaseService;
import org.example.assignment_java3.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends BaseService<User, String> {

    /**
     * Kiểm tra thông tin đăng nhập.
     *
     * @param username tên đăng nhập
     * @param password mật khẩu
     * @return Optional chứa User nếu thông tin hợp lệ, ngược lại là Optional.empty()
     */
    Optional<User> checkLogin(String username, String password);

}
