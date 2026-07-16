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

        Connection connection = null;

        try {

            // Create output folder if it doesn't exist
            FileUtils.createOutputFolder();

            // Connect to Azure SQL Database
            System.out.println("\nConnecting to Azure SQL Database...");
            connection = DatabaseConnection.getConnection();
            System.out.println("✅ Connection Successful!");

            // Generate CSV files
            System.out.println("\nGenerating CSV files...");
            TableReader.readTables(connection);

            // Compress CSV files
            System.out.println("\nCompressing CSV files...");
            GzipCompressor.compressAllCsvFiles();
            System.out.println("✅ All CSV files compressed successfully!");

            // Connect to Azure Data Lake Storage
            System.out.println("\nConnecting to Azure Data Lake Storage...");
            AzureStorageUploader.initialize();

            // Upload all GZIP files
            System.out.println("\nUploading GZIP files to Azure Data Lake Storage...");
            AzureStorageUploader.uploadAllFiles();

            System.out.println("\n========================================");
            System.out.println("✅ PROCESS COMPLETED SUCCESSFULLY!");
            System.out.println("========================================");

        } catch (Exception e) {

            System.out.println("\n========================================");
            System.out.println("❌ PROCESS FAILED!");
            System.out.println("========================================");

            e.printStackTrace();

        } finally {

            try {

                if (connection != null) {
                    connection.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}