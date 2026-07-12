package com.lavanya.migration.compressor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.zip.GZIPOutputStream;

public class GzipCompressor {

    private static final String OUTPUT_FOLDER = "output";

    public static void compressAllCsvFiles() {

        try {

            Files.walk(Paths.get(OUTPUT_FOLDER))
                    .filter(path -> path.toString().endsWith(".csv"))
                    .forEach(GzipCompressor::compressFile);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void compressFile(Path csvFile) {

        String gzipFile = csvFile.toString() + ".gz";

        try (
                FileInputStream fis = new FileInputStream(csvFile.toFile());
                FileOutputStream fos = new FileOutputStream(gzipFile);
                GZIPOutputStream gzipOut = new GZIPOutputStream(fos)
        ) {

            byte[] buffer = new byte[4096];
            int length;

            while ((length = fis.read(buffer)) > 0) {
                gzipOut.write(buffer, 0, length);
            }

            System.out.println("✅ Compressed : " + csvFile.getFileName());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}