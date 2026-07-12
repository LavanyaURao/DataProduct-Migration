package com.lavanya.migration.db;

import com.lavanya.migration.config.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() throws SQLException {

        String url = ConfigLoader.getProperty("sqlserver.url");
        String username = ConfigLoader.getProperty("sqlserver.username");
        String password = ConfigLoader.getProperty("sqlserver.password");

        return DriverManager.getConnection(url, username, password);
    }

}