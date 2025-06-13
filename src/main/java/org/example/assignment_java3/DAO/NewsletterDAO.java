package org.example.assignment_java3.DAO;

import org.example.assignment_java3.entity.Newsletter;

import java.util.List;

public interface NewsletterDAO {

    Newsletter createNewsletter(Newsletter newsletter);

    Newsletter getNewsletterByEmail(String email);

    Newsletter updateNewsletter(Newsletter newsletter);

    int deleteNewsletter(String id);

    List<Newsletter> getAllNewsletter();
}
