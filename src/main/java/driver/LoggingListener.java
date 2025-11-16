package driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingListener implements WebDriverListener {
    private static final Logger log = LoggerFactory.getLogger(LoggingListener.class);

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        log.info("Find element: {}", locator);
    }

    @Override
    public void beforeClick(WebElement element) {
        log.info("Click on: {}", safe(element));
    }

    @Override
    public void beforeSendKeys(WebElement element, CharSequence... keysToSend) {
        log.info("Type '{}' into: {}", String.join("", keysToSend), safe(element));
    }

    @Override
    public void afterGetText(WebElement element, String result) {
        log.info("Get text '{}' from: {}", result, safe(element));
    }

    private String safe(WebElement e) {
        try { return String.format("<%s> text='%s'", e.getTagName(), e.getText()); }
        catch (Throwable t) { return "<element>"; }
    }
}
