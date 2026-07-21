package com.lavanya.migration;

import com.lavanya.migration.compressor.GzipCompressor;
import com.lavanya.migration.config.ConfigLoader;
import com.lavanya.migration.db.DatabaseConnection;
import com.lavanya.migration.reader.TableReader;
import com.lavanya.migration.snowflake.SnowflakeDDLExecutor;
import com.lavanya.migration.snowflake.SnowflakeDataLoader;
import com.lavanya.migration.storage.AzureStorageUploader;
import com.lavanya.migration.translator.DDLTranslator;
import com.lavanya.migration.utils.FileUtils;

import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        // Check if config file is provided
        if (args.length != 1) {
            System.out.println("Usage:");
            System.out.println("java -jar DataProductMigration-1.0-SNAPSHOT.jar <config-file>");
            return;
        }

        // Load configuration
        ConfigLoader.load(args[0]);

        System.out.println("      DATA PRODUCT MIGRATION");

        Connection connection = null;

        try {

            // Create output folder if it doesn't exist
            FileUtils.createOutputFolder();

            // Connect to Azure SQL Database
            System.out.println("\nConnecting to Azure SQL Database...");
            connection = DatabaseConnection.getConnection();
            System.out.println("✅ Connection Successful!");

            // Generate Snowflake DDL files
            System.out.println("\nGenerating Snowflake DDL files...");
            DDLTranslator.translateAllTables(connection);

            // Deploy translated DDL to Snowflake
            System.out.println("\nDeploying translated DDL to Snowflake...");
            SnowflakeDDLExecutor.executeAllDDL();

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

            // Load data into Snowflake
            System.out.println("\nLoading data into Snowflake...");
            SnowflakeDataLoader.loadAllTables();

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
                    System.out.println("\nAzure SQL Database connection closed.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}