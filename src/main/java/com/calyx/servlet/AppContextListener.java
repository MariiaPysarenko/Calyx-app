package com.calyx.servlet;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(
                    "PostgreSQL JDBC driver is missing from WEB-INF/lib. Rebuild the WAR with Maven (mvn package) and redeploy.",
                    e);
        }
    }
}
