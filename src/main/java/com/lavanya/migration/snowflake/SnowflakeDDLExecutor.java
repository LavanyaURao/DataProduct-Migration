package com.lavanya.migration.snowflake;

import com.lavanya.migration.config.ConfigLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;

public class SnowflakeDDLExecutor {

    public static void executeAllDDL() {

        try (Connection connection = SnowflakeConnection.getConnection()) {

            // Set Snowflake session context
            Statement sessionStatement = connection.createStatement();

            sessionStatement.execute(
                    "USE ROLE " +
                            ConfigLoader.getProperty("snowflake.role"));

            sessionStatement.execute(
                    "USE WAREHOUSE " +
                            ConfigLoader.getProperty("snowflake.warehouse"));

            sessionStatement.execute(
                    "USE DATABASE " +
                            ConfigLoader.getProperty("snowflake.database"));

            sessionStatement.execute(
                    "USE SCHEMA " +
                            ConfigLoader.getProperty("snowflake.schema"));

            sessionStatement.close();

            // Execute all DDL files
            Files.walk(Paths.get("output"))
                    .filter(path -> path.toString().endsWith(".sql"))
                    .forEach(path -> executeDDL(connection, path));

            System.out.println("\n✅ All DDL files executed successfully!");

        } catch (Exception e) {

            System.out.println("\n❌ Failed to execute DDL files.");
            e.printStackTrace();

        }

    }

    private static void executeDDL(Connection connection, Path filePath) {

        try {

            String fileName = filePath.getFileName().toString();

            System.out.println("\nExecuting : " + fileName);

            String sql = Files.readString(filePath);

            Statement statement = connection.createStatement();

            statement.execute(sql);

            statement.close();

            System.out.println("✅ Created table from : " + fileName);

        } catch (IOException e) {

            System.out.println("❌ Unable to read file : " + filePath.getFileName());
            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("❌ Failed executing : " + filePath.getFileName());
            e.printStackTrace();

        }

    }

}