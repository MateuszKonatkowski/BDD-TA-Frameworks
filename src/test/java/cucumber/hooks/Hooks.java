package cucumber.hooks;

import driver.BrowserFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigReaderUtil;
import util.DriverManager;
import util.ScreenShotUtil;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    // Runs before each scenario. Initializes the WebDriver and stores it in a thread-safe storage.
    @Before
    public void setUp(Scenario scenario) {

        WebDriver driver = BrowserFactory.getDriver();
        DriverManager.setDriver(driver);
        logger.info("Scenario STARTED: {}", scenario.getName());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ConfigReaderUtil.getChromeProperty("global.timeout"))));
        driver.get(ConfigReaderUtil.getChromeProperty("url"));
    }

    // Runs after each scenario. If the scenario failed, takes a screenshot and attaches it to the report. Then closes the WebDriver.
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (driver != null) {
            if (scenario.isFailed()) {
                logger.error("Scenario FAILED: {}", scenario.getName());
                try {
                    // Capture screenshot using ScreenShotUtil class
                    String screenshotPath = ScreenShotUtil.takeScreenshot(driver, scenario.getName());
                    if (screenshotPath != null) {
                        logger.info("Screenshot taken: {}", screenshotPath);
                        // Read screenshot as bytes and attach it to Cucumber report
                        byte[] screenshotBytes = Files.readAllBytes(Path.of(screenshotPath));
                        scenario.attach(screenshotBytes, "image/png", "Screenshot on failure");
                    }
                } catch (Exception e) {
                    logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
                }
            } else {
                logger.info("Scenario PASSED: {}", scenario.getName());
            }
            // Clean up WebDriver
            if (driver != null) {
                driver.quit();
                DriverManager.removeDriver();
            }
        }
    }
}
