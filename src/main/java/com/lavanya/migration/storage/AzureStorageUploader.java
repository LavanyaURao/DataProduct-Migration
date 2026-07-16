package com.lavanya.migration.storage;

import com.azure.storage.file.datalake.DataLakeFileSystemClient;
import com.azure.storage.file.datalake.DataLakeServiceClient;
import com.azure.storage.file.datalake.DataLakeServiceClientBuilder;
import com.lavanya.migration.config.ConfigLoader;

public class AzureStorageUploader {

    private static DataLakeFileSystemClient fileSystemClient;

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

}