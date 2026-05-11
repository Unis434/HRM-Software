package HRS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Manages application configuration.
 *
 * Sensitive values (database password, API keys) are NEVER stored in
 * config.properties. Instead, this class checks environment variables
 * first and falls back to the properties file only for non-sensitive
 * settings.
 *
 * Recommended environment variables for production:
 *   HRS_DB_URL       — JDBC connection string
 *   HRS_DB_USERNAME  — database username
 *   HRS_DB_PASSWORD  — database password  (never in config.properties)
 *
 * config.properties should only contain non-sensitive application settings
 * such as logging levels, report output paths, and locale preferences.
 */
public class ConfigurationManager {

    private static final Logger  LOGGER      = Logger.getLogger(ConfigurationManager.class.getName());
    private static final String  CONFIG_FILE = "config.properties";

    // Environment variable names for sensitive credentials
    public static final String ENV_DB_URL      = "HRS_DB_URL";
    public static final String ENV_DB_USERNAME = "HRS_DB_USERNAME";
    public static final String ENV_DB_PASSWORD = "HRS_DB_PASSWORD";

    private final Properties properties;

    public ConfigurationManager() {
        this.properties = new Properties();
        loadProperties();
    }

    // ------------------------------------------------------------------
    // Internal loading
    // ------------------------------------------------------------------

    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.warning("config.properties not found or unreadable; using environment variables only.");
        }
    }

    // ------------------------------------------------------------------
    // Public API
    // ------------------------------------------------------------------

    /**
     * Returns the value of a configuration key.
     * Environment variables take precedence over the properties file.
     * Sensitive keys (containing "password" or "secret") are rejected
     * with an IllegalArgumentException to prevent accidental logging.
     *
     * @param key the configuration key
     * @return the resolved value, or null if not found
     */
    public String getProperty(String key) {
        if (isSensitiveKey(key)) {
            throw new IllegalArgumentException(
                    "Sensitive key '" + key + "' must be read via the dedicated credential methods, "
                            + "not getProperty().");
        }
        String envValue = System.getenv(toEnvName(key));
        if (envValue != null) return envValue;
        return properties.getProperty(key);
    }

    /**
     * Stores a non-sensitive configuration value and persists it to disk.
     * Sensitive keys are rejected.
     */
    public void setProperty(String key, String value) {
        if (isSensitiveKey(key)) {
            throw new IllegalArgumentException(
                    "Sensitive key '" + key + "' must not be written to config.properties.");
        }
        properties.setProperty(key, value);
        saveProperties();
    }

    // ------------------------------------------------------------------
    // Credential accessors — read from environment variables only
    // ------------------------------------------------------------------

    /**
     * Returns the database JDBC URL.
     * Reads HRS_DB_URL environment variable; falls back to a
     * non-sensitive property "database.url" if the env var is absent.
     */
    public String getDatabaseUrl() {
        String envValue = System.getenv(ENV_DB_URL);
        if (envValue != null && !envValue.isBlank()) return envValue;
        // Fall back to property (URL itself is not a secret)
        return properties.getProperty("database.url", "jdbc:sqlite:hrs.db");
    }

    /**
     * Returns the database username from the HRS_DB_USERNAME environment variable.
     */
    public String getDatabaseUsername() {
        String envValue = System.getenv(ENV_DB_USERNAME);
        if (envValue != null && !envValue.isBlank()) return envValue;
        // Username is low-sensitivity; allow properties fallback
        return properties.getProperty("database.username", "hrs_user");
    }

    /**
     * Returns the database password from the HRS_DB_PASSWORD environment variable ONLY.
     * Never reads this from a file.
     *
     * @throws IllegalStateException if the environment variable is not set
     */
    public String getDatabasePassword() {
        String password = System.getenv(ENV_DB_PASSWORD);
        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Database password not configured. "
                            + "Set the " + ENV_DB_PASSWORD + " environment variable.");
        }
        return password;
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private void saveProperties() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "HRS application configuration — do not store passwords here");
        } catch (IOException e) {
            LOGGER.severe("Failed to save config.properties: " + e.getMessage());
        }
    }

    /**
     * Converts a dot-separated property key to an environment variable name.
     * Example: "database.url" -> "DATABASE_URL"
     */
    private String toEnvName(String key) {
        return key.toUpperCase().replace('.', '_');
    }

    private boolean isSensitiveKey(String key) {
        String lower = key.toLowerCase();
        return lower.contains("password") || lower.contains("secret") || lower.contains("token");
    }
}