package com.lavanya.migration.translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class DDLWriter {

    public static void writeDDL(String tableName, String ddl) {

        try {

            File outputFolder = new File("output");

            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }

            File ddlFile = new File(outputFolder, tableName + ".sql");

            BufferedWriter writer = new BufferedWriter(new FileWriter(ddlFile));

            writer.write(ddl);

            writer.close();

            System.out.println("✅ DDL Generated : output/" + tableName + ".sql");

        } catch (Exception e) {

            System.out.println("❌ Failed to generate DDL for " + tableName);
            e.printStackTrace();

        }

    }

}