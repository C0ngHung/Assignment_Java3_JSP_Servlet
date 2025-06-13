package org.example.assignment_java3.service;

import org.example.assignment_java3.entity.Newsletter;

import java.util.List;

public interface NewsletterService {
    Newsletter createNewsletter(Newsletter newsletter);

    Newsletter getNewsletterEmail(String email);

    Newsletter updateNewsletter(Newsletter newsletter);

    int deleteNewsletter(String id);

    List<Newsletter> getAllNewsletter();
}
