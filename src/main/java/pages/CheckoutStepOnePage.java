package pages;

import models.Adress;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckoutStepOnePage extends BasePage {

    public CheckoutStepOnePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#cancel")
    private WebElement cancelButton;

    @FindBy(css = "#continue")
    private WebElement continueButton;

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(css = "#postal-code")
    private WebElement zipCodeInput;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public CheckoutStepOnePage enterFirstName(String firstName) {
        waitForElementToBeVisible(firstNameInput);
        type(firstNameInput, firstName);
        return this;
    }

    public CheckoutStepOnePage enterLastName(String lastName) {
        type(lastNameInput, lastName);
        return this;
    }

    public CheckoutStepOnePage enterZipCode(String zipCode) {
        type(zipCodeInput, zipCode);
        return this;
    }

    public CheckoutStepTwoPage clickOnContinueButton() {
        click(continueButton);
        return new CheckoutStepTwoPage(driver);
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errorMessage);
        return getText(errorMessage);
    }

    public CheckoutStepOnePage enterAddress(Adress adress) {
        enterFirstName(adress.getFirstName());
        enterLastName(adress.getLastName());
        enterZipCode(adress.getZipCode());
        return this;
    }
}
