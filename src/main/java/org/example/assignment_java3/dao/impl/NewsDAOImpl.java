package org.example.assignment_java3.dao.impl;

import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NewsDAOImpl implements NewsDAO {

    private static final String SQL_INSERT = "INSERT INTO news (id, title, content, image, postDate, author, viewCount, categoryId, home) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM news WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE news SET title = ?, content = ?, image = ?, postDate = ?, author = ?, viewCount = ?, categoryId = ?, home = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM news WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM news";
    private static final String SQL_EXISTS = "SELECT COUNT(*) FROM news WHERE id = ?";
    private static final String SQL_SELECT_HOME = "SELECT * FROM news WHERE home = 1";
    private static final String SQL_TOP5_LATEST = "SELECT TOP 5 * FROM news ORDER BY postDate DESC";
    private static final String SQL_TOP5_VIEWS = "SELECT TOP 5 * FROM news ORDER BY viewCount DESC";
    private static final String SQL_UPDATE_VIEW_COUNT = "UPDATE news SET viewCount = viewCount + 1 WHERE id = ?";
    private static final String SQL_BY_CATEGORY = "SELECT * FROM news WHERE categoryId = ?";
    private static final String SQL_BY_AUTHOR = "SELECT * FROM news WHERE author = ?";

    private News map(ResultSet rs) throws SQLException {
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

    // CRUD từ BaseDAO

    @Override
    public int insert(News news) {
        return JdbcHelper.update(SQL_INSERT,
                news.getId(), news.getTitle(), news.getContent(), news.getImage(),
                news.getPostDate(), news.getAuthor(), news.getViewCount(),
                news.getCategoryId(), news.isHome());
    }

    @Override
    public News findById(String id) {
        return JdbcHelper.query(SQL_SELECT_BY_ID, rs -> {
            if (rs.next()) return map(rs);
            return null;
        }, id);
    }

    @Override
    public int update(News news) {
        return JdbcHelper.update(SQL_UPDATE,
                news.getTitle(), news.getContent(), news.getImage(), news.getPostDate(),
                news.getAuthor(), news.getViewCount(), news.getCategoryId(), news.isHome(),
                news.getId());
    }

    @Override
    public int delete(String id) {
        return JdbcHelper.update(SQL_DELETE, id);
    }

    @Override
    public List<News> findAll() {
        return JdbcHelper.query(SQL_SELECT_ALL, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }

    @Override
    public boolean exists(String id) {
        return JdbcHelper.query(SQL_EXISTS, rs -> rs.next() && rs.getInt(1) > 0, id);
    }

    // Mở rộng

    @Override
    public List<News> getAllNewsByHome() {
        return JdbcHelper.query(SQL_SELECT_HOME, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }

    @Override
    public List<News> getTop5ViewsCount() {
        return JdbcHelper.query(SQL_TOP5_VIEWS, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }

    @Override
    public List<News> getTop5NewsLatest() {
        return JdbcHelper.query(SQL_TOP5_LATEST, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        });
    }

    @Override
    public List<News> getTop5NewsViewed(List<String> ids) {
        if (ids == null || ids.isEmpty()) return List.of();

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String sql = "SELECT TOP 5 * FROM news WHERE id IN (" + placeholders + ") ORDER BY viewCount DESC";

        List<News> newsList = JdbcHelper.query(sql, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }, ids.toArray());

        // Optional: sort theo vị trí trong danh sách gốc
        newsList.sort(Comparator.comparingInt(n -> ids.indexOf(n.getId())));
        return newsList;
    }

    @Override
    public List<News> getNewsByCategory(String categoryId) {
        return JdbcHelper.query(SQL_BY_CATEGORY, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }, categoryId);
    }

    @Override
    public boolean updateViewCount(String id) {
        return JdbcHelper.update(SQL_UPDATE_VIEW_COUNT, id) > 0;
    }

    @Override
    public List<News> getNewsByAuthor(String author) {
        return JdbcHelper.query(SQL_BY_AUTHOR, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) list.add(map(rs));
            return list;
        }, author);
    }

    @Override
    public boolean existsByCategoryId(String categoryId) {
        String sql = "SELECT COUNT(*) FROM news WHERE categoryId = ?";
        return JdbcHelper.query(sql, rs -> rs.next() && rs.getInt(1) > 0, categoryId);
    }

    @Override
    public boolean existsByAuthorId(String author) {
        String sql = "SELECT COUNT(*) FROM news WHERE author = ?";
        return JdbcHelper.query(sql, rs -> rs.next() && rs.getInt(1) > 0, author);
    }
}
