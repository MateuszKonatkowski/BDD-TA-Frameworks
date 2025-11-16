package util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScreenShotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenShotUtil.class);

    //Takes a screenshot of the current browser window and saves it to a file.
    public static String takeScreenshot(WebDriver driver, String testName) {
        // Get today's date (used to create a daily folder)
        String dateFolder = DateTimeUtil.getCurrentDate();
        // Folder where the screenshot will be saved
        String screenshotDir = ConfigReaderUtil.getChromeProperty("screenShotPath") + dateFolder;
        // Current time, used to make the filename unique
        String timestamp = DateTimeUtil.getCurrentTime();
        // Make the test name safe for file systems
        String safeTestName = testName.replaceAll("[\\\\/:*?\"<>|]", "_");
        // Full path to the screenshot file
        String screenshotPath = screenshotDir + "/" + safeTestName + "_" + timestamp + ".png";

        try {
            // Create folder if it doesn't exist
            Files.createDirectories(Paths.get(screenshotDir));
            // Take screenshot from the browser
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            // Copy the screenshot to the target path
            Files.copy(srcFile.toPath(), Paths.get(screenshotPath));
            logger.info("Screenshot saved to: {}", screenshotPath);
            return screenshotPath;
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", e.getMessage(), e);
            return null;
        }
    }
}