package com.mtomics.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader class reads configuration from properties file
 */
public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";

    /**
     * Constructor - loads properties file
     */
    public ConfigReader() {
        if (properties == null) {
            loadProperties();
        }
    }

    /**
     * Load properties from config file
     */
    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Configuration loaded successfully from {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration file: {}", e.getMessage());
            throw new RuntimeException("Configuration file not found: " + CONFIG_FILE_PATH, e);
        }
    }

    /**
     * Get property value by key
     * 
     * @param key Property key
     * @return Property value
     */
    public String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in configuration", key);
        }
        return value;
    }

    /**
     * Get property value with default
     * 
     * @param key          Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default
     */
    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get base URL from configuration
     * 
     * @return Base URL
     */
    public String getBaseUrl() {
        return getProperty("base.url");
    }

    /**
     * Get browser from configuration
     * 
     * @return Browser name
     */
    public String getBrowser() {
        return getProperty("browser");
    }

    /**
     * Get environment from configuration
     * 
     * @return Environment name
     */
    public String getEnvironment() {
        return getProperty("environment");
    }
}
