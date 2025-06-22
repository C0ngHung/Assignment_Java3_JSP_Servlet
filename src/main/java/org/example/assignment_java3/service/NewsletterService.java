package org.example.assignment_java3.service;

import org.example.assignment_java3.common.service.BaseService;
import org.example.assignment_java3.entity.Newsletter;

import java.util.List;
import java.util.Optional;

/**
 * Service xử lý logic liên quan đến đăng ký nhận tin (newsletter).
 */
public interface NewsletterService extends BaseService<Newsletter, String> {

    /**
     * Kiểm tra email đã tồn tại trong hệ thống hay chưa.
     *
     * @param email địa chỉ email cần kiểm tra
     * @return true nếu đã tồn tại
     */
    boolean isEmailExists(String email);

    /**
     * Lấy danh sách newsletter đang được bật (enable = true).
     *
     * @return danh sách email được kích hoạt
     */
    List<Newsletter> getAllNewsletterByEnabled();

    /**
     * Lấy thông tin newsletter theo email.
     *
     * @param email địa chỉ email
     * @return Optional chứa newsletter nếu tìm thấy
     */
    Optional<Newsletter> findByEmail(String email);
}
