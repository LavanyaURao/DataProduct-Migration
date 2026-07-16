package com.lavanya.migration;

import com.lavanya.migration.compressor.GzipCompressor;
import com.lavanya.migration.db.DatabaseConnection;
import com.lavanya.migration.reader.TableReader;
import com.lavanya.migration.storage.AzureStorageUploader;
import com.lavanya.migration.utils.FileUtils;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        System.out.println("      DATA PRODUCT MIGRATION");

        try {

            // Create output folder if it doesn't exist
            FileUtils.createOutputFolder();

            System.out.println("\nConnecting to Azure SQL Database...");

            Connection connection = DatabaseConnection.getConnection();

            System.out.println("✅ Connection Successful!");

            // Read all tables and generate CSV files
            System.out.println("\nGenerating CSV files...");
            TableReader.readTables(connection);

            // Compress all generated CSV files
            System.out.println("\nCompressing CSV files...");
            GzipCompressor.compressAllCsvFiles();
            System.out.println("✅ All CSV files compressed successfully!");

            // Connect to Azure Data Lake Storage
            System.out.println("\nConnecting to Azure Data Lake Storage...");
            AzureStorageUploader.initialize();

            // Close database connection
            connection.close();

            System.out.println("\n✅ Process Completed Successfully!");

        } catch (Exception e) {

            System.out.println("\n❌ Error occurred while executing the application.");
            e.printStackTrace();

        }

    }

}