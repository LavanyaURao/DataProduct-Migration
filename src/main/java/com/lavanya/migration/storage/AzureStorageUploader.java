package com.lavanya.migration.storage;

import com.azure.storage.file.datalake.DataLakeFileClient;
import com.azure.storage.file.datalake.DataLakeFileSystemClient;
import com.azure.storage.file.datalake.DataLakeServiceClient;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;
import com.lavanya.migration.config.ConfigLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AzureStorageUploader {

    private static DataLakeFileSystemClient fileSystemClient;

    // Initialize connection to Azure Data Lake Storage
    public static void initialize() {

        try {

            String connectionString =
                    ConfigLoader.getProperty("azure.storage.connection-string");

            String fileSystem =
                    ConfigLoader.getProperty("azure.storage.file-system");

            DataLakeServiceClient serviceClient =
                    new DataLakeServiceClientBuilder()
                            .connectionString(connectionString)
                            .buildClient();

            fileSystemClient =
                    serviceClient.getFileSystemClient(fileSystem);

            System.out.println("✅ Connected to Azure Data Lake Storage");

        } catch (Exception e) {

            System.out.println("❌ Unable to connect to Azure Data Lake Storage");
            e.printStackTrace();

        }

    }

    // Upload a single GZIP file
    public static void uploadFile(Path filePath) {

        try {

            String fileName = filePath.getFileName().toString();

            System.out.println("Uploading : " + fileName);

            DataLakeFileClient fileClient =
                    fileSystemClient.getFileClient(fileName);

            // Create file if it doesn't already exist
            if (!fileClient.exists()) {
                fileClient.create();
            }

            try (FileInputStream inputStream =
                         new FileInputStream(filePath.toFile())) {

                fileClient.upload(inputStream,
                        filePath.toFile().length(),
                        true);

            }

            System.out.println("✅ Uploaded : " + fileName);

        } catch (IOException e) {

            System.out.println("❌ Failed to upload : "
                    + filePath.getFileName());

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println("❌ Error uploading : "
                    + filePath.getFileName());

            e.printStackTrace();

        }

    }

    // Upload all GZIP files present in the output folder
    public static void uploadAllFiles() {

        try {

            Files.walk(Paths.get("output"))
                    .filter(path -> path.toString().endsWith(".gz"))
                    .forEach(AzureStorageUploader::uploadFile);

            System.out.println("\n✅ All GZIP files uploaded successfully!");

        } catch (IOException e) {

            System.out.println("❌ Failed to scan output folder.");
            e.printStackTrace();

        }

    }

}