package com.lavanya.migration.snowflake;

import com.lavanya.migration.config.ConfigLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SnowflakeConnection {

    public static Connection getConnection() throws SQLException {

        String url = ConfigLoader.getProperty("snowflake.url");

        Properties properties = new Properties();

        properties.put("user",
                ConfigLoader.getProperty("snowflake.username"));

        properties.put("password",
                ConfigLoader.getProperty("snowflake.password"));

        properties.put("db",
                ConfigLoader.getProperty("snowflake.database"));

        properties.put("schema",
                ConfigLoader.getProperty("snowflake.schema"));

        properties.put("warehouse",
                ConfigLoader.getProperty("snowflake.warehouse"));

        properties.put("role",
                ConfigLoader.getProperty("snowflake.role"));

        return DriverManager.getConnection(url, properties);
    }
}