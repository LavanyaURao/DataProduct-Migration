package com.lavanya.migration.writer;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class CSVWriter {

    public static void write(ResultSet rs, String tableName) throws Exception {

        String fileName = "output/" + tableName + ".csv";

        FileWriter writer = new FileWriter(fileName);

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        // Write Header
        for (int i = 1; i <= columnCount; i++) {

            writer.append(metaData.getColumnName(i));

            if (i < columnCount)
                writer.append(",");

        }

        writer.append("\n");

        // Write Rows
        while (rs.next()) {

            for (int i = 1; i <= columnCount; i++) {

                Object value = rs.getObject(i);

                writer.append(value == null ? "" : value.toString());

                if (i < columnCount)
                    writer.append(",");

            }

            writer.append("\n");

        }

        writer.close();

        System.out.println("✅ CSV Generated : " + fileName);

    }

}