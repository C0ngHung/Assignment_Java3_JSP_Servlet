package org.example.assignment_java3.DAO;

import org.example.assignment_java3.entity.Newsletter;

import java.util.List;

public interface NewsletterDAO {

    Newsletter createNewsletter(Newsletter newsletter);

    Newsletter getNewsletterById(String id);

    Newsletter updateNewsletter(Newsletter newsletter);

    int deleteNewsletter(String id);

    List<Newsletter> getAllNewsletter();
}
