package com.lavanya.migration.reader;

import com.lavanya.migration.writer.CSVWriter;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class TableReader {

    public static void readTables(Connection connection) {

        try {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(
                    null,
                    "dbo",
                    "%",
                    new String[]{"TABLE"});

            while (tables.next()) {

                String tableName = tables.getString("TABLE_NAME");

                // Ignore SQL Server system tables
                if (tableName.startsWith("trace_")) {
                    continue;
                }

                System.out.println("Generating CSV for : " + tableName);

                readTable(connection, tableName);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void readTable(Connection connection, String tableName) {

        try {

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);

            CSVWriter.write(rs, tableName);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}