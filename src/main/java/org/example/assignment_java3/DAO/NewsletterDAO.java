package org.example.assignment_java3.DAO;

import org.example.assignment_java3.entity.Newsletter;

import java.util.List;

public interface NewsletterDAO {

    boolean subscribe(String email);

    boolean unsubscribe(String email);

    List<Newsletter> getAll();
}
