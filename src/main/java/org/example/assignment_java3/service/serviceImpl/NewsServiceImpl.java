package org.example.assignment_java3.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.service.NewsService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsDAO newsDAO;

    // === CRUD kế thừa từ BaseService ===
    @Override
    public List<News> findAll() {
        return newsDAO.findAll();
    }

    @Override
    public Optional<News> findById(String id) {
        return Optional.ofNullable(newsDAO.findById(id));
    }

    @Override
    public boolean insert(News news) {
        return newsDAO.insert(news) > 0;
    }

    @Override
    public boolean update(News news) {
        return newsDAO.update(news) > 0;
    }

    @Override
    public boolean delete(String id) {
        return newsDAO.delete(id) > 0;
    }

    @Override
    public boolean exists(String id) {
        return newsDAO.exists(id);
    }

    // === Các phương thức đặc thù của News ===

    @Override
    public List<News> getTop5ViewsCount() {
        return newsDAO.getTop5ViewsCount();
    }

    @Override
    public List<News> getTop5NewsLatest() {
        return newsDAO.getTop5NewsLatest();
    }

    @Override
    public List<News> getTop5NewsViewed(List<String> ids) {
        return newsDAO.getTop5NewsViewed(ids);
    }

    @Override
    public List<News> getAllNewsByHome() {
        return newsDAO.getAllNewsByHome();
    }

    @Override
    public boolean updateViewCount(String id) {
        return newsDAO.updateViewCount(id);
    }

    @Override
    public List<News> getNewsByCategory(String categoryId) {
        return newsDAO.getNewsByCategory(categoryId);
    }

    @Override
    public List<News> getNewsByAuthor(String author) {
        return newsDAO.getNewsByAuthor(author);
    }

    @Override
    public boolean existsByCategoryId(String categoryId) {
        return newsDAO.existsByCategoryId(categoryId);
    }

    @Override
    public boolean existsByAuthorId(String authorId) {
        return newsDAO.existsByAuthorId(authorId);
    }
}
