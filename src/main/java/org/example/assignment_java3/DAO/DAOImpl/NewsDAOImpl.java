package org.example.assignment_java3.DAO.DAOImpl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.assignment_java3.DAO.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.utils.JdbcHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsDAOImpl implements NewsDAO {
    @Override
    public News createNews(News news) {
        String sql = "INSERT INTO news (id, title, content, image, postDate, author, viewCount, categoryId, home)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int rows = JdbcHelper.update(sql,
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getImage(),
                news.getPostDate(),
                news.getAuthor(),
                news.getViewCount(),
                news.getCategoryId(),
                news.isHome());
        return rows > 0 ? news : null;
    }

    @Override
    public News getNewsById(int id) {
        String sql = "SELECT * FROM news WHERE id = ?";
        return JdbcHelper.query(sql, rs -> {
            if (rs.next()) {
                News news = new News();
                news.setId(rs.getString("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setImage(rs.getString("image"));
                news.setPostDate(rs.getDate("postDate"));
                news.setAuthor(rs.getString("author"));
                news.setViewCount(rs.getInt("viewCount"));
                news.setCategoryId(rs.getString("categoryId"));
                news.setHome(rs.getBoolean("home"));
                return news;
            }
            return null;
        }, id);
    }


    @Override
    public News updateNews(News news) {
        return null;
    }

    @Override
    public int deleteNews(String id) {
        return 0;
    }

    @Override
    public List<News> getAllNewsByHome() {
        String sql = "SELECT * FROM news WHERE home = 1";
        return JdbcHelper.query(sql, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getString("id"));
                news.setTitle(rs.getString("title"));
                news.setContent(rs.getString("content"));
                news.setImage(rs.getString("image"));
                news.setPostDate(rs.getDate("postDate"));
                news.setAuthor(rs.getString("author"));
                news.setViewCount(rs.getInt("viewCount"));
                news.setCategoryId(rs.getString("categoryId"));
                news.setHome(rs.getBoolean("home"));
                newsList.add(news);
            }
            if (!newsList.isEmpty()) {
                return newsList;
            } else {
                throw new RuntimeException("Không tìm thấy danh sách news");
            }
        });
    }

    @Override
    public List<News> getTop5ViewsCount() {
        return List.of();
    }

    @Override
    public List<News> getTop5NewsLatest() {
        return List.of();
    }

    @Override
    public List<News> getTop5NewsSeenMore() {
        return List.of();
    }
}
