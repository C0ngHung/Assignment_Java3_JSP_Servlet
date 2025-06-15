package org.example.assignment_java3.service.serviceImpl;

import lombok.AllArgsConstructor;

import org.example.assignment_java3.dao.NewsletterDAO;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.service.NewsletterService;

import java.util.List;

@AllArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterDAO newsletterDAO;

    @Override
    public Newsletter createNewsletter(Newsletter newsletter) {
        try {
            return newsletterDAO.createNewsletter(newsletter);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo email nhận bản tin: " + e.getMessage(), e);
        }
    }

    @Override
    public Newsletter getNewsletterEmail(String email) {
        try {
            return newsletterDAO.getNewsletterByEmail(email);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy email nhận bản tin: " + e.getMessage(), e);
        }
    }


    @Override
    public Newsletter updateNewsletter(Newsletter newsletter) {
        try {
            return newsletterDAO.updateNewsletter(newsletter);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật email nhận bản tin: " + e.getMessage(), e);
        }
    }

    @Override
    public int deleteNewsletter(String id) {
        try {
            return newsletterDAO.deleteNewsletter(id);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi xóa email nhận bản tin: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Newsletter> getAllNewsletter() {
        try {
            return newsletterDAO.getAllNewsletter();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách email nhận bản tin: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        try {
            return newsletterDAO.isEmailExists(email);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi kiểm tra email tồn tại: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Newsletter> getAllNewsletterByEnabled() {
        try {
            return newsletterDAO.getAllNewsletterByEnabled();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách email nhận bản tin đã kích hoạt: " + e.getMessage(), e);
        }
    }
}
