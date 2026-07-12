package com.lavanya.migration;

import com.lavanya.migration.db.DatabaseConnection;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {


        System.out.println("      DATA PRODUCT MIGRATION");


        try {

            System.out.println("Connecting to Azure SQL Database...");

            Connection connection = DatabaseConnection.getConnection();

            System.out.println("✅ Connection Successful!");

            connection.close();

        } catch (Exception e) {

            System.out.println("❌ Connection Failed!");

            e.printStackTrace();

        }

    }
}