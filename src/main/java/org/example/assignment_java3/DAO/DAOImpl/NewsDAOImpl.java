package org.example.assignment_java3.DAO.DAOImpl;

import org.example.assignment_java3.DAO.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.utils.JdbcHelper;

import java.util.ArrayList;
import java.util.List;

public class NewsDAOImpl implements NewsDAO {

    private static final String SQL_INSERT_NEWS = "INSERT INTO news (id, title, content, image, postDate, author, viewCount, categoryId, home) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_GET_NEWS_BY_ID = "SELECT * FROM news WHERE id = ?";
    private static final String SQL_UPDATE_NEWS = "UPDATE news SET title = ?, content = ?, image = ?, postDate = ?, author = ?, viewCount = ?, categoryId = ?, home = ? WHERE id = ?";
    private static final String SQL_DELETE_NEWS = "DELETE FROM news WHERE id = ?";
    private static final String SQL_GET_ALL_NEWS = "SELECT * FROM news";
    private static final String SQL_GET_ALL_NEWS_BY_HOME = "SELECT * FROM news WHERE home = 1";
    private static final String SQL_GET_TOP_5_NEWS_LATEST = "SELECT TOP 5 * FROM news ORDER BY postDate DESC";
    private static final String SQL_GET_TOP_5_VIEWS_COUNT = "SELECT TOP 5 * FROM news ORDER BY viewCount DESC";

    private News mapNewsFromResultSetToNews(java.sql.ResultSet rs) {
        try {
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
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public News createNews(News news) {
        if (news == null) {
            return null;
        }
        int row = JdbcHelper.update(SQL_INSERT_NEWS,
                news.getId(),
                news.getTitle(),
                news.getContent(),
                news.getImage(),
                news.getPostDate(),
                news.getAuthor(),
                news.getViewCount(),
                news.getCategoryId(),
                news.isHome());
        return row > 0 ? news : null;
    }

    @Override
    public News getNewsById(String id) {
        return JdbcHelper.query(SQL_GET_NEWS_BY_ID, rs -> {
            if (rs.next()) {
                return mapNewsFromResultSetToNews(rs);
            }
            return null;
        }, id);
    }

    @Override
    public News updateNews(News news) {
        if (news == null) {
            return null;
        }
        int row = JdbcHelper.update(SQL_UPDATE_NEWS,
                news.getTitle(),
                news.getContent(),
                news.getImage(),
                news.getPostDate(),
                news.getAuthor(),
                news.getViewCount(),
                news.getCategoryId(),
                news.isHome(),
                news.getId());
        return row > 0 ? news : null;
    }

    @Override
    public int deleteNews(String id) {
        return JdbcHelper.update(SQL_DELETE_NEWS, id);
    }

    @Override
    public List<News> getAllNews() {
        return JdbcHelper.query(SQL_GET_ALL_NEWS, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                newsList.add(mapNewsFromResultSetToNews(rs));
            }
            return newsList;
        });
    }

    @Override
    public List<News> getAllNewsByHome() {
        return JdbcHelper.query(SQL_GET_ALL_NEWS_BY_HOME, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                newsList.add(mapNewsFromResultSetToNews(rs));
            }
            return newsList;
        });
    }

    @Override
    public List<News> getTop5ViewsCount() {
        return JdbcHelper.query(SQL_GET_TOP_5_VIEWS_COUNT, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                newsList.add(mapNewsFromResultSetToNews(rs));
            }
            return newsList;
        });
    }

    @Override
    public List<News> getTop5NewsLatest() {
        return JdbcHelper.query(SQL_GET_TOP_5_NEWS_LATEST, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                newsList.add(mapNewsFromResultSetToNews(rs));
            }
            return newsList;
        });
    }

    @Override
    public List<News> getTop5NewsSeenMore() {
        // throw new UnsupportedOperationException("Method not implemented yet");
        return List.of();
    }
}
