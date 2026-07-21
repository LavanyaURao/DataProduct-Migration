package com.lavanya.migration.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private static final Properties properties = new Properties();

    public static void load(String configFile) {

        try (FileInputStream input = new FileInputStream(configFile)) {

            properties.load(input);

        } catch (IOException e) {

            throw new RuntimeException("Unable to load config file: " + configFile, e);

        }

    }

    public static String getProperty(String key) {

        return properties.getProperty(key);

    }

}