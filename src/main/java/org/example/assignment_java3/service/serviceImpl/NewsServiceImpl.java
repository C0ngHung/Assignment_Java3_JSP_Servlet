package org.example.assignment_java3.service.serviceImpl;

import org.example.assignment_java3.DAO.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.service.NewsService;

import java.util.List;

public class NewsServiceImpl implements NewsService {

    private final NewsDAO newsDAO;

    public NewsServiceImpl(NewsDAO newsDAO) {
        this.newsDAO = newsDAO;
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
}
