package util;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//Singelton
//Utility class for managing WebDriver instances in a thread-safe way. It uses ThreadLocal to ensure each test thread has its own WebDriver.
public class DriverManager {
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    // Thread-local storage to keep a separate WebDriver for each test thread
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    //Returns the WebDriver instance for the current thread. If no driver is set, logs a warning and returns null.
    public static WebDriver getDriver() {
        WebDriver driver = driverThread.get();
        if (driver == null) {
            logger.warn("Attempted to get WebDriver, but it was NULL.");
        } else {
            logger.debug("Retrieved WebDriver instance: {}", driver);
        }
        return driver;
    }
    //Sets the WebDriver instance for the current thread.
    public static void setDriver(WebDriver driverInstance) {
        driverThread.set(driverInstance);
        logger.info("WebDriver set: {}", driverInstance);
    }

    // Removes the WebDriver instance from the current thread.
    public static void removeDriver() {
        logger.info("Removing WebDriver instance: {}", driverThread.get());
        driverThread.remove();
    }
}