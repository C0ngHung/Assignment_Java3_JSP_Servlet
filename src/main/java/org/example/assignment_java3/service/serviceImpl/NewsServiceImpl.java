package org.example.assignment_java3.service.serviceImpl;

import lombok.AllArgsConstructor;
import org.example.assignment_java3.DAO.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.service.NewsService;

import java.util.List;

@AllArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsDAO newsDAO;


    @Override
    public News createNews(News news) {
        try {
            return newsDAO.createNews(news);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi tạo tin tức: " + e.getMessage());
            return null;
        }
    }

    @Override
    public News getNewsById(String id) {
        try {
            return newsDAO.getNewsById(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy tin tức theo id: " + e.getMessage());
            return null;
        }
    }

    @Override
    public News updateNews(News news) {
        try {
            return newsDAO.updateNews(news);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi cập nhật tin tức: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteNews(String id) {
        try {
            newsDAO.deleteNews(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi xóa tin tức: " + e.getMessage());
        }
    }

    @Override
    public List<News> getAllNews() {
        try {
            return newsDAO.getAllNews();
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy danh sách tin tức: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<News> getTop5ViewsCount() {
        try {
            return newsDAO.getTop5ViewsCount();
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy danh sách 5 tin tức nhiêu lượt xem nhất: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<News> getTop5NewsLatest() {
        try {
            return newsDAO.getTop5NewsLatest();
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy danh sách 5 tin tức mới nhất: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<News> getTop5NewsViewed(List<String> ids) {
        try {
            return newsDAO.getTop5NewsViewed(ids);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy danh sách 5 tin tức mới xem gần đây: " + e.getMessage());
            return null;
        }
    }


    @Override
    public List<News> getAllNewsByHome() {
        try {
            return newsDAO.getAllNewsByHome();
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy danh sách news: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean updateViewCount(String id) {
        try {
            return newsDAO.updateViewCount(id);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi cập nhật số lượt xem: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<News> getNewsByCategory(String categoryId) {
        try {
            return newsDAO.getNewsByCategory(categoryId);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy tin tức theo danh muc: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<News> getNewsByAuthor(String author) {
        try {
            return newsDAO.getNewsByAuthor(author);
        } catch (RuntimeException e) {
            System.out.println("Lỗi khi lấy tin tức theo tài khoản: " + e.getMessage());
            return null;
        }
    }
}
