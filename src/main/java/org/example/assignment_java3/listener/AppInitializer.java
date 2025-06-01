package org.example.assignment_java3.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.example.assignment_java3.config.DatabaseMigration;

@WebListener
public class AppInitializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println(">> Starting Flyway Migration...");
        DatabaseMigration.migrate();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // optional cleanup
    }
}
