package com.lavanya.migration.reader;

import java.sql.*;

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

                System.out.println("\n=========================================");
                System.out.println("TABLE : " + tableName);
                System.out.println("=========================================");

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

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            while (rs.next()) {

                for (int i = 1; i <= columnCount; i++) {

                    System.out.printf("%-20s : %s%n",
                            metaData.getColumnName(i),
                            rs.getObject(i));

                }

                System.out.println("-----------------------------------------");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}