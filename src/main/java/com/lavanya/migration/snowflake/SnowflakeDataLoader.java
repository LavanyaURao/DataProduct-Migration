package com.lavanya.migration.snowflake;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class SnowflakeDataLoader {

    public static void loadAllTables() {

        try (Connection connection = SnowflakeConnection.getConnection()) {

            Statement statement = connection.createStatement();

            // Set Snowflake session
            statement.execute("USE ROLE SYSADMIN");
            statement.execute("USE WAREHOUSE COMPUTE_WH");
            statement.execute("USE DATABASE DATA_PRODUCT_MIGRATION");
            statement.execute("USE SCHEMA MIGRATION");

            // Automatically load all .csv.gz files
            Files.walk(Paths.get("output"))
                    .filter(path -> path.toString().endsWith(".csv.gz"))
                    .forEach(path -> loadTable(statement, path));

            statement.close();

            System.out.println("\n✅ All tables loaded successfully!");

        } catch (Exception e) {

            System.out.println("\n❌ Failed loading data into Snowflake.");
            e.printStackTrace();

        }

    }

    private static void loadTable(Statement statement, Path path) {

        try {

            String fileName = path.getFileName().toString();

            // Remove .csv.gz to get table name
            String tableName = fileName.replace(".csv.gz", "");

            System.out.println("Loading table : " + tableName);

            String copyInto =
                    "COPY INTO " + tableName +
                            " FROM @migration_stage/" + fileName +
                            " FILE_FORMAT = (FORMAT_NAME = csv_gzip_format) " +
                            " ON_ERROR = CONTINUE";

            statement.execute(copyInto);

            System.out.println("✅ Loaded : " + tableName);

        } catch (Exception e) {

            System.out.println("❌ Failed loading : " + path.getFileName());
            e.printStackTrace();

        }

    }

}