package com.lavanya.migration.translator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class DDLTranslator {

    public static void translateAllTables(Connection connection) {

        try {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet tables = metaData.getTables(
                    null,
                    "dbo",
                    "%",
                    new String[]{"TABLE"});

            while (tables.next()) {

                String tableName = tables.getString("TABLE_NAME");

                // Ignore SQL Server system tables
                if (tableName.startsWith("trace_")) {
                    continue;
                }

                translateTable(connection, tableName);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private static void translateTable(Connection connection,
                                       String tableName) {

        try {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet columns =
                    metaData.getColumns(null, "dbo", tableName, "%");

            StringBuilder ddl = new StringBuilder();

            ddl.append("CREATE OR REPLACE TABLE ")
                    .append(tableName)
                    .append(" (\n");

            while (columns.next()) {

                String columnName =
                        columns.getString("COLUMN_NAME");

                String sqlType =
                        columns.getString("TYPE_NAME");

                int columnSize =
                        columns.getInt("COLUMN_SIZE");

                int decimalDigits =
                        columns.getInt("DECIMAL_DIGITS");

                String nullable =
                        columns.getString("IS_NULLABLE");

                String snowflakeType =
                        DataTypeMapper.mapToSnowflakeType(
                                sqlType,
                                columnSize,
                                decimalDigits);

                ddl.append("    ")
                        .append(columnName)
                        .append(" ")
                        .append(snowflakeType);

                if ("NO".equals(nullable)) {
                    ddl.append(" NOT NULL");
                }

                ddl.append(",\n");

            }

            // Remove last comma
            int lastComma = ddl.lastIndexOf(",");

            if (lastComma != -1) {
                ddl.deleteCharAt(lastComma);
            }

            ddl.append(");");

            DDLWriter.writeDDL(tableName, ddl.toString());

        } catch (Exception e) {

            System.out.println("Failed translating table : " + tableName);

            e.printStackTrace();

        }

    }

}