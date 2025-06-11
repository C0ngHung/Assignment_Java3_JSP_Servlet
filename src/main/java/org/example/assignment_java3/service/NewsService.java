package org.example.assignment_java3.service;

import org.example.assignment_java3.entity.News;

import java.util.List;

public interface NewsService {

    News createNews(News news);

    News getNewsById(String id);

    News updateNews(News news);

    void deleteNews(String id);

    List<News> getAllNews();

    List<News> getTop5ViewsCount();

    List<News> getTop5NewsLatest();

    List<News> getTop5NewsViewed(List<String> ids);

    List<News> getAllNewsByHome();

    boolean updateViewCount(String id);

    List<News> getNewsByCategory(String categoryId);

    List<News> getNewsByAuthor(String author);

}
