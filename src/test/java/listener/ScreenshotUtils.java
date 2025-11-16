package listener;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

public class ScreenshotUtils {
    public static void attachPageScreenshot(WebDriver webDriver, String name) {
        if (webDriver instanceof TakesScreenshot) {
            byte[] bytes = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(bytes), "png");
        }
    }
}
