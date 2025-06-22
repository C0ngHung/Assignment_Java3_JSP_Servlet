package org.example.assignment_java3.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.assignment_java3.dao.NewsletterDAO;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.service.NewsletterService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class NewsletterServiceImpl implements NewsletterService {

    private final NewsletterDAO newsletterDAO;

    // === CRUD từ BaseService ===
    @Override
    public List<Newsletter> findAll() {
        return newsletterDAO.findAll();
    }

    @Override
    public Optional<Newsletter> findById(String email) {
        return Optional.ofNullable(newsletterDAO.findById(email));
    }

    @Override
    public boolean insert(Newsletter newsletter) {
        return newsletterDAO.insert(newsletter) > 0;
    }

    @Override
    public boolean update(Newsletter newsletter) {
        return newsletterDAO.update(newsletter) > 0;
    }

    @Override
    public boolean delete(String email) {
        return newsletterDAO.delete(email) > 0;
    }

    @Override
    public boolean exists(String email) {
        return newsletterDAO.exists(email);
    }

    // === Phương thức đặc thù ===
    @Override
    public boolean isEmailExists(String email) {
        return newsletterDAO.isEmailExists(email);
    }

    @Override
    public List<Newsletter> getAllNewsletterByEnabled() {
        return newsletterDAO.findAllEnabled();
    }

    @Override
    public Optional<Newsletter> findByEmail(String email) {
        return Optional.ofNullable(newsletterDAO.findByEmail(email));
    }
}
