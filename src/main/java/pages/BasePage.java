package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ConfigReaderUtil;

import java.time.Duration;

public class BasePage {

    protected final WebDriver driver;
    private final int TIMEOUT = Integer.parseInt(ConfigReaderUtil.getChromeProperty("timeout")); //seconds

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    protected WebElement waitForElementToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Type text into the given WebElement
    protected void type(WebElement element, String text) {
        element.sendKeys(text);
    }

    // CLick the given WebElement
    protected void click(WebElement element) {
        element.click();
    }

    // Return text of the given WebElement
    protected String getText(WebElement element) {
        return element.getText();
    }
}
