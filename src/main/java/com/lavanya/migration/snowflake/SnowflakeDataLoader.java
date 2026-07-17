package com.lavanya.migration.snowflake;

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

            // Load each table
            loadTable(statement, "Attendance");
            loadTable(statement, "Departments");
            loadTable(statement, "EmployeeProjects");
            loadTable(statement, "Employees");
            loadTable(statement, "Projects");
            loadTable(statement, "Salaries");

            statement.close();

            System.out.println("\n✅ All tables loaded successfully!");

        } catch (Exception e) {

            System.out.println("\n❌ Failed loading data into Snowflake.");
            e.printStackTrace();

        }

    }

    private static void loadTable(Statement statement, String tableName)
            throws Exception {

        System.out.println("Loading table : " + tableName);

        String copyInto =
                "COPY INTO " + tableName +
                        " FROM @migration_stage/" + tableName + ".csv.gz " +
                        " FILE_FORMAT = (FORMAT_NAME = csv_gzip_format) " +
                        " ON_ERROR = CONTINUE";

        statement.execute(copyInto);

        System.out.println("✅ Loaded : " + tableName);

    }

}