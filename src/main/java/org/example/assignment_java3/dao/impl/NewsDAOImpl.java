package org.example.assignment_java3.dao.impl;


import org.example.assignment_java3.dao.NewsDAO;
import org.example.assignment_java3.entity.News;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final String SQL_UPDATE_VIEW_COUNT = "UPDATE news SET viewCount = viewCount + 1 WHERE id = ?";
    private static final String SQL_GET_ALL_NEWS_BY_CATEGORY_ID = "SELECT * FROM news WHERE categoryId = ?";
    private static final String SQL_GET_ALL_NEWS_BY_AUTHOR = "SELECT * FROM news WHERE author = ?";

    private News mapNewsFromResultSetToNews(ResultSet rs) {
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
    public List<News> getTop5NewsViewed(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        String sql = "SELECT TOP 5 * FROM news WHERE id IN (" +
                String.join(",", Collections.nCopies(ids.size(), "?")) + ")";
        List<News> newsList = JdbcHelper.query(sql, rs -> {
            List<News> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapNewsFromResultSetToNews(rs));
            }
            return list;
        }, ids.toArray());

        /*
         * Gọi hàm sort để sắp xếp newsList theo thứ tự của các id trong list ids.
         * Cụ thể: dựa vào vị trí (index) của id trong list ids.
         * Khi thêm id vào list ids, id đầu tiên sẽ có vị trí 0,
         * id tiếp theo sẽ là 1, 2, 3, 4, 5,...
         * Khi sort, id có vị trí nhỏ hơn sẽ được lên đầu tiên trong list newsList.
         */
        newsList.sort((news1, news2) ->
                ids.indexOf(news1.getId()) - ids.indexOf(news2.getId())
        );

        return newsList;
    }


    @Override
    public List<News> getNewsByCategory(String categoryId) {
        return JdbcHelper.query(SQL_GET_ALL_NEWS_BY_CATEGORY_ID, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                newsList.add(mapNewsFromResultSetToNews(rs));
            }
            return newsList;
        }, categoryId);
    }

    @Override
    public boolean updateViewCount(String id) {
        if (id == null) {
            return false;
        }
        int row = JdbcHelper.update(SQL_UPDATE_VIEW_COUNT, id);
        return row > 0;
    }

    @Override
    public List<News> getNewsByAuthor(String author) {
        return JdbcHelper.query(SQL_GET_ALL_NEWS_BY_AUTHOR, rs -> {
            List<News> newsList = new ArrayList<>();
            while (rs.next()) {
                newsList.add(mapNewsFromResultSetToNews(rs));
            }
            return newsList;
        }, author);
    }
}
