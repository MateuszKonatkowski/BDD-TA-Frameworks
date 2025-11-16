package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css="input[id='user-name']")
    private WebElement loginInput;

    @FindBy(css="input[id='password']")
    private WebElement passwordInput;

    @FindBy(css="input[id='login-button']")
    private WebElement loginButton;

    @FindBy(css="h3[data-test='error']")
    private WebElement errorMessage;

    public LoginPage enterLogin(String login)
    {
        loginInput.sendKeys(login);
        return this;
    }

    public LoginPage enterPassword (String password)
    {
        type(passwordInput,password);
        return this;
    }

    public InventoryPage clickOnLoginButton ()
    {
        click(loginButton);
        return new InventoryPage(driver);
    }

}
