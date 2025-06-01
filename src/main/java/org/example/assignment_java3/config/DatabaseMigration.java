package org.example.assignment_java3.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.internal.database.base.Database;

import javax.sql.DataSource;

public class DatabaseMigration {

    public static void migrate() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println(">> JDBC Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.err.println(">> JDBC Driver NOT FOUND!");
            e.printStackTrace();
            return;
        }

        try {
            // Cấu hình HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:sqlserver://localhost:1433;databaseName=NewsDB;encrypt=true;trustServerCertificate=true;characterEncoding=UTF-8");
            config.setUsername("sa");
            config.setPassword("songlong");
            config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

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
