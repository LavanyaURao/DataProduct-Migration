package com.lavanya.migration;

import com.lavanya.migration.db.DatabaseConnection;
import com.lavanya.migration.reader.TableReader;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        System.out.println("      DATA PRODUCT MIGRATION");

        try {

            System.out.println("\nConnecting to Azure SQL Database...");

            Connection connection = DatabaseConnection.getConnection();

            System.out.println("✅ Connection Successful!");

            TableReader.listTables(connection);

            connection.close();

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

}