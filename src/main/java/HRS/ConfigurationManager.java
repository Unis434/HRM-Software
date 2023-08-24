package HRS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationManager {
    private static final String CONFIG_FILE = "config.properties";
    private Properties properties;

    public ConfigurationManager() {
        loadProperties();
    }

    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        saveProperties();
    }

    private void saveProperties() {
        try (FileOutputStream fileOutputStream = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fileOutputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ConfigurationManager configManager = new ConfigurationManager();

        // Set and save properties
        configManager.setProperty("database.url", "jdbc:mysql://localhost:3306/payroll");
        configManager.setProperty("database.username", "root");
        configManager.setProperty("database.password", "mypassword");

        // Retrieve properties
        String dbUrl = configManager.getProperty("database.url");
        String dbUsername = configManager.getProperty("database.username");
        String dbPassword = configManager.getProperty("database.password");

        System.out.println("Database URL: " + dbUrl);
        System.out.println("Database Username: " + dbUsername);
        System.out.println("Database Password: " + dbPassword);
    }
}
