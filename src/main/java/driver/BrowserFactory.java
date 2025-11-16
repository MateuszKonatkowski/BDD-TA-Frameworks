package driver;

import org.openqa.selenium.support.events.EventFiringDecorator;
import util.ConfigReaderUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import enums.Browsers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

// Factory Method + Decorator
public class BrowserFactory {
    private static final Logger logger = LoggerFactory.getLogger(BrowserFactory.class);

    public static WebDriver getDriver() {
        String browserName = System.getProperty("browser", ConfigReaderUtil.getChromeProperty("browser")).toUpperCase();
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", ConfigReaderUtil.getChromeProperty("headless")));
        int width = Integer.parseInt(System.getProperty("window.width", ConfigReaderUtil.getChromeProperty("window.width")));
        int height = Integer.parseInt(System.getProperty("window.height", ConfigReaderUtil.getChromeProperty("window.height")));
        boolean notifications = Boolean.parseBoolean(System.getProperty("notification", ConfigReaderUtil.getChromeProperty("notification")));
        boolean extensions = Boolean.parseBoolean(System.getProperty("extensions", ConfigReaderUtil.getChromeProperty("extensions")));
        Browsers browser = Browsers.valueOf(browserName);

        WebDriver driver;

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) chromeOptions.addArguments("--headless=new");
                chromeOptions.addArguments("--window-size=" + width + "," + height);
                if (notifications) chromeOptions.addArguments("--disable-notifications");
                if (extensions) chromeOptions.addArguments("--disable-extensions");
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("credentials_enable_service", false);
                chromePrefs.put("profile.password_manager_enabled", false);
                chromePrefs.put("profile.password_manager_leak_detection", false);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                driver = new ChromeDriver(chromeOptions);
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) firefoxOptions.addArguments("--headless");
                firefoxOptions.addArguments("--width=" + width);
                firefoxOptions.addArguments("--height=" + height);
                driver = new FirefoxDriver(firefoxOptions);
                break;

            default:
                logger.error("Browser not supported: {}", browser);
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        logger.info("WebDriver initialized successfully for browser: {}", browser);

        WebDriver decorated = new EventFiringDecorator(new LoggingListener()).decorate(driver);
        return decorated;
    }
}
