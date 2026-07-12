package com.lavanya.migration.utils;

import java.io.File;

public class FileUtils {

    public static final String OUTPUT_FOLDER = "output";

    public static void createOutputFolder() {

        File folder = new File(OUTPUT_FOLDER);

        if (!folder.exists()) {

            if (folder.mkdir()) {
                System.out.println("✅ Output folder created.");
            } else {
                System.out.println("❌ Failed to create output folder.");
            }

        } else {

            System.out.println("Output folder already exists.");

        }

    }

}