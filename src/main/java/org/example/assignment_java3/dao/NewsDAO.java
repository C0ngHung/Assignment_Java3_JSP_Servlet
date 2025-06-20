package org.example.assignment_java3.dao;

import org.example.assignment_java3.entity.News;

import java.util.List;

public interface NewsDAO {

    News createNews(News news);

    News getNewsById(String id);

    News updateNews(News news);

    int deleteNews(String id);

    List<News> getAllNews();

    List<News> getAllNewsByHome();

    List<News> getTop5ViewsCount();

    List<News> getTop5NewsLatest();

    List<News> getTop5NewsViewed(List<String> ids);

    List<News> getNewsByCategory(String categoryId);

    boolean updateViewCount(String id);

    List<News> getNewsByAuthor(String author);

}
