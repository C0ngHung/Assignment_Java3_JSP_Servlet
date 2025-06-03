package org.example.assignment_java3.utils;

import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.example.assignment_java3.config.AppConfigReader;
import org.example.assignment_java3.config.MailSessionFactory;

public class Mailer {

    public static int send(String from, String to, String subject, String body) {
        try {
            // Khai báo username, password lấy từ file config
            String username = AppConfigReader.getMailUsername();
            String password = AppConfigReader.getMailPassword();

            // Tạo mail
            Session session = MailSessionFactory.getSession(username, password);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8", "html");
            // Gửi mail
            Transport.send(message);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        String from = AppConfigReader.getMailUsername();
        int result = Mailer.send(from, "daoconghungg@gmail.com", "Test", "Test Thử lần 1");
        System.out.println("Kết quả gửi mail: " + result);
    }

}
