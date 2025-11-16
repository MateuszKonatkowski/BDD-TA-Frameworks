package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


//Utility class used for reading configuration values from configFirefox.properties file. It loads the properties once when the class is first accessed.
public class ConfigReaderUtil {
    private static final Logger logger = LoggerFactory.getLogger(ConfigReaderUtil.class);

    // Properties object that stores key-value pairs from the config and testdata files
    private static final Properties firefox = new Properties();
    private static final Properties chrome = new Properties();

    // Static block is executed once when the class is loaded
    static {
        loadProperties(firefox, "properties/configFirefox.properties", "FIREFOX");
        loadProperties(chrome, "properties/configChrome.properties", "CHROME");
    }

    //Helper method to load a properties file and log the result
    public static void loadProperties(Properties target, String filePath, String label) {
        try (InputStream input = ConfigReaderUtil.class.getClassLoader().getResourceAsStream(filePath)) {
            if (input != null) {
                target.load(new java.io.InputStreamReader(input, StandardCharsets.UTF_8));
                logger.info("Loaded {} file successfully from path: {}", label, filePath);
            } else {
                logger.error("{} file not found in classpath: {}", label, filePath);
                // If the file is missing, we log the error and stop the program
                throw new RuntimeException(filePath + " not found");
            }
        } catch (IOException e) {
            logger.error("Error loading {} file '{}': {}", label, filePath, e.getMessage(), e);
            // If there's an issue reading the file, we throw a runtime exception
            throw new RuntimeException("Error loading " + filePath, e);
        }
    }

    //Retrieves the value associated with the given key from configFirefox.properties file.
    public static String getChromeProperty(String key) {
        return chrome.getProperty(key);
    }

    //Retrieves the value associated with the given key from configChrome.properties file.
    public static String getFirefoxProperty(String key) {
        return firefox.getProperty(key);
    }
}
