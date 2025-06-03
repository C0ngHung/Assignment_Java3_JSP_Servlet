package org.example.assignment_java3.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfigReader {

    private static final Properties props = new Properties();

    static {
        try (InputStream input = AppConfigReader.class
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input != null) {
                props.load(input);
            } else {
                throw new RuntimeException("Không tìm thấy file application.properties");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi đọc file application.properties", e);
        }
    }

    // ----------- MAIL ----------
    public static String getMailUsername() {
        return props.getProperty("mail.username");
    }

    public static String getMailPassword() {
        return props.getProperty("mail.password");
    }

    // ----------- DATABASE ----------
    public static String getDbUrl() {
        return props.getProperty("db.url");
    }

    public static String getDbUsername() {
        return props.getProperty("db.username");
    }

    public static String getDbPassword() {
        return props.getProperty("db.password");
    }

    // ----------- DÙNG CHUNG ----------
    public static String get(String key) {
        return props.getProperty(key);
    }
}
