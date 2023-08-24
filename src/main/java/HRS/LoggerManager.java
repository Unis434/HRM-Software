package HRS;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {
    private static Logger logger = Logger.getLogger(LoggerManager.class.getName());

    public LoggerManager() {
        // Configure the logger
        configureLogger();
    }

    private void configureLogger() {
        try {
            // Create a file handler to write logs to a file
            FileHandler fileHandler = new FileHandler("application.log", true);

            // Set a custom formatter for log entries
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);

            // Add the file handler to the logger
            logger.addHandler(fileHandler);

            // Set the logging level (e.g., INFO, WARNING, SEVERE)
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logWarning(String message) {
        logger.warning(message);
    }

    public void logError(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    // Add more logging methods as needed

    public static void main(String[] args) {
        LoggerManager loggerManager = new LoggerManager();

        // Example usage:
        loggerManager.logInfo("User 'Alice' logged in.");
        loggerManager.logWarning("Failed login attempt for user 'Unisa' from IP address 192.168.1.100.");
        try {
            int result = 10 / 0; // Simulate an error
        } catch (Exception e) {
            loggerManager.logError("An error occurred during a calculation.", e);
        }
    }
}
