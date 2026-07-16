package com.lavanya.migration.translator;

public class DataTypeMapper {

    public static String mapToSnowflakeType(String sqlServerType,
                                            int columnSize,
                                            int decimalDigits) {

        sqlServerType = sqlServerType.toUpperCase();

        switch (sqlServerType) {

            case "INT":
                return "INTEGER";

            case "BIGINT":
                return "BIGINT";

            case "SMALLINT":
                return "SMALLINT";

            case "TINYINT":
                return "NUMBER(3)";

            case "BIT":
                return "BOOLEAN";

            case "FLOAT":
                return "FLOAT";

            case "REAL":
                return "FLOAT";

            case "DECIMAL":
            case "NUMERIC":
                return "NUMBER(" + columnSize + "," + decimalDigits + ")";

            case "MONEY":
                return "NUMBER(19,4)";

            case "SMALLMONEY":
                return "NUMBER(10,4)";

            case "CHAR":
                return "CHAR(" + columnSize + ")";

            case "NCHAR":
                return "CHAR(" + columnSize + ")";

            case "VARCHAR":
                return "VARCHAR(" + columnSize + ")";

            case "NVARCHAR":
                return "VARCHAR(" + columnSize + ")";

            case "TEXT":
            case "NTEXT":
                return "VARCHAR";

            case "DATE":
                return "DATE";

            case "TIME":
                return "TIME";

            case "DATETIME":
            case "DATETIME2":
            case "SMALLDATETIME":
                return "TIMESTAMP_NTZ";

            case "UNIQUEIDENTIFIER":
                return "VARCHAR(36)";

            case "BINARY":
            case "VARBINARY":
                return "BINARY";

            default:
                return "VARCHAR";
        }

    }

}