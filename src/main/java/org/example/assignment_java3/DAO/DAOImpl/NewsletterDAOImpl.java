package org.example.assignment_java3.DAO.DAOImpl;

import org.example.assignment_java3.DAO.NewsletterDAO;
import org.example.assignment_java3.entity.Newsletter;
import org.example.assignment_java3.utils.JdbcHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NewsletterDAOImpl implements NewsletterDAO {

    private static final String SQL_INSERT_NEWSLETTER = "INSERT INTO newsletter (email, enable) VALUES (?, ?)";
    private static final String SQL_GET_NEWSLETTER_BY_ID = "SELECT * FROM newsletter WHERE email = ?";
    private static final String SQL_UPDATE_NEWSLETTER = "UPDATE newsletter SET enable = ? WHERE email = ?";
    private static final String SQL_DELETE_NEWSLETTER = "DELETE FROM newsletter WHERE email = ?";
    private static final String SQL_GET_ALL_NEWSLETTER = "SELECT * FROM newsletter";

    private Newsletter mapNewsletterFromResultSetToNewsletter(ResultSet rs) {
        try {
            Newsletter newsletter = new Newsletter();
            newsletter.setEmail(rs.getString("email"));
            newsletter.setEnable(rs.getBoolean("enable"));
            return newsletter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Newsletter createNewsletter(Newsletter newsletter) {
        if (newsletter == null) return null;
        int row = JdbcHelper.update(SQL_INSERT_NEWSLETTER,
                newsletter.getEmail(),
                newsletter.isEnable());
        return row > 0 ? newsletter : null;
    }

    @Override
    public Newsletter getNewsletterById(String id) {
        return JdbcHelper.query(SQL_GET_NEWSLETTER_BY_ID, rs -> {
            if (rs.next()) {
                return mapNewsletterFromResultSetToNewsletter(rs);
            }
            return null;
        });
    }

    @Override
    public Newsletter updateNewsletter(Newsletter newsletter) {
        if (newsletter == null) return null;
        int row = JdbcHelper.update(SQL_UPDATE_NEWSLETTER,
                newsletter.getEmail(),
                newsletter.isEnable());
        return row > 0 ? newsletter : null;
    }

    @Override
    public int deleteNewsletter(String id) {
        return JdbcHelper.update(SQL_DELETE_NEWSLETTER, id);
    }

    @Override
    public List<Newsletter> getAllNewsletter() {
        return JdbcHelper.query(SQL_GET_ALL_NEWSLETTER, rs -> {
            List<Newsletter> newsletterList = new ArrayList<>();
            while (rs.next()) {
                newsletterList.add(mapNewsletterFromResultSetToNewsletter(rs));
            }
            return newsletterList;
        });
    }
}
