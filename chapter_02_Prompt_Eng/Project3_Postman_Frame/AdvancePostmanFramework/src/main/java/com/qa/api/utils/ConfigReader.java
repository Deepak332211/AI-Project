package com.qa.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Utility class to read configuration properties from config.properties file
 * Handles environment-specific configurations like base URL, API keys, credentials
 */
public class ConfigReader {
    
    private static Properties properties = null;
    private static final String CONFIG_FILE_PATH = "src/main/resources/config.properties";

    /**
     * Load properties from the config file
     * @throws IOException if config file is not found
     */
    public static void loadProperties() {
        if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
                properties.load(fis);
                LoggerUtil.info("Configuration properties loaded successfully from: " + CONFIG_FILE_PATH);
            } catch (IOException e) {
                LoggerUtil.error("Failed to load config.properties: " + e.getMessage());
                throw new RuntimeException("Unable to load configuration file: " + CONFIG_FILE_PATH, e);
            }
        }
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value, or throws exception if key not found
     */
    public static String getProperty(String key) {
        if (properties == null) {
            loadProperties();
        }
        
        String value = properties.getProperty(key);
        if (value == null) {
            LoggerUtil.warn("Property key not found: " + key);
            throw new RuntimeException("Property not found: " + key);
        }
        return value;
    }

    /**
     * Get property value with default fallback
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get base URL for API testing
     * @return Base URL from config
     */
    public static String getBaseURL() {
        return getProperty("base.url");
    }

    /**
     * Get API key for authentication
     * @return API key from config
     */
    public static String getAPIKey() {
        return getProperty("api.key", "");
    }

    /**
     * Get username for basic auth
     * @return Username from config
     */
    public static String getUsername() {
        return getProperty("username", "");
    }

    /**
     * Get password for basic auth
     * @return Password from config
     */
    public static String getPassword() {
        return getProperty("password", "");
    }

    /**
     * Get bearer token if available
     * @return Bearer token from config
     */
    public static String getBearerToken() {
        return getProperty("bearer.token", "");
    }

    /**
     * Get connection timeout in milliseconds
     * @return Connection timeout value
     */
    public static int getConnectionTimeout() {
        String timeout = getProperty("connection.timeout", "5000");
        return Integer.parseInt(timeout);
    }

    /**
     * Get read timeout in milliseconds
     * @return Read timeout value
     */
    public static int getReadTimeout() {
        String timeout = getProperty("read.timeout", "5000");
        return Integer.parseInt(timeout);
    }

    /**
     * Check if logging is enabled
     * @return true if logging is enabled
     */
    public static boolean isLoggingEnabled() {
        String logging = getProperty("logging.enabled", "true");
        return Boolean.parseBoolean(logging);
    }

    /**
     * Reload properties (useful for testing different environments)
     */
    public static void reloadProperties() {
        properties = null;
        loadProperties();
    }
}
