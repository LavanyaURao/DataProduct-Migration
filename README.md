# DataProduct-Migration
This product migrates the data from SQL Server to Snowflake. Covers one time (historical)  load as well as CDC ( Change Data Capture / incremental )  from on-prem / cloud.

## Creating an Executable JAR

### 1. Package the Project
Used the **Maven Shade Plugin** to create a fat (executable) JAR containing all project dependencies.

```bash
mvn clean package
```

This generates:

```
target/
└── DataProductMigration-1.0-SNAPSHOT.jar
```

### 2. Load the Snowflake JDBC Driver
While running the JAR, the Snowflake JDBC driver was not automatically detected. To resolve this, the driver was explicitly loaded before creating the connection.

```java
Class.forName("net.snowflake.client.jdbc.SnowflakeDriver");
```

### 3. Support External Configuration
Modified `ConfigLoader` to load properties from an external configuration file instead of the default `application.properties`.

```java
ConfigLoader.load(args[0]);
```

### 4. Update `Main.java`
Updated the `main()` method to accept the configuration file as a command-line argument.

```java
if (args.length != 1) {
    System.out.println("Usage:");
    System.out.println("java -jar DataProductMigration-1.0-SNAPSHOT.jar <config-file>");
    return;
}

ConfigLoader.load(args[0]);
```

### 5. Create the Configuration File
Created a `config.properties` file containing Azure SQL, Snowflake, and Azure Storage connection details.

### 6. Run the Application

```bash
java -jar target/DataProductMigration-1.0-SNAPSHOT.jar target/config.properties
```

The application will:

- Connect to Azure SQL Database
- Generate Snowflake DDL
- Create tables in Snowflake
- Export data to CSV
- Compress CSV files
- Upload files to Azure Data Lake Storage Gen2
- Load data into Snowflake
- Complete the migration pipeline
