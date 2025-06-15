package org.example.assignment_java3.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

public class DatabaseMigration {

    // Khai báo URL, username, password lấy từ file config
    private static final String url = AppConfigReader.getDbUrl();
    private static final String dbDriver = AppConfigReader.getDbDriver();
    private static final String dbUsername = AppConfigReader.getDbUsername();
    private static final String dbPassword = AppConfigReader.getDbPassword();

    public static void migrate() {
        try {
            Class.forName(dbDriver);
            System.out.println(">> JDBC Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.err.println(">> JDBC Driver NOT FOUND!");
            e.printStackTrace();
            return;
        }

        try {
            // Cấu hình HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(dbUsername);
            config.setPassword(dbPassword);
            config.setDriverClassName(dbDriver);

            // Tạo DataSource
            HikariDataSource dataSource = new HikariDataSource(config);

            // Cấu hình Flyway
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
