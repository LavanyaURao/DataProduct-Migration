package com.lavanya.migration.snowflake;

import java.sql.Connection;

public class SnowflakeConnectionTest {

    public static void main(String[] args) {

        try {

            System.out.println("Connecting to Snowflake...");

            Connection connection =
                    SnowflakeConnection.getConnection();

            System.out.println("✅ Connection Successful!");

            connection.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}