package com.lavanya.migration.reader;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class TableReader {

    public static void listTables(Connection connection) {

        try {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(
                    null,
                    null,
                    "%",
                    new String[]{"TABLE"});

            System.out.println("\nTables Found");
            System.out.println("-------------------------");

            while (tables.next()) {

                System.out.println(tables.getString("TABLE_NAME"));

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

}