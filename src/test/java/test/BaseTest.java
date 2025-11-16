package test;
import driver.BrowserFactory;

import io.qameta.allure.testng.AllureTestNg;
import listener.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import pages.HeaderComponent;
import pages.LoginPage;
import util.ConfigReaderUtil;
import util.DriverManager;
import java.time.Duration;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Listeners({
        listener.TestListener.class,
        io.qameta.allure.testng.AllureTestNg.class

})

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp()
    {
        // Get configured WebDriver instance (based on browser, headless mode, etc.)
        driver = BrowserFactory.getDriver();
        // Store driver in ThreadLocal for safe parallel execution
        DriverManager.setDriver(driver);
        // Navigate to base URL from config
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(ConfigReaderUtil.getChromeProperty("global.timeout"))));
        driver.get(ConfigReaderUtil.getChromeProperty("url"));
        LoginPage loginPage = new LoginPage(driver);
        loginPage
                .enterLogin(ConfigReaderUtil.getChromeProperty("login"))
                .enterPassword(ConfigReaderUtil.getChromeProperty("password"))
                .clickOnLoginButton();
        assertThat(driver.getCurrentUrl()).endsWith("/inventory.html");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        HeaderComponent headerComponent = new HeaderComponent(driver);
        headerComponent
                .clickOnMenuButton()
                .clickLogoutOption();
        assertThat(driver.getCurrentUrl()).isEqualTo(ConfigReaderUtil.getChromeProperty("url"));

        if (driver != null) {
            // Quit browser and clean up ThreadLocal
            driver.quit();
            DriverManager.removeDriver();
        }
    }
}
